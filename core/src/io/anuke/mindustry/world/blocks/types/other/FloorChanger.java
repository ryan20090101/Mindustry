package io.anuke.mindustry.world.blocks.types.other;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.mindustry.world.blocks.types.Floor;
import java.util.Map;

public class FloorChanger extends Block {

    protected final int timerChange = timers++;

    protected int changeTime = 23044;

    protected Map<Floor,Floor> changeRecipe;

    public FloorChanger(String name) {
        super(name);
        update = true;
        solid = false;
    }

    @Override
    public void update(Tile tile) {
        if(!changeRecipe.containsKey(tile.floor())) {
            tile.setBlock(Blocks.air);
            return;
        }

        TileEntity ent = tile.entity();

        if (ent.timer.get(timerChange, changeTime)) {
            tile.setFloor(changeRecipe.get(tile.floor()));
        }
    }
}
