package io.anuke.mindustry.world.blocks.types.debug;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerBlock;
import io.anuke.mindustry.world.blocks.types.distribution.PowerLaser;

public class PowerVoid extends PowerBlock {
    public Color color = Color.valueOf("e54135");

    public PowerVoid(String name) {
        super(name);
        solid = true;
    }

    @Override
    public boolean canReplace(Block other) {
        return other instanceof PowerLaser && other != this;
    }

    @Override
    public boolean acceptsPower(Tile tile){ return true; }

    @Override
    public float addPower(Tile tile, float amount){ return amount; }
}
