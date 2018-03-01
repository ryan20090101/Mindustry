package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;

public class RandomCrafter extends Block {

    protected final int timerDump = timers++;
    protected final int timerCraft = timers++;

    protected int capacity = 20;
    protected double craftChance = 0.10;
    protected int craftTime = 5;

    protected Effect createEffect = Fx.generate;

    protected Item input;
    protected Array<Item> output;

    public RandomCrafter(String name) {
        super(name);
        update = true;
        solid = true;
        bars.add(new BlockBar(Color.GREEN, true, tile -> (float) tile.entity.totalItems() / capacity));
    }


    @Override
    public void draw(Tile tile){
        Draw.rect(name(), tile.worldx(), tile.worldy());

        TileEntity entity = tile.entity();

        if(!entity.hasItem(input)) return;

        Draw.color(Color.YELLOW);
        Draw.alpha(entity.getItem(input) / capacity);
        Draw.rect(name+"-center", tile.worldx(), tile.worldy(), 2, 2);
        Draw.color();
    }

    @Override
    public void getStats(Array<String> list) {
        super.getStats(list);

        list.add("[craftinfo]Input: " + input.name);

        for (Item item : output.items) {
            list.add("[craftinfo]Output: " + item.name);
        }

    }

    @Override
    public void update(Tile tile) {
        TileEntity ent = tile.entity();

        if (tile.entity.timer.get(timerDump, 20)) {
            tryDump(tile);
        }

        Item product = output.random();

        if (ent.getItem(product) >= capacity //output full //successful?
                || !ent.hasItem(input) //has input item?
                || !ent.timer.get(timerCraft,craftTime)) //time yet?
        {
            return;
        }

        if (!Mathf.chance(craftChance)) {
            ent.removeItem(input,1);
            return;
        }
        else {
            ent.removeItem(input,1);
        }

        offloadNear(tile, product);
        Effects.effect(createEffect, tile.entity);
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        return item == input && tile.entity.totalItems() < capacity;
    }
}
