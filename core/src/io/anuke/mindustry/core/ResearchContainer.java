package io.anuke.mindustry.core;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.resource.Research;

public class ResearchContainer {
    public ResearchContainer(Array<Research> researches) {
        this.researches = researches;
    }
    //TODO: this class is added to world and synced somehow (new packet maybe?)
    public Array<Research> researched;
    public Array<Research> researches;
}
