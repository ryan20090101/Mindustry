package io.anuke.mindustry.world.blocks.types;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Tile;

public interface LiquidAcceptor{
	
	boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount);
	void handleLiquid(Tile tile, Tile source, Liquid liquid, float amount);
	float getLiquid(Tile tile);
	float getLiquidCapacity(Tile tile);
}
