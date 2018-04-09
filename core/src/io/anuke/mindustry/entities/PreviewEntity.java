package io.anuke.mindustry.entities;

import com.badlogic.gdx.math.Vector2;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.graphics.Draw;

public class PreviewEntity extends Entity {

    public Block parentBlock = Blocks.air;
    public boolean hidden;
    public float rotation = 0;

    @Override
    public void draw() {
        if(hidden) return;
        Draw.alpha(0.5f);
        Vector2 offset = parentBlock.getPlaceOffset();
        Draw.rect(parentBlock.name,x+offset.x,y+offset.y,rotation);
        Draw.reset();
    }
}
