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

import static io.anuke.mindustry.Vars.tileGroup;
import static io.anuke.mindustry.Vars.tilesize;
import static io.anuke.mindustry.Vars.world;
import static io.anuke.mindustry.gen.Call.sendMessage;
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
                        ctx.reply("[blue]#[white]" + i + "-   [red]" + p.name);
                    }
                    i++;
                }
            }
            int p = parseInt(args[1].replaceAll(" ", ""));
            if ((p < 0) || (p >= Vars.playerGroup.size())) {
                ctx.reply("[red]Invalid player");
                return;
            }
            Player dest = Vars.playerGroup.all().get(p);
            if (dest.getTeam() == player.getTeam()) player.setNet(dest.getX(), dest.getY());
            ctx.reply("[green]Teleported to " + dest.name);
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
    public static Command l33tCommand = new Command("1337") {
        {
            help = "Switch to the halberd";
            adminOnly = true;
        }
        public void run(CommandContext ctx) {
            ctx.player.mech = Mechs.halberd;
        }
    };
    public static Command playerListCommand = new Command("players") {
        {
            help = "List the players currently on the server";
        }
        public void run(CommandContext ctx) {
            ctx.reply("[accent]Player listing");
            for (Player p : Vars.playerGroup.all()) {
                Team t = p.getTeam();
                String fs = String.format("[#%s](%s team) %s", t.color, t.name(), p.name);
                ctx.reply(fs);
            }
        }
    };
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
            help = "Explosive missiles!";
            adminOnly = true;
            secret = true; // or you could make it not secret
        }
        public void run(CommandContext ctx) {
            ctx.player.inventory.fillAmmo(AmmoTypes.missileExplosive);
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
        commandRegistry.registerCommand(l33tCommand);
        commandRegistry.registerCommand(playerListCommand);
        commandRegistry.registerCommand(teamSwitchCommand);
        commandRegistry.registerCommand(superGunCommand);

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
                /*
            else if (player.isAdmin && message.contains("!clear")) {
                for (int i = 0; i < world.width(); i++) {
                    for (int q = 0; q < world.height(); q++) {
                        Tile t = world.tile(i,q);
                        if (t.getTeam() == Team.red) {
                            if  (t.block() != Blocks.blockpart && (t.block() != StorageBlocks.core)){
                                Call.onDeconstructFinish(t, t.block());
                            }
                        }
                    }
                }

            } 
            else if (message.contains("!test")) {
                int x = round(player.x / tilesize), y = round(player.y / tilesize);
                Tile t = world.tile(x, y);
                Call.sendMessage(t.block().name);
                Block b = Block.getByName("conveyor");
                if (Build.validPlace(player.getTeam(),x,y,b,(byte)0)) {
                    Call.onConstructFinish(t, b, player.id, (byte) 0, Team.blue);
                }
            }
            //throw new ValidateException(player, "chatCommand"); // Blocks original message from broadcasting to other
            players, doesnt work here because try{}
            */

    static void pipeTeleport(Player player, int direction) {
        int x = round(player.x / tilesize), y = round(player.y / tilesize);
        Tile t = world.tile(x, y);
        System.out.println(t.block().name);
        int max = 200;
        while ((t.block().name.contains("conduit") && (max-- > 0))) {
            int rot = t.getRotation();
            x += direction * Geometry.d4[rot].x;
            y += direction * Geometry.d4[rot].y;
            t = world.tile(x, y);
        }
        player.setNet(x * tilesize, y * tilesize);
    }

    static void teamBattle() {
        sendMessage("[red] BATTLE TO THE [black]D E A T H");
        int totPlayers = Vars.playerGroup.size();
        int halfPlayers = totPlayers / 2;
        int radius = 100;
        //for (int i = 0; i < totPlayers; i++){
        int i = 0;
        Item ammo = Item.getByID(4);
        for (Player p : Vars.playerGroup.all()) {
            float x = 1600, y = 1600;
            x += Math.sin(i * 6.28 / totPlayers) * radius;
            y += Math.cos(i * 6.28 / totPlayers) * radius;
            p.mech = Mechs.delta;

            p.setNet(x, y);
            if (i < halfPlayers) p.toggleTeam();
            i++;
            for (int q = 0; q < 100; q++) {
                p.addAmmo(p.getWeapon().getAcceptedItems().iterator().next());
            }
        }
    }

    static boolean mechSet(Player player, String message) {
        String[] split = message.split("mech");
        if (split.length <= 1) return false;
        int p = parseInt(split[1].replaceAll(" ", ""));
        if ((p < 0) || (p > 7)) return false;
        Mech mech = null;
        switch (p) {
            case 0:
                mech = Mechs.alpha;
                break;
            case 1:
                mech = Mechs.tau;
                break;
            case 2:
                mech = Mechs.trident;
                break;
            case 3:
                mech = Mechs.dart;
                break;
            case 4:
                mech = Mechs.delta;
                break;
            case 5:
                mech = Mechs.halberd;
                break;
            case 6:
                mech = Mechs.javelin;
                break;
            case 7:
                mech = Mechs.omega;
                break;
        }
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

    public int markX = 0, markY = 0;
    public int previewCount = 0;
    public int previewLength = 25;
    public boolean drawPreview = false;


    int tileSize = 8;

    static boolean saveMap(){
        int sx = 0,  sy = 0,  ex = world.width(), ey = world.height();
        int wd = 1+(ex-sx);
        int hd = 1+(ey-sy);

        /*if ((wd >25)||(hd>25)) {
            return false;
        }*/

        String[][] patNames = new String[wd][hd];
        int[][] patRot = new int[wd][hd];

        System.out.println(wd + " x " + hd);

        for (int i = 0; i < wd; i++) {
            for (int q = 0; q < hd; q++) {
                Tile t = world.tile(sx + i , sy + q ); //  Tile t = world.tileWorld(sx + i * tileSize, sy + q * tileSize);
                patRot[i][q] = t.getRotation();
                patNames[i][q] = t.block().name;
                /*if (patNames[i][q].equals("router")){
                    Sorter.SorterEntity sorter = t.entity();
                    patNames[i][q] += "." + sorter.sortItem.id;
                }
                if (patNames[i][q].equals("teleporter")) {
                    Teleporter.TeleporterEntity tp = t.entity();
                    patNames[i][q] += "." + tp.color;
                }*/
            }
        }

        String s = "worldSave";

        try {
            String content = "";
            String nl = System.getProperty("line.separator");
            // = wd + "x" + hd + nl;
            content = wd + nl + hd + nl;
            for (int i = 0; i < wd; i++) {
                for (int q = 0; q < hd; q++) {
                    content = content + patNames[i][q] + "," + patRot[i][q] + " ";
                }
                content = content + nl;
            }
            String fpath = new File(".").getCanonicalPath();
            String path = fpath + "\\patterns\\" + s + ".txt";
            System.out.println(fpath);
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            System.out.println(file.getAbsoluteFile());
            Log.info("saved " + s);
            return true;
            //return "[green]saved " + s;
        } catch (Exception e) {
            Log.info(e.toString());
            return false;//return e.toString();
        }
        //if (netServer == null) ui.chatfrag.addMessage("[green]saved " + s, null);
    }

}
