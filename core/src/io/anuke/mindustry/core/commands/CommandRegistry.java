package io.anuke.mindustry.core.commands;

import java.util.Collection;
import java.util.HashMap;

/** Represents a registry of commands */
public class CommandRegistry {
    private HashMap<String, Command> registry = new HashMap<String, Command>();

    /**
     * Register a command in the CommandRegistry
     * @param c
     */
    public void registerCommand(Command c) {
        System.out.println("register command " + c.name);
        registry.put(c.name.toLowerCase(), c);
    }
    // you can override the name of the command manually, for example for aliases
    /**
     * Register a command in the CommandRegistry
     * @param forcedName Register the command under another name
     * @param c The command to register
     */
    public void registerCommand(String forcedName, Command c) {
        registry.put(forcedName.toLowerCase(), c);
    }
    /**
     * Run a command
     * @param name
     * @param ctx
     */
    public void runCommand(String name, CommandContext ctx) {
        Command command = registry.get(name.toLowerCase());
        if (command == null) {
            ctx.reply("No such command");
            return;
        }
        if ((!ctx.player.isAdmin && command.adminOnly) || (ctx.player.accessLevel < command.accessLevel)) {
            ctx.reply("No permissions");
            return;
        }
        command.run(ctx);
    }
    /**
     * Get a command by name
     * @param name
     * @return
     */
    public Command getCommand(String name) {
        Command command = registry.get(name.toLowerCase());
        if (command == null) throw new Error("No such command");
        return command;
    }
    /**
     * Get all commands in the registry
     * @return
     */
    public Command[] getAllCommands() {
        Collection<Command> commandSet = registry.values();
        Command[] commands = commandSet.toArray(new Command[commandSet.size()]);
        return commands;
    }
    /**
     * Check if a command exists in the registry
     * @param name
     * @return
     */
    public boolean isCommand(String name) {
        return registry.containsKey(name.toLowerCase());
    }
}