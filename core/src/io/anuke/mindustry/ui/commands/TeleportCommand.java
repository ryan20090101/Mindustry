package io.anuke.mindustry.ui.commands;

import io.anuke.mindustry.entities.Player;

import static io.anuke.mindustry.Vars.*;

public class TeleportCommand extends Command {
	Player player = players[0];

	public TeleportCommand() {
		super("teleport");
	}

	public void action(String[] arguments) {
		if (arguments.length < 2) {
			ui.chatfrag.addMessage("Need two arguments: x, y", null);
			return;
		}

		float x = 0f;
		float y = 0f;

		// handle relative coordinates
		if (arguments[0].startsWith("!")) {
			arguments[0] = arguments[0].substring(1);
			x = player.x;
		}
		if (arguments[1].startsWith("!")) {
			arguments[1] = arguments[1].substring(1);
			y = player.y;
		}

		try {
			x += Float.parseFloat(arguments[0]);
			y += Float.parseFloat(arguments[1]);
		} catch (NumberFormatException err) {
			ui.chatfrag.addMessage("Invalid coordinates", null);
			return;
		}

		player.x = x;
		player.y = y;
		ui.chatfrag.addMessage("Teleported to " + x + ", " + y, null);
	}
}
