package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.mindustry.world.blocks.types.LogicBlock;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.Vars.*;
import io.anuke.mindustry.world.blocks.LogicBlocks;

import java.util.Iterator;

import static io.anuke.mindustry.Vars.world;

public class ANDGate extends LogicBlock {
    public ANDGate(String name) {
        super(name);
    }

    @Override
    public boolean setLogic(Tile tile, Tile source, Boolean logicState) {
        LogicEntity ent = tile.entity();
        int pos;
        boolean active = true;
        Iterator<Integer> it = tile.<LogicEntity>entity().inputBlocks.iterator();
        while(it.hasNext()) {
            pos = it.next();
            LogicEntity logicEntity = world[tile.dimension].tile(pos % world[tile.dimension].width(), pos / world[tile.dimension].width()).entity();
            if (logicEntity == null)
                continue;
            active = active && logicEntity.outputActive;
        }
        if(active){
            ent.outputActive = true;
            updateOutputLogic(tile);
            return true;
        }
        else
            ent.outputActive = false;
        updateOutputLogic(tile);
        return false;
    }
}
