package io.anuke.mindustry.world.blocks;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.logic.*;

public class LogicBlocks{
	public static final Block
    toggleConveyor = new ToggleConveyor("toggleconveyor"){},
	logicSwitch = new LogicSwitch("logicSwitch"){},
	notgate = new NOTGate("notgate"){},
	andgate = new ANDGate("andgate"){},
	logicpylon = new LogicPylon("logicpylon"){};
}
