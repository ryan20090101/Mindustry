package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.utils.Array;

import io.anuke.mindustry.core.GameState;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.state;

public class LiquidDrill extends LiquidBlock{

    protected final int timerDrill = timers++;
    protected final int timerDump = timers++;

    protected Block resource;
    protected Item result;
    protected Liquid liquid;
    protected float liquidUsage;
    protected float time = 5;
    protected int capacity = 5;
    protected Effect drillEffect = Fx.spark;

    public LiquidDrill(String name) {
        super(name);
        update = true;
        solid = true;
        rotate = false;
        layer = Layer.overlay;
    }

    @Override
    public void getStats(Array<String> list){
        super.getStats(list);
        list.add("[iteminfo]Capacity: " + capacity);
        list.add("[iteminfo]Seconds/item: " + time);
    }

    @Override
    public void update(Tile tile){
        LiquidEntity entity = tile.entity();

        if((tile.floor() == resource || (resource.drops.equals(tile.floor().drops)))
                && entity.timer.get(timerDrill, 60 * time) && tile.entity.getItem(result) < capacity && entity.liquidAmount >= liquidUsage){
            entity.liquidAmount -= liquidUsage;
            offloadNear(tile, result);
            Effects.effect(drillEffect, tile.worldx(), tile.worldy());
        }

        if(entity.timer.get(timerDump, 30)){
            tryDump(tile);
        }
    }
    @Override
    public void draw(Tile tile){
        if(!isMultiblock()){
            Draw.rect(variants > 0 ? (name() + Mathf.randomSeed(tile.id(), 1, variants))  : name(),
                    tile.worldx(), tile.worldy(), rotate ? tile.getRotation() * 90 : 0);
        }else{
            Draw.rect(name(), tile.drawx(), tile.drawy());
        }

        if(destructible && !update && !state.is(GameState.State.paused)){
            tile.entity.update();
        }
    }

    @Override
    public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
        LiquidEntity entity = tile.entity();

        return liquid == liquid && entity.liquidAmount + amount < liquidCapacity && (entity.liquid == liquid || entity.liquidAmount <= 0.01f);
    }

    @Override
    public boolean isLayer(Tile tile){
        return tile.floor() != resource && resource != null && !(resource.drops.equals(tile.floor().drops));
    }

    @Override
    public void drawLayer(Tile tile){
        Draw.colorl(0.85f + Mathf.absin(Timers.time(), 6f, 0.15f));
        Draw.rect("cross", tile.worldx(), tile.worldy());
        Draw.color();
    }


    @Override
    public TileEntity getEntity(){
        return new LiquidEntity();
    }


}
