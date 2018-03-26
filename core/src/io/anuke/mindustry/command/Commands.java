package io.anuke.mindustry.command;

import com.badlogic.gdx.utils.ObjectMap;

public class Commands {

    private static ObjectMap<String,Class<?>> classes = new ObjectMap<String,Class<?>>() {{
            put("kick",Kick.class);
    }};

    public static Class<?> getClass(String command) {
        return classes.get(command);
    }

    public static class Kick implements Command{
        public int id;
        public String arguments;
    }
}
