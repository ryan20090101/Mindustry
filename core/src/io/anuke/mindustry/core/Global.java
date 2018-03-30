package io.anuke.mindustry.core;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.io.Maps;
import io.anuke.mindustry.resource.Research;
import io.anuke.ucore.modules.Module;

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
