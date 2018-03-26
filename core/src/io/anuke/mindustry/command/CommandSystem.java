package io.anuke.mindustry.command;

import com.badlogic.gdx.utils.ObjectMap;
import io.anuke.ucore.function.Consumer;
import io.anuke.ucore.modules.Module;
import io.anuke.ucore.util.Log;

public class CommandSystem extends Module {
    private static ObjectMap<Class<?>, Consumer<Object>> listeners = new ObjectMap<>();

    public CommandSystem() {
        registerCommand(Commands.Kick.class, command -> {
            System.out.println(command.id);
            System.out.println(command.arguments);
        });
    }

    /**Call to handle a packet being recieved for the server.*/
    public static void handleCommandReceived(Object object){
        if(listeners.get(object.getClass()) != null) listeners.get(object.getClass()).accept(object);
        else{
            Log.err("Unhandled command type: '{0}'!", (object.getClass().getCanonicalName()));
        }
    }

    public static <T> void registerCommand(Class<T> type, Consumer<T> listener){
        listeners.put(type, (Consumer<Object>) listener);
    }
}

