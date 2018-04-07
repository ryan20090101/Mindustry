package io.anuke.mindustry.world.blocks;

import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.Wall;
import io.anuke.mindustry.world.blocks.types.defense.*;

import static io.anuke.mindustry.Vars.fire;

public class DefenseBlocks{
	static final int wallHealthMultiplier = 4;
	
	public static final Block

	stonewall = new Wall("stonewall"){{
		damageTypeDamageModifier.put(fire,0f);
		health = 40*wallHealthMultiplier;
	}},

	ironwall = new Wall("ironwall"){{
		health = 80*wallHealthMultiplier;
	}},

	steelwall = new Wall("steelwall"){{
		health = 110*wallHealthMultiplier;
	}},

	titaniumwall = new Wall("titaniumwall"){{
		health = 150*wallHealthMultiplier;
	}},
	diriumwall = new Wall("duriumwall"){{
		health = 190*wallHealthMultiplier;
	}},
	diriumalloywall = new Wall("alloywall"){{
		health = 190*2*wallHealthMultiplier;
	}},
	compositewall = new Wall("compositewall"){{
		health = 270*wallHealthMultiplier;
	}},
	steelwalllarge = new Wall("steelwall-large"){{
		health = 110*4*wallHealthMultiplier;
		size = 2;
	}},
	titaniumwalllarge = new Wall("titaniumwall-large"){{
		health = 150*4*wallHealthMultiplier;
		size = 2;
	}},
	diriumwalllarge = new Wall("duriumwall-large"){{
		health = 190*4*wallHealthMultiplier;
		size = 2;
	}},
	diriumalloywalllarge = new Wall("alloywall-large"){{
		health = 190*4*2*wallHealthMultiplier;
		size = 2;
	}},
	titaniumshieldwall = new ShieldedWallBlock("titaniumshieldwall"){{
		health = 150*wallHealthMultiplier;
	}},
	diriumshieldwall = new ShieldedWallBlock("diriumshieldwall"){{
		health = 190*wallHealthMultiplier;
	}},

	repairturret = new RepairTurret("repairturret"){
		{
			range = 30;
			reload = 20f;
			health = 60;
			powerUsed = 0.08f;
		}
	},

	megarepairturret = new RepairTurret("megarepairturret"){
		{
			range = 44;
      		reload = 12f;
			health = 90;
			powerUsed = 0.13f;
		}
	},

	ultrarepairturret = new RepairTurret("ultrarepairturret"){
		{
			size = 2;
			range = 120;
			reload = 5f;
			health = 160;
			powerUsed = 0.30f;
		}
	},

	shieldgenerator = new ShieldBlock("shieldgenerator"){
		{
			health = 100*wallHealthMultiplier;
		}
	},

	megaShield = new ShieldBlock("megaShield") {
		{
			health = 100 * wallHealthMultiplier;
			powerPerDamage = 0.001f;
			powerDrain = 0.1f;
			shieldRadius = 80f;
			maxRadius = 100f;
			size = 3;
		}
	},

	door = new Door("door"){{
		health = 90*wallHealthMultiplier;
	}},

	largedoor = new Door("door-large"){{
		openfx = Fx.dooropenlarge;
		closefx = Fx.doorcloselarge;
		health = 90*4*wallHealthMultiplier;
		size = 2;
	}};
}
