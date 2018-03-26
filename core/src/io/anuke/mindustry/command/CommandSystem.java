package io.anuke.mindustry.command;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import io.anuke.ucore.function.Consumer;
import io.anuke.ucore.util.Log;

public class CommandSystem {
    private static ObjectMap<Class<?>, Consumer<Object>> listeners = new ObjectMap<>();

    /**Call to handle a packet being recieved for the server.*/
    public static void handleCommandReceived(Object object){
        if(listeners.get(object.getClass()) != null) listeners.get(object.getClass()).accept(object);
        else{
            Log.err("Unhandled command type: '{0}'!", ClassReflection.getSimpleName(object.getClass()));
        }
    }

    public static <T> void registerCommand(Class<T> type, Consumer<T> listener){
        listeners.put(type, (Consumer<Object>) listener);
    }
}

