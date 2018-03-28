package io.anuke.mindustry.entities;

import com.badlogic.gdx.math.Vector2;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.world;

public abstract class AltDimEntity{
    private static int lastid;

    protected transient AltDimEntityGroup<?> group;

    /**Do not modify. Used for network operations.*/
    public int id;
    public int dimension;
    public float x,y;

    public void update(){}
    public void draw(){}
    public void drawOver(){}
    public void removed(){}
    public void added(){}

    public AltDimEntity(){
        id = lastid++;
    }

    public <T extends AltDimEntity> T set(float x, float y){
        this.x = x;
        this.y = y;
        return (T)this;
    }

    public <T extends AltDimEntity> T add(AltDimEntityGroup group){
        group.add(this);
        return (T)this;
    }

    public <T extends AltDimEntity> T add(){
        return (T) add(world[dimension].ents.defaultGroup());
    }

    public AltDimEntity remove(){
        if(group != null)
            ((AltDimEntityGroup)group).remove(this);
        removed();
        return this;
    }

    public AltDimEntityGroup<?> getGroup() {
        return group;
    }

    public float angleTo(AltDimEntity other){
        return Mathf.atan2(other.x - x, other.y - y);
    }

    public float angleTo(AltDimEntity other, float yoffset){
        return Mathf.atan2(other.x - x, other.y - (y+yoffset));
    }

    public float angleTo(float ox, float oy){
        return Mathf.atan2(ox - x, oy - y);
    }

    public float angleTo(AltDimEntity other, float xoffset, float yoffset){
        return Mathf.atan2(other.x - (x+xoffset), other.y - (y+yoffset));
    }

    public float distanceTo(AltDimEntity other){
        return Vector2.dst(other.x, other.y, x, y);
    }

    public float distanceTo(float ox, float oy){
        return Vector2.dst(ox, oy, x, y);
    }

    public float drawSize(){
        return 20;
    }

    public boolean isAdded(){
        return group != null;
    }

    public String toString(){
        return getClass() + " " + id;
    }
}
