package io.anuke.mindustry.command;

import com.badlogic.gdx.utils.ObjectMap;
import io.anuke.mindustry.entities.Player;
import io.anuke.mindustry.game.EventType;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetConnection;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.net.Packets;
import io.anuke.ucore.core.Events;
import io.anuke.ucore.function.Consumer;
import io.anuke.ucore.modules.Module;
import io.anuke.ucore.util.Log;

import static io.anuke.mindustry.Vars.*;

public class CommandSystem extends Module {
    private static ObjectMap<String, Consumer<String>> listeners = new ObjectMap<>();

    public CommandSystem() {
        registerCommand("kick", command -> {
            String arg = ((String) command).replaceFirst(" ","");
            if (state.players.containsKey(arg))
                netServer.kick(state.players.get(arg).clientid, Packets.KickReason.kick);
        });
        registerCommand("banip", command -> {
            String arg = ((String) command).replaceFirst(" ","");
            if (state.players.containsKey(arg)){
                Player player = state.players.get(arg);
                NetConnection connection = gwt ? null : Net.getConnection(player.clientid);
                netServer.admins.banPlayerIP(connection.address);
            }
        });
        registerCommand("banid", command -> {
            String arg = ((String) command).replaceFirst(" ","");
            if (state.players.containsKey(arg)){
                Player player = state.players.get(arg);
                NetConnection connection = gwt ? null : Net.getConnection(player.clientid);
                netServer.admins.banPlayerID(netServer.admins.getTrace(connection.address).uuid);
            }
        });
        registerCommand("ban", command -> {
            String arg = ((String) command).replaceFirst(" ","");
            if (state.players.containsKey(arg)){
                Player player = state.players.get(arg);
                NetConnection connection = gwt ? null : Net.getConnection(player.clientid);
                netServer.admins.banPlayerIP(connection.address);
                netServer.admins.banPlayerID(netServer.admins.getTrace(connection.address).uuid);
            }
        });
        registerCommand("surrender", command -> {
            state.gameOver = true;
            if (Net.server()) NetEvents.handleGameOver();
            Events.fire(EventType.GameOverEvent.class);
        });
    }

    /**Call to handle a packet being recieved for the server.*/
    public static void handleCommandReceived(Packets.AdminCommandPacket object){
        String command = object.message.split(" ")[0];
        if(listeners.get(command) != null) listeners.get(command).accept(object.message.replace(command,""));
        else{
            Log.err("Unhandled command: '{0}'!", command);
        }
    }

    public static <T> void registerCommand(String command, Consumer<T> listener){
        listeners.put(command, (Consumer<String>) listener);
    }
}