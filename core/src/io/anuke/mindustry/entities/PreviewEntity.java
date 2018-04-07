package io.anuke.mindustry.entities;

import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.graphics.Draw;

public class PreviewEntity extends Entity {

    public String parentBlock;
    public boolean hidden;
    public float rotation = 0;

    @Override
    public void draw() {
        if(hidden) return;
        Draw.alpha(0.5f);
        Draw.rect(parentBlock,x,y,rotation);
        Draw.reset();
    }
}
