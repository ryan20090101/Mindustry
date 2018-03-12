package io.anuke.mindustry.world.blocks;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.other.*;
import io.anuke.mindustry.world.blocks.types.debug.*;

public class OtherBlocks{
	public static final Block
    structurecore = new StructureCore("structurecore"){},
    powervoid = new PowerVoid("powervoid"){},
    powersource = new UnlimitedPower("powersource"),
	itemspawner = new ItemSpawner("itemspawner") {};
}
