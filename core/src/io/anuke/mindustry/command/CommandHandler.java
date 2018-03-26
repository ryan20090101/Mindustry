package io.anuke.mindustry.command;

public class CommandHandler {
    public CommandHandler(){
        CommandSystem.registerCommand(Commands.Kick.class, command -> {
            System.out.println(command.id);
            System.out.println(command.arguments);
        });
    }
}
