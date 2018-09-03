package io.anuke.mindustry.core.commands;

/** Represents a single Command */
public abstract class Command {
    public String name;
    /**
     * Help for this command, shown by the help command
     */
    public String help = "No help is availble for this command";
    /**
     * Whether or not this command may only be run by admins
     * Also hides the command in the command listing for users who are not admins
     */
    public boolean adminOnly = false;
    /**
     * Access level needed to run this command
     */
    public int accessLevel = 0;
    /**
     * Whether or not to always hide the command in the command listing
     */
    public boolean secret = false;

    public Command(String name) {
        // always ALWAYS lowercase command names
        this.name = name.toLowerCase();
    }

    /**
     * This method is called when the command is run
     * @param ctx
     */
    public abstract void run(CommandContext ctx);
}
