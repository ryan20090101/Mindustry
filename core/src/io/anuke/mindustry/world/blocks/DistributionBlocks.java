package io.anuke.mindustry.world.blocks;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.distribution.*;

public class DistributionBlocks{
	
	public static final Block
	
	conduit = new Conduit("conduit"){{
		health = 45;
	}},
	
	pulseconduit = new Conduit("pulseconduit"){{
		liquidCapacity = 16f;
		flowfactor = 4.9f;
		health = 65;
	}},

	pressurisedconduit = new PressurisedConduit("pressurisedconduit"){{
		liquidCapacity = 10f;
		flowfactor = 20f;
		health = 165;
	}},

	pressureLoader = new PressurisedLoader("pressureLoader"){{
				liquidCapacity = 36f;
				flowfactor = 20f;
				health = 165;
	}},

	steeltank = new Tank("steeltank"){{
		liquidCapacity = 50f;
		flowfactor = 10f;
	}},

	diriumtank = new Tank("diriumtank"){{
		liquidCapacity = 150f;
		flowfactor = 10f;
	}},
	
	liquidrouter = new LiquidRouter("liquidrouter"){{

	}},
	
	conveyor = new Conveyor("conveyor"){{
		activeMovementSpeedMultiplier = 1;
        animated = true;
        animationFrames = 2;
        activeMovement = true;
	}},
	
	steelconveyor = new Conveyor("steelconveyor"){{
		activeMovementSpeedMultiplier = 2;
		health = 55;
		speed = 0.04f;
        animated = true;
        animationFrames = 2;
		activeMovement = true;
	}},
	
	pulseconveyor = new Conveyor("poweredconveyor"){{
		activeMovementSpeedMultiplier = 3;
		health = 75;
		speed = 0.09f;
        animated = true;
        animationFrames = 2;
		activeMovement = true;
	}},

	ultraconveyor = new Conveyor("ultraconveyor"){{
		health = 75;
		speed = 0.14f;
		drawItems = false;
        animated = true;
        animationFrames = 3;
	}},
	
	router = new Router("router"){{

	}},

	buffer = new Router("buffer"){{
        capacity = 200;
        bufferUpdate = true;
		rotate = true;
	}},

	coreOut = new CorePuller("coreOut"){{
		size = 3;
	}},

    coreIn = new CorePusher("coreIn"){{
    	size = 3;
	}},

	combiner = new Combiner("combiner"){{
        rotate = true;
	}},
	
	junction = new Junction("junction"){{
		
	}},
	tunnel = new TunnelConveyor("conveyortunnel"){{
	}},
	liquidjunction = new LiquidJunction("liquidjunction"){{

	}},
	powerbooster = new PowerBooster("powerbooster"){{
		powerRange = 4;
	}},
	cooledPowerBooster = new PowerBooster("cooledPowerBooster"){{
		powerRange = 6;
		powerSpeed = 2f;
		size = 2;
		coolantUsage = 0.1f;
		generateLiquid = Liquid.water;
	}},
	powerlaser = new PowerLaser("powerlaser"){{
	}},
	powerlaserrouter = new PowerLaserRouter("powerlaserrouter"){{
	}},
	powerlasercorner = new PowerLaserRouter("powerlasercorner"){{
		laserDirections = 2;
	}},
	teleporter = new Teleporter("teleporter"){{
	}},
	sorter = new Sorter("sorter"){{
	}};
}
