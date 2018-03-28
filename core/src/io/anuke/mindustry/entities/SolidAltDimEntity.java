package io.anuke.mindustry.entities;

import com.badlogic.gdx.math.Rectangle;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.Hitbox;
import io.anuke.ucore.util.QuadTree;

import static io.anuke.mindustry.Vars.world;

public class SolidAltDimEntity extends AltDimEntity implements QuadTree.QuadTreeObject {
    public transient Hitbox hitbox = new Hitbox(10f);
    public transient Hitbox hitboxTile = new Hitbox(4f);
    public transient float lastX = Float.NaN, lastY = Float.NaN;

    public void move(float x, float y){
        world[0].ents.collisions().move(this, x, y);
    }

    public boolean collidesTile(){
        return Entities.collisions().overlapsTile(hitbox.getRect(x, y));
    }

    public boolean collides(SolidAltDimEntity other){
        return true;
    }

    public void collision(SolidAltDimEntity other, float x, float y){}

    @Override
    public void getBoundingBox(Rectangle out){
        hitbox.getRect(out, x, y);
    }
}