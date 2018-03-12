package io.anuke.mindustry.world.blocks.types.distribution;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;

public class PressurisedConduit extends LiquidBlock {

    public PressurisedConduit(String name) {
        super(name);
    }

    @Override
    public boolean canReplace(Block other) {
        return other instanceof PressurisedConduit && other != this;
    }

    @Override
    public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
        LiquidEntity entity = tile.entity();

        return (entity.liquid == liquid || entity.liquidAmount <= 0.01f)
                && ((tile.block() instanceof PressurisedConduit
                && entity.liquidAmount >= liquidCapacity)||tile.block() instanceof PressurisedLoader);
    }

    @Override
    public void tryMoveLiquid(Tile tile, Tile next){
        if(!(next.block() instanceof PressurisedConduit)&&!(tile.block() instanceof PressurisedLoader))
            return;
        super.tryMoveLiquid(tile,next);
    }
}
