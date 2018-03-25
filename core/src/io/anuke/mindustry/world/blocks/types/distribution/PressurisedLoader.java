package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.mindustry.world.blocks.types.LiquidPowerBlock;

public class PressurisedLoader extends LiquidPowerBlock {

    protected float powerUsagePerTransfer = 0.001f;

    public PressurisedLoader(String name) {
        super(name);
    }

    @Override
    public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
        LiquidPowerEntity entity = tile.entity();

        return (entity.liquid == liquid || entity.liquidAmount <= 0.01f)
                && (tile.block() instanceof PressurisedConduit
                && (tile.block()) instanceof Conduit
                && entity.liquidAmount >= liquidCapacity);
    }

    @Override
    public void tryMoveLiquid(Tile tile, Tile next){
        LiquidPowerEntity ent = tile.entity();
        if((next.block() instanceof Conduit)&&ent.power < powerUsagePerTransfer)
            return;
        ent.power-=powerUsagePerTransfer;
        super.tryMoveLiquid(tile,next);
    }
}
