package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.util.Mathf;

public class Combiner extends Block{

    public Combiner(String name){
        super(name);
        solid = true;
        instantTransfer = true;
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        Tile to = getTileTarget(tile, source);

        return to != null && to.block().acceptItem(item, to, tile);
    }

    @Override
    public void handleItem(Item item, Tile tile, Tile source){
        Tile to = getTileTarget(tile, source);

        to.block().handleItem(item, to, tile);
    }

    Tile getTileTarget(Tile dest, Tile source){
        int dir = source.relativeTo(dest.x, dest.y);
        if(dir == -1) return null;

        Tile to = dest.getNearby(dir);

        return to;
    }
}
