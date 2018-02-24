package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Strings;

public class SolarPowerGenerator extends Generator{
	public float powerOutput;
	public Effect generateEffect = Fx.generatespark;
	public Color heatColor = Color.valueOf("ff9b59");

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
		
		if (Vars.world.time < Vars.maxTime/2) entity.power += powerOutput;
		
		distributeLaserPower(tile);
		
	}

}
