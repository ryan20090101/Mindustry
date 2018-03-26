package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.utils.Array;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidAcceptor;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Strings;

public class ReactorCooler extends LiquidBlock{
	protected int heat = 200;
	protected final int maxLiquid = 1000;


	public ReactorCooler(String name) {
		super(name);
		rotate = false;
		solid = true;
		layer = Layer.overlay;
	}

	@Override
	public boolean canReplace(Block other) {
		return other instanceof Pump && other != this;
	}

	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[liquidinfo]Liquid/second: eeck");
	}
	
	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidEntity entity = tile.entity();
		return entity.liquidAmount + entity.liquid2Amount < maxLiquid;
	}
	
	@Override
	public void draw(Tile tile){
		Draw.rect(name(), tile.worldx(), tile.worldy());
		
		LiquidEntity entity = tile.entity();
		
		if(entity.liquid == null) return;
		
		Draw.color(entity.liquid.color);
		Draw.alpha(entity.liquidAmount / liquidCapacity);
		Draw.rect("blank", tile.worldx(), tile.worldy(), 2, 2);
		Draw.color();
	}

	@Override
	public boolean isLayer(Tile tile) {
		LiquidEntity entity = tile.entity();
		return entity.liquid == null;
	}
	
	@Override
	public void update(Tile tile){
		LiquidEntity entity = tile.entity();
		if(entity.liquid != null) {
			if (entity.heat < entity.liquid.heat)
				entity.heat = entity.liquid.heat;
			while (entity.liquid == Liquid.water && entity.heat >= 100) {
				entity.liquidAmount -= 1;
				entity.liquid2 = Liquid.steam;
				entity.liquid2Amount += 2;
				entity.heat -= 1;
			}
		}
		tryDumpLiquid(tile);

	}
	@Override
	public void tryMoveLiquid(Tile tile, Tile next){
		LiquidEntity entity = tile.entity();

		Liquid liquid = entity.liquid2;

		if(next != null && next.block() instanceof LiquidAcceptor && entity.liquidAmount > 0.01f){
			LiquidAcceptor other = (LiquidAcceptor)next.block();

			float flow = Math.min(other.getLiquidCapacity(next) - other.getLiquid(next) - 0.001f,

						Math.min(entity.liquid2Amount/flowfactor * Math.max(Timers.delta(), 1f), entity.liquid2Amount));

			if(flow <= 0f || entity.liquid2Amount < flow) return;

			if(other.acceptLiquid(next, tile, liquid, flow)){
				other.handleLiquid(next, tile, liquid, flow);
				entity.liquid2Amount -= flow;
			}
		}
	}
	@Override
	public void tryDumpLiquid(Tile tile){
		LiquidEntity entity = tile.entity();

		if(entity.liquid2Amount > 0.01f){
			tryMoveLiquid(tile, tile.getNearby(tile.getDump()));
			tile.setDump((byte)Mathf.mod(tile.getDump() + 1, 4));
		}
	}
}
