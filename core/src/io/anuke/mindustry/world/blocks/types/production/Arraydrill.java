package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.Floor;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

public class Arraydrill extends Drill {

    protected Array<Block> mineable;
    /**1 = normal, 2 = liquid, 3 = power */
    protected int mode = 1;

    public Arraydrill(String name){
        super(name);
        resource = null;
        result = null;
    }

    @Override
    public void draw(Tile tile){
        super.draw(tile);

        if(tile.floor().drops == null || mineable.contains(tile.floor(),true)) return;
        Item item = tile.floor().drops.item;

        TextureRegion region = item.region;
        Tmp.tr1.setRegion(region, 4, 4, 1, 1);

        Draw.rect(Tmp.tr1, tile.worldx(), tile.worldy(), 2f, 2f);
    }

    @Override
    public boolean canReplace(Block other) {
        return other instanceof Drill && other != this;
    }

    @Override
    public void update(Tile tile){
        LogicEntity entity = tile.entity();

        if(entity.selfActive)
            return;

        if(tile.floor().drops != null && mineable.contains(tile.floor(),true) && entity.timer.get(timerDrill, 60 * time)){
            offloadNear(tile, tile.floor().drops.item);
            Effects.effect(drillEffect, tile.worldx(), tile.worldy(), tile.dimension);
        }

        if(entity.timer.get(timerDump, 30)){
            tryDump(tile);
        }
    }
    @Override
    public boolean isLayer(Tile tile){
        return tile.floor() != resource && resource != null && !(mineable.contains(tile.floor(),true));
    }

    @Override
    public void drawLayer(Tile tile){
        Draw.colorl(0.85f + Mathf.absin(Timers.time(), 6f, 0.15f));
        Draw.rect("cross", tile.worldx(), tile.worldy());
        Draw.color();
    }
}
