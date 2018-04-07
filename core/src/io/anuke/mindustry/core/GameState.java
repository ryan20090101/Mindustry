package io.anuke.mindustry.core;

import io.anuke.mindustry.entities.Player;
import io.anuke.mindustry.game.Difficulty;
import io.anuke.mindustry.game.EventType.StateChangeEvent;
import io.anuke.mindustry.game.GameMode;
import io.anuke.mindustry.game.Inventory;
import io.anuke.ucore.core.Events;

import java.util.HashMap;
import java.util.Map;

public class GameState{
	private State state = State.menu;

	public final Inventory inventory = new Inventory();

	public int wave = 1;
	public int lastUpdated = -1;
	public float wavetime;
	public float extrawavetime;
	public int enemies = 0;
	public boolean gameOver = false;
	public GameMode mode = GameMode.waves;
	public Difficulty difficulty = Difficulty.normal;
	public boolean friendlyFire;
	public Map<String,Player> players = new HashMap<String,Player>(){{
	}};
	
	public void set(State astate){
		Events.fire(StateChangeEvent.class, state, astate);
		state = astate;
	}
	
	public boolean is(State astate){
		return state == astate;
	}

	public State getState(){
		return state;
	}
	
	public enum State{
		paused, playing, menu
	}
}
