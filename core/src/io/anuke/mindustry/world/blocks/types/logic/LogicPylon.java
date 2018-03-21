package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;
import io.anuke.ucore.graphics.Draw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

import static io.anuke.mindustry.Vars.tilesize;

public class LogicPylon extends LogicBlock {

	protected LogicPylon(String name) {
		super(name);
		solid = true;
		destructible = true;
        health = 50;
	}
}