package io.anuke.mindustry.core;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.content.AmmoTypes;
import io.anuke.mindustry.content.Mechs;
import io.anuke.mindustry.content.blocks.Blocks;
import io.anuke.mindustry.content.blocks.StorageBlocks;
import io.anuke.mindustry.entities.Player;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.entities.traits.BuilderTrait;
import io.anuke.mindustry.game.Team;
import io.anuke.mindustry.gen.Call;
import io.anuke.mindustry.net.Packets;
import io.anuke.mindustry.net.ValidateException;
import io.anuke.mindustry.type.Item;
import io.anuke.mindustry.type.Mech;
import io.anuke.mindustry.type.Recipe;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Build;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.storage.CoreBlock;
import io.anuke.mindustry.world.blocks.storage.StorageBlock;
import io.anuke.ucore.util.Geometry;
import io.anuke.ucore.util.Log;
import io.anuke.mindustry.core.commands.Command;
import io.anuke.mindustry.core.commands.CommandContext;
import io.anuke.mindustry.core.commands.CommandRegistry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.function.Consumer;

import static io.anuke.mindustry.Vars.*;
import static io.anuke.mindustry.Vars.netServer;
import static io.anuke.mindustry.gen.Call.sendMessage;
import static io.anuke.ucore.util.Log.info;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

/* implementation
    add to NetEvents:sendMessage() line 24

        else {
            try {
                System.out.println(player.name + ": " + message);
                ChatCommands.parse(player, message);
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
        }
 */

public class ChatCommands {
    HashMap<String, Tile> k = new HashMap<String, Tile>();

    public static final String commandPrefix = "!";
    public static final String teamChatPrefix = ".";
    public static final Boolean globalByDefault = false;
    public static CommandRegistry commandRegistry = new CommandRegistry();
    public static Command teleportCommand = new Command("tp") {
        {
            // tbh i have no clue how to make this look not ugly
            help = "Teleport to another player";
        }
        public void run(CommandContext ctx) {
            Player player = ctx.player;
            String[] args = ctx.args;

            if (args.length < 2) {
                // send tp list
                int i = 0;
                ctx.reply("[accent]use !tp #");
                for (Player p : Vars.playerGroup.all()) {
                    if (p.getTeam() == player.getTeam()){
                        String fs = String.format("[cyan]#%d [white]- [#%s]%s", i, p.color, p.name);
                        ctx.reply(fs);
                    }
                    i++;
                }
                return;
            }
            int p = parseInt(args[1].replaceAll(" ", ""));
            if ((p < 0) || (p >= Vars.playerGroup.size())) {
                ctx.reply("[red]Invalid player");
                return;
            }
            Player dest = Vars.playerGroup.all().get(p);
            if (dest.getTeam() == player.getTeam()) player.setNet(dest.getX(), dest.getY());
            ctx.reply(String.format("[green]Teleported to [#%s]%s", dest.color, dest.name));
        }
    };
    public static Command mechCommand = new Command("mech") {
        {
            help = "Switch between normal and flying mechs";
        }
        public void run(CommandContext ctx) {
            ctx.player.inventory.clear();
            if (ctx.player.mech.flying) ctx.player.mech = Mechs.starterDesktop;
            else ctx.player.mech = Mechs.starterMobile;
        }
    };
    /*
    public static Command l33tCommand = new Command("1337") {
        {
            help = "Switch to the halberd";
            adminOnly = true;
        }
        public void run(CommandContext ctx) {
            ctx.player.mech = Mechs.halberd;
        }
    };
    */
    public static Command teamSwitchCommand = new Command("team") {
        {
            help = "Switch between teams";
        }
        public void run(CommandContext ctx) {
            if (ctx.player.health < 50) {
                ctx.reply("[red]You must have more than 50 health to switch teams");
                ctx.reply("[red]Consider reconnecting to the game");
                return;
            }
            ctx.player.toggleTeam();
            if (ctx.player.mech.flying) ctx.player.mech = Mechs.starterMobile;
            else ctx.player.mech = Mechs.starterDesktop;
            ctx.player.inventory.clear();
            ctx.player.damage(1000);
        }
    };
    public static Command superGunCommand = new Command("supergun") {
        {
            help = "idk what this does, honestly";
            adminOnly = true;
            secret = true; // or you could make it not secret
        }
        public void run(CommandContext ctx) {
            ctx.player.mech = Mechs.omega;
        }
    };
    public static Command playerListCommand = new Command("players") {
        {
            help = "List the players currently on the server";
        }
        public void run(CommandContext ctx) {
            ctx.reply("[accent]Player listing");
            int i = 0;
            for (Player p : Vars.playerGroup.all()) {
                Team t = p.getTeam();
                String fs = String.format("[cyan]#%d [#%s](%s team) [#%s]%s", i, t.color, t.name(), p.color, p.name);
                ctx.reply(fs);
                i++;
            }
        }
    };
    public static Command kickCommand = new Command("kick") {
        {
            help = "Use !players to get player number, then use '!kick # reasons'";
            adminOnly = true;
        }
        public void run(CommandContext ctx) {
            String reason = "";
            for (int i = 2; i < ctx.args.length; i++) reason = reason + " " + ctx.args[i];
            reason = " \"" + reason + "\"";
            int p;
            try {
                p = parseInt(ctx.args[1].replaceAll(" ", ""));
            } catch (NumberFormatException e) {
                ctx.reply("[red]Invalid number");
                return;
            }
            if ((p < 0) || (p >= Vars.playerGroup.size())) {
                ctx.reply("[red]Invalid player");
                return;
            }
            Player target = Vars.playerGroup.all().get(p);
            netServer.kick(target.con.id, Packets.KickReason.kick);
            cmdAdminBot(" kick " + target.name + " " + ctx.player.name + reason);
            info("Kicked player " + target.name);
        }
    };
    public static Command banCommand = new Command("ban") {
        {
            help = "Use !players to get player number, then use '!ban # reasons'";
            adminOnly = true;
        }
        public void run(CommandContext ctx) {
            String reason = "";
            for (int i = 2; i < ctx.args.length; i++) reason = reason + " " + ctx.args[i];
            reason = " \"" + reason + "\"";
            int p;
            try {
                p = parseInt(ctx.args[1].replaceAll(" ", ""));
            } catch (NumberFormatException e) {
                ctx.reply("[red]Invalid number");
                return;
            }
            if ((p < 0) || (p >= Vars.playerGroup.size())) {
                ctx.reply("[red]Invalid player");
                return;
            }
            Player target = Vars.playerGroup.all().get(p);
            netServer.admins.banPlayerIP(target.con.address);
            netServer.admins.banPlayerID(target.uuid);
            netServer.kick(target.con.id, Packets.KickReason.banned);
            info("Banned player by IP and ID: {0} / {1}", target.con.address, target.uuid);
            cmdAdminBot(" ban " + target.name + " " + ctx.player.name + reason);
        }
    };

    public static Command helpCommand = new Command("help") {
        {
            help = "List commands or get help with a command";
        }
        public void run(CommandContext ctx) {
            if (ctx.args.length > 1) {
                String command = ctx.args[1];
                if (commandRegistry.isCommand(command)) {
                    ctx.reply(commandRegistry.getCommand(command).help);
                } else ctx.reply("No such command");
            } else {
                ctx.reply("[accent]Use " + commandPrefix + "help <command> for help with a specific command");
                ctx.reply("[accent]Prefix messages with " + teamChatPrefix + " to chat to " +
                    (globalByDefault ? "your team" : "everyone"));
                ctx.reply("[accent]Command list: ");
                String l = "";
                Command[] commandList = commandRegistry.getAllCommands();
                for (Command command : commandList) {
                    if (command.secret) continue;
                    if (command.adminOnly && !ctx.player.isAdmin) continue;
                    l += commandPrefix + command.name + " ";
                }
                ctx.reply(l);
            }
        }
    };

    static {
        System.out.println("loading commands");
        commandRegistry.registerCommand(teleportCommand);
        commandRegistry.registerCommand(mechCommand);
        // commandRegistry.registerCommand(l33tCommand);
        commandRegistry.registerCommand(playerListCommand);
        commandRegistry.registerCommand(teamSwitchCommand);
        commandRegistry.registerCommand(superGunCommand);
        commandRegistry.registerCommand(kickCommand);
        commandRegistry.registerCommand(banCommand);

        commandRegistry.registerCommand(helpCommand);
    }

    /**
     * Parse a chat message for commands
     * @param player
     * @param message
     * @return True if the message should be sent to all other users, false otherwise
     */
    public static boolean parse(Player player, String message) {
        if (message.startsWith(commandPrefix)) {
            sendMessage(player.con.id, "[green]" + message);

            String[] args = message.split(" ");
            args[0] = args[0].substring(commandPrefix.length());
            String name = args[0];

            commandRegistry.runCommand(name, new CommandContext(player, args));
            return false;
        } else if (message.startsWith(teamChatPrefix) ^ !globalByDefault) {
            Team t = player.getTeam();
            String fs = String.format("[#%s](%s team) %s : [white] %s", t.color, t.name(), player.name, message);
            for (Player p : Vars.playerGroup.all()) if (p.getTeam() == t) Call.sendMessage(p.con.id, fs);
            return false;
        } else return true;
    }

    static void cmdAdminBot(String params){
        String botPath = System.getProperty("user.dir") + "\\admin_bot.py";
        String cmd = "py " + botPath + params;

        try {
            Process p = Runtime.getRuntime().exec(cmd);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* old stuff that doesn't work anymore
    static Mech [] mechs = {Mechs.alpha, Mechs.tau, Mechs.trident, Mechs.dart, Mechs.delta, Mechs.halberd, Mechs.javelin, Mechs.omega};

    static boolean mechSet(Player player, String message) {
        String[] split = message.split("mech");
        if (split.length <= 1) return false;
        int p = parseInt(split[1].replaceAll(" ", ""));
        if ((p < 0) || (p > 7)) return false;
        Mech mech = null;

        mech = mechs[p];
        if (mech == null) return false;
        player.mech = mech;
        player.mech.load();
        return true;
    }

    static void mechList(Player player) {
        sendMessage(player.con.id, "[accent]Mech/Ship list, use !mech #");
        sendMessage(player.con.id,
                "[scarlet] 0-alpha " +
                        "[white] 1-tau " +
                        "[royal] 2-trident " +
                        "[scarlet] 3-dart " +
                        "[white] 4-delta " +
                        "[royal] 5-halberd " +
                        "[scarlet] 6-javelin " +
                        "[white] 7-omega");
    }
    */
}
