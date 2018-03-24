package io.anuke.mindustry.ui.commands;

import static io.anuke.mindustry.Vars.ui;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.anuke.mindustry.Vars;

class EvalCommand extends Command {
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("nashorn");

	public EvalCommand() {
		super("eval");
		try {
			engine.put("game", engine.eval("Java.type('io.anuke.mindustry.Vars')"));
			engine.put("mindustry", engine.eval("Packages.io.anuke.mindustry"));
		} catch (ScriptException e) {
			throw new RuntimeException("Some internal thing that should have existed didn't");
		}
	}

	public void action(String[] arguments) {
		if (arguments.length < 1) {
			ui.chatfrag.addMessage("Not enough arguments, expected javascript expression", null);
			return;
		}

		Object evalResult;
		try {
			evalResult = engine.eval(String.join(" ", arguments));
		} catch (Throwable error) {
			// something was thrown
			evalResult = error;
		}
		if (evalResult == null) evalResult = "undefined or null";

		ui.chatfrag.addMessage(evalResult.toString(), null);
	}
}
