package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.production.Generator;
import io.anuke.ucore.entities.Entities;

public class CoreMover extends Generator{

    public Color color = Color.valueOf("e54135");

    public CoreMover(String name) {
        super(name);
        solid = true;
        explosive = false;
        health = 50;
    }

    @Override
    public void update(Tile tile){
        PowerEntity ent = tile.entity();
    }

    @Override
    public boolean acceptsPower(Tile tile){
        PowerEntity entity = tile.entity();

        return entity.power + 0.001f <= powerCapacity;
    }
}
