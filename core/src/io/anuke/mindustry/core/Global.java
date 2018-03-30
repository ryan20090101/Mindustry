package io.anuke.mindustry.core;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.ai.Pathfind;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.game.SpawnPoint;
import io.anuke.mindustry.io.Maps;
import io.anuke.mindustry.resource.Research;
import io.anuke.mindustry.world.*;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.mindustry.world.blocks.DistributionBlocks;
import io.anuke.mindustry.world.blocks.ProductionBlocks;
import io.anuke.mindustry.world.blocks.WeaponBlocks;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.modules.Module;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

import static io.anuke.mindustry.Vars.control;
import static io.anuke.mindustry.Vars.tilesize;

public class Global extends Module {
	private Maps maps = new Maps();

	public int time;
	public boolean reversedTime;
	private Array<Research> rContainer;
	public int bossAmount = 0;

	public Global() {
		maps.loadMaps();
		rContainer = Research.researches;
	}

	@Override
	public void dispose() {
		maps.dispose();
	}

	public Maps maps() {
		return maps;
	}

	public void research(Research res) {
		rContainer.get(res.id).researched = true;
	}

	public void research(int id) {
		rContainer.get(id).researched = true;
	}

	public void unresearch(Research res) {
		rContainer.get(res.id).researched = false;
	}

	public boolean getResearchStatus(Research res) {
		if (res == null) return true;
		return rContainer.get(res.id).researched;
	}

	public Research getResearchById(int id) {
		return rContainer.get(id);
	}
}
