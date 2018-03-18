package io.anuke.mindustry.ui.commands;

public abstract class Command {
	public String name;

	public Command(String name) {
		this.name = name;
	}

	public abstract void action(String[] arguments);
}
