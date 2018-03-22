package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;

public class LogicPylon extends LogicBlock {

	protected LogicPylon(String name) {
		super(name);
		solid = true;
		destructible = true;
        health = 50;
	}

	@Override
	public boolean setLogic(Tile tile, Tile source, Boolean logicState) {
		LogicBlock.LogicEntity ent = tile.entity();
		ent.selfActive = logicState;
		ent.outputActive = logicState;
		updateOutputLogic(tile);
		return true;
	}
}