package io.anuke.mindustry.ui.commands;

import static io.anuke.mindustry.Vars.*;

public class SuicideCommand extends Command {
  public SuicideCommand() {
    super("suicide");
  }

  public void action(String[] arguments) {
    player.damage(player.health + 1);
    ui.chatfrag.addMessage("Committed suicide", null);
  }
}
