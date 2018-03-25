package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.util.Strings;

public class SolarPowerGenerator extends Generator{
	public float powerOutput;

	public SolarPowerGenerator(String name) {
		super(name);
		outputOnly = true;
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[powerinfo]Power Generation/second: " + Strings.toFixed(powerOutput*60f, 2));
	}

	@Override
	public void update(Tile tile){
		PowerEntity entity = tile.entity();
		
		if (Vars.world[tile.dimension].time < Vars.maxTime/2) entity.power += powerOutput;
		
		distributeLaserPower(tile);
		
	}

}
