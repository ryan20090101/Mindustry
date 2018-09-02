package io.anuke.mindustry.core.commands;

import io.anuke.mindustry.entities.Player;

import static io.anuke.mindustry.gen.Call.sendMessage;

/** Represents the context in which a command was called */
public class CommandContext {
    public Player player;
    public String[] args;

    /**
     * Create a new context
     * @param player
     * @param args
     */
    public CommandContext(Player player, String[] args) {
        this.player = player;
        this.args = args;
    }

    /**
     * Send a message back to the user who invoked the command
     * @param message
     */
    public void reply(String message) {
        sendMessage(player.con.id, message);
    }
}
