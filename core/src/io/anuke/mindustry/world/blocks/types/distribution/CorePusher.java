package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;

import static io.anuke.mindustry.Vars.debug;
import static io.anuke.mindustry.Vars.state;

public class CorePusher extends Block {

    public CorePusher(String name) {
        super(name);
        health = 800;
        solid = true;
        destructible = true;
    }

    @Override
    public void handleItem(Item item, Tile tile, Tile source){
        if(Net.server() || !Net.active()) state.inventory.addItem(item, 1);
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        return item.material;
    }
}
