package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;

public class NOTGate extends LogicBlock {
    public NOTGate(String name) {
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
        ent.selfActive = true;
        ent.outputActive = true;
    }
}
