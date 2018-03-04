package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Bundles;

public class Research {
    private static final Array<Research> researches = new Array<>();

    public static final Research
            unlockOmnidrill = new Research("unlockOmnidrill"),
            unlockFluxpump = new Research("unlockFluxpump");

    public final int id;
    public final String name;
    public TextureRegion region;
    public boolean researched;

    public Research(String name) {
        this.id = researches.size;
        this.name = name;

        researches.add(this);
    }

    public void init(){
        this.region = Draw.region("icon-" + name);
    }

    public String localizedName(){
        return Bundles.get("research." + this.name + ".name");
    }

    @Override
    public String toString() {
        return localizedName();
    }
}