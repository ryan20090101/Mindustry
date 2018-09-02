package io.anuke.mindustry.net;

import io.anuke.annotations.Annotations.Loc;
import io.anuke.annotations.Annotations.Remote;
import io.anuke.annotations.Annotations.Variant;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.core.ChatCommands;
import io.anuke.mindustry.entities.Player;
import io.anuke.mindustry.game.Team;
import io.anuke.mindustry.gen.Call;

import static io.anuke.mindustry.Vars.maxTextLength;
import static io.anuke.mindustry.Vars.playerGroup;
import static io.anuke.mindustry.gen.Call.sendMessage;
import static io.anuke.mindustry.gen.Call.sendMessage;
public class NetEvents{
    @Remote(called = Loc.server, targets = Loc.both, forward = true)
    public static void sendMessage(Player player, String message) throws Cancelled, ValidateException {
        if(message.length() > maxTextLength){
            throw new ValidateException(player, "Player has sent a message above the text limit.");
        }

        if(Vars.ui != null) {
            Vars.ui.chatfrag.addMessage(message, player == null ? null : colorizeName(player.id, player.name));
        } else {
            System.out.println(player.name + ": " + message);
            if (!ChatCommands.parse(player, message)) throw new Cancelled("Intercepted by command");
        }
    }

    @Remote(called = Loc.server, variants = Variant.both, forward = true)
    public static void sendMessage(String message){
        if(Vars.ui != null){
            Vars.ui.chatfrag.addMessage(message, null);
        }
    }

    private static String colorizeName(int id, String name){
        Player player = playerGroup.getByID(id);
        if(name == null || player == null) return null;
        return "[#" + player.color.toString().toUpperCase() + "]" + name;
    }
}
