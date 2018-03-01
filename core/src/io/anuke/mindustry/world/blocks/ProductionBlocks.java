package io.anuke.mindustry.world.blocks;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.defense.CoreBlock;
import io.anuke.mindustry.world.blocks.types.production.*;

import java.util.HashMap;

public class ProductionBlocks{
	public static final Block
	
	core = new CoreBlock("core"){},
	
	pump = new Pump("pump"){},
	
	fluxpump = new Pump("fluxpump"){
		{
			pumpAmount = 3f;
		}
	},
	
	smelter = new Smelter("smelter"){
		{
			health = 70;
			inputs = new Item[]{Item.iron};
			fuel = Item.coal;
			result = Item.steel;
		}
	},
	
	crucible = new Smelter("crucible"){
		{
			health = 90;
			inputs = new Item[]{Item.titanium, Item.steel};
			fuel = Item.coal;
			result = Item.dirium;
			burnDuration = 40f;
			craftTime = 20f;
		}
	},

	crafter = new Crafter("crafter"){
		{
			health = 90;
			recipe = new HashMap<>();
			recipe.put(Item.steel,new Item[]{Item.coal,Item.iron});
		}
	},
	
	coalpurifier = new LiquidCrafter("coalpurifier"){
		{
			input = Item.stone;
			inputAmount = 5;
			inputLiquid = Liquid.water;
			liquidAmount = 18.99f;
			output = Item.coal;
			health = 50;
			purifyTime = 50;
		}
	},
	
	titaniumpurifier = new LiquidCrafter("titaniumpurifier"){
		{
			input = Item.iron;
			inputAmount = 6;
			inputLiquid = Liquid.water;
			liquidAmount = 40f;
			liquidCapacity = 41f;
			purifyTime = 60;
			output = Item.titanium;
			health = 70;
		}
	},
	
	oilrefinery = new LiquidCrafter("oilrefinery"){
		{
			inputLiquid = Liquid.oil;
			liquidAmount = 45f;
			liquidCapacity = 46f;
			purifyTime = 60;
			output = Item.coal;
			health = 80;
			craftEffect = Fx.purifyoil;
		}
	},
	
	stoneformer = new LiquidCrafter("stoneformer"){
		{
			input = null;
			inputLiquid = Liquid.lava;
			liquidAmount = 16f;
			liquidCapacity = 21f;
			purifyTime = 12;
			output = Item.stone;
			health = 80;
			craftEffect = Fx.purifystone;
		}
	},
	
	lavasmelter = new LiquidCrafter("lavasmelter"){
		{
			input = Item.iron;
			inputAmount = 1;
			inputLiquid = Liquid.lava;
			liquidAmount = 40f;
			liquidCapacity = 41f;
			purifyTime = 30;
			output = Item.steel;
			health = 80;
			craftEffect = Fx.purifystone;
		}
	},

	siliconextractor = new LiquidCrafter("siliconextractor"){
		{
			input = Item.sand;
			inputAmount = 5;
			inputLiquid = Liquid.water;
			liquidAmount = 18.99f;
			output = Item.sand;
			health = 50;
			purifyTime = 50;
		}
	},
	
	grounddrill = new Arraydrill("grounddrill"){
		{
			mineable = Array.with(
					Blocks.blackstone,
					Blocks.stone,
					Blocks.sand,
					Blocks.dirt,
					Blocks.grass
			);
			time = 4;
		}
	},
	
	irondrill = new Drill("irondrill"){
		{
			resource = Blocks.iron;
			result = Item.iron;
		}
	},
	
	coaldrill = new Drill("coaldrill"){
		{
			resource = Blocks.coal;
			result = Item.coal;
			time = 6;
		}
	},
	
	uraniumdrill = new Drill("uraniumdrill"){
		{
			resource = Blocks.uranium;
			result = Item.uranium;
			time = 7;
		}
	},
	
	titaniumdrill = new Drill("titaniumdrill"){
		{
			resource = Blocks.titanium;
			result = Item.titanium;
			time = 7;
		}
	},
	
	omnidrill = new Omnidrill("omnidrill"){
		{
			time = 4;
		}
	},
	coalgenerator = new ItemPowerGenerator("coalgenerator"){
		{
			generateItem = Item.coal;
			powerOutput = 0.04f;
			powerCapacity = 40f;
		}
	},
	thermalgenerator = new LiquidPowerGenerator("thermalgenerator"){
		{
			generateLiquid = Liquid.lava;
			maxLiquidGenerate = 0.5f;
			powerPerLiquid = 0.08f;
			powerCapacity = 40f;
			generateEffect = Fx.redgeneratespark;
		}
	},
	combustiongenerator = new LiquidPowerGenerator("combustiongenerator"){
		{
			generateLiquid = Liquid.oil;
			maxLiquidGenerate = 0.4f;
			powerPerLiquid = 0.12f;
			powerCapacity = 40f;
		}
	},
	rtgenerator = new ItemPowerGenerator("rtgenerator"){
		{
			generateItem = Item.uranium;
			powerCapacity = 40f;
			powerOutput = 0.03f;
			itemDuration = 240f;
		}
	},
	nuclearReactor = new NuclearReactor("nuclearreactor"){
		{
			width = 3;
			height = 3;
			health = 600;
			breaktime *= 2.3f;
		}
	},
	breederReactor = new NuclearReactor("breederReactor") {
		{
			width = 3;
			height = 3;
			health = 600;
			breaktime *= 2.3f;
			outputTime = 260;
			powerMultiplier = 0.0004f;
			heating = 0.0007f;
		}
	},
	sieve = new RandomCrafter("sieve") {
		{
			input = Item.dirt;
			output = Array.with(
					Item.iron,
					Item.stone,
					Item.gravel,
					Item.coal,
					Item.sand,
					Item.titanium,
					Item.tin,
					Item.copper
			);
			createEffect = Fx.purifystone;
		}
	},
	weaponFactory = new WeaponFactory("weaponfactory"){
		{
			width = height = 2;
			health = 250;
		}
	};
}
