package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;

public class Void extends Block{

    public Void(String name) {
        super(name);
        solid = true;
    }

    @Override
    public boolean canReplace(Block other){
        return other instanceof Junction || other instanceof Conveyor;
    }

    @Override
    public void handleItem(Item item, Tile tile, Tile source){ return; }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){ return true; }
}
