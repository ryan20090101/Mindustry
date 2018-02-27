package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;

public class Tank extends LiquidBlock {

    // TODO: some tank features?

    public Tank(String name) {
        super(name);
    }

    @Override
    public boolean canReplace(Block other) {
        return other instanceof Conduit && other != this;
    }

    @Override
    public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
        return super.acceptLiquid(tile,source,liquid,amount) && !(tile.block() instanceof Tank);
    }
}
