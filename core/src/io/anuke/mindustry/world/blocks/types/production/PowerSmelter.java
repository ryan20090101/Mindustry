package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerBlock;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Strings;

import java.util.Arrays;

public class PowerSmelter extends PowerBlock{
	protected final int timerDump = timers++;
	protected final int timerCraft = timers++;

	protected Item[] inputs;
	protected float powerUsage;
	protected Item result;

	protected float craftTime = 20f; //time to craft one item, so max 3 items per second by default
	protected Effect craftEffect = Fx.smelt;

	protected int capacity = 20;

	public PowerSmelter(String name) {
		super(name);
		update = true;
		solid = true;
	}

	@Override
	public void init(){
		PowerEntity ent = (PowerEntity) getEntity();
		for(Item item : inputs){
			bars.add(new BlockBar(Color.GREEN, true, tile -> (float)ent.getItem(item)/capacity));
		}
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[craftinfo]Input: " + Arrays.toString(inputs));
		list.add("[craftinfo]Power Usage: " + powerUsage);
		list.add("[craftinfo]Output: " + result);
		list.add("[craftinfo]Max output/second: " + Strings.toFixed(60f/craftTime, 1));
		list.add("[craftinfo]Input Capacity: " + capacity);
		list.add("[craftinfo]Output Capacity: " + capacity);
	}
	
	@Override
	public void update(Tile tile){
		PowerEntity entity = tile.entity();
		
		if(entity.timer.get(timerDump, 5) && entity.hasItem(result)){
			tryDump(tile, -1, result);
		}

		//make sure it has all the items
		for(Item item : inputs){
			if(!entity.hasItem(item)){
				return;
			}
		}

		if(entity.getItem(result) >= capacity //output full
				|| entity.power <= powerUsage //not enough power
				|| !entity.timer.get(timerCraft, craftTime)){ //not yet time
			return;
		}

		for(Item item : inputs){
			entity.removeItem(item, 1);
		}
		entity.power -= powerUsage;
		offloadNear(tile, result);
		Effects.effect(craftEffect, entity, entity.dimension);
	}

	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		boolean isInput = false;

		for(Item req : inputs){
			if(req == item){
				isInput = true;
				break;
			}
		}

		return (isInput && tile.entity.getItem(item) < capacity);
	}

	@Override
	public void draw(Tile tile){
		super.draw(tile);

		PowerEntity entity = tile.entity();

        //draw glowing center
        if(entity.power > 0){
            Draw.color(1f, 1f, 1f, Mathf.absin(Timers.time(), 9f, 0.4f) + Mathf.random(0.05f));
            Draw.rect("smelter-middle", tile.worldx(), tile.worldy());
            Draw.color();
        }
    }

	@Override
	public TileEntity getEntity() {
		return new PowerEntity();
	}
}
