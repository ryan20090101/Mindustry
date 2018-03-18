package io.anuke.mindustry.ui.commands;

import io.anuke.mindustry.entities.Player;

import static io.anuke.mindustry.Vars.*;

public class SuicideCommand extends Command {
  Player player = players[0];
  
  public SuicideCommand() {
    super("suicide");
  }

  public void action(String[] arguments) {
    player.damage(player.health + 1);
    ui.chatfrag.addMessage("Committed suicide", null);
  }
}
