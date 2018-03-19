package io.anuke.mindustry.ui.commands;

import java.util.HashMap;

public class CommandRegistry extends HashMap<String, Command> {
	private static final long serialVersionUID = 1L;

	public CommandRegistry() {
		add(new TeleportCommand());
		add(new EvalCommand());
		add(new SuicideCommand());
	}

	public void add(Command command) {
		put(command.name, command);
	}

	public Boolean run(String name, String[] arguments) {
		Command command = get(name.toLowerCase());
		if (command != null) {
			command.action(arguments);
			return true;
		} else return false;
	}
}
