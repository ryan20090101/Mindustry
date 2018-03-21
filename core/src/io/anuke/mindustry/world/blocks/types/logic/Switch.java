package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;

public class Switch extends LogicBlock {
    public Switch(String name) {
        super(name);
    }

    @Override
    public void tapped(Tile tile){
        LogicEntity entity = tile.entity();
        entity.selfActive = entity.selfActive ? false : true;
        entity.outputActive = entity.selfActive;
        updateOutputLogic(tile);
        setConfigure(tile, (byte)1, (byte)(entity.selfActive ? 1 : 0));
    }

    @Override
    public void configure(Tile tile, byte... data) {
        LogicEntity entity = tile.entity();
        if(entity != null){
            entity.selfActive = data[0] == 1;
        }
    }
}
