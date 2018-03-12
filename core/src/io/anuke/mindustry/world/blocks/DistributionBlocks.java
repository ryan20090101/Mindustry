package io.anuke.mindustry.world.blocks;

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
		movementSpeedMultiplier = 1.5f;
        animated = true;
        animationFrames = 2;
	}},
	
	steelconveyor = new Conveyor("steelconveyor"){{
		movementSpeedMultiplier = 2f;
		health = 55;
		speed = 0.04f;
        animated = true;
        animationFrames = 2;
	}},
	
	pulseconveyor = new Conveyor("poweredconveyor"){{
		movementSpeedMultiplier = 3f;
		health = 75;
		speed = 0.09f;
        animated = true;
        animationFrames = 2;
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
		height = width = 3;
	}},

    coreIn = new CorePusher("coreIn"){{
    	height = width = 3;
	}},

	combiner = new Router("combiner"){{
        capacity = 5;
        bufferUpdate = true;
        rotate = true;
		selfAccept = false;
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
