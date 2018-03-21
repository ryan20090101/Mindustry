package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.Vars.*;
import io.anuke.mindustry.world.blocks.LogicBlocks;

public class ANDGate extends LogicBlock {
    public ANDGate(String name) {
        super(name);
    }

    @Override
    public boolean setLogic(Tile tile, Tile source, Boolean logicState) {
        LogicBlock.LogicEntity ent = tile.entity();
        ent.selfActive = logicState;
        ent.outputActive = !logicState;
        updateOutputLogic(tile);
        return true;
    }

    @Override
    public void placed(Tile tile) {
        super.placed(tile);
        LogicEntity ent = tile.entity();
        ent.selfActive = false;
        ent.outputActive = false;
    }
	
	@Override
	public void update(Tile tile){
		LogicEntity ent = tile.entity();
        if(Vars.world.tile((int)tile.entity().x, (int)tile.entity().y+1).block()==LogicBlocks.logicpylon&&Vars.world.tile((int)tile.entity().x, (int)tile.entity().y-1).block()==LogicBlocks.logicpylon){
			if(Vars.world.tile((int)tile.entity().x, (int)tile.entity().y+1).selfActive && Vars.world.tile((int)tile.entity().x, (int)tile.entity().y-1).selfActive){
				ent.outputActive = true;
			}else{
				ent.outputActive = false;
			}
		}else{
			ent.outputActive = false;
		}
	}
}
