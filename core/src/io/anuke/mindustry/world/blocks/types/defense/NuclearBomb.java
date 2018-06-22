package io.anuke.mindustry.world.blocks.types.defense;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.entities.effect.DamageArea;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Translator;

import static io.anuke.mindustry.Vars.tilesize;

public class NuclearBomb extends LogicBlock {

    protected final Translator tr = new Translator();

    protected Item explosive = Item.plutonium;
    protected int itemCapacity = 10;
    protected int explosionRadius = 30;
    protected int explosionDamage = 300;

    public NuclearBomb(String name) {
        super(name);
        explosionEffect = Fx.nuclearShockwave;
        solid = true;
        destructible = true;

        bars.add(new BlockBar(Color.GREEN, true, tile -> (float)tile.entity.getItem(explosive) / itemCapacity));
    }

    @Override
    public boolean setLogic(Tile tile, Tile source, Boolean logicState) {
        LogicEntity ent = tile.entity();
        ent.selfActive = logicState;
        if(ent.selfActive) {
            onDestroyed(tile);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyed(Tile tile){
        super.onDestroyed(tile);

        TileEntity te = tile.entity();

        int waves = 6;
        float delay = 8f;

        for(int i = 0; i < waves; i ++){
            float rad = (float)i /waves * explosionRadius;
            Timers.run(i * delay, ()->{
                tile.damageNearby((int)rad, (explosionDamage / waves) * (te.getItem(explosive)/10), 0.4f);
            });
        }

        Effects.shake(6f, 16f, tile.worldx(), tile.worldy());
        Effects.effect(explosionEffect, tile.worldx(), tile.worldy(), tile.dimension);
        for(int i = 0; i < 6; i ++){
            Timers.run(Mathf.random(40), ()->{
                Effects.effect(Fx.nuclearcloud, tile.worldx(), tile.worldy(), tile.dimension);
            });
        }

        DamageArea.damageEntities(tile.worldx(), tile.worldy(), explosionRadius * tilesize, (explosionDamage * 4) * (te.getItem(explosive)/10));


        for(int i = 0; i < 20; i ++){
            Timers.run(Mathf.random(50), ()->{
                tr.rnd(Mathf.random(40f));
                Effects.effect(Fx.explosion, tr.x + tile.worldx(), tr.y + tile.worldy(), tile.dimension);
            });
        }

        for(int i = 0; i < 70; i ++){
            Timers.run(Mathf.random(80), ()->{
                tr.rnd(Mathf.random(120f));
                Effects.effect(Fx.nuclearsmoke, tr.x + tile.worldx(), tr.y + tile.worldy(), tile.dimension);
            });
        }
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        return item == explosive && tile.entity.getItem(explosive) < itemCapacity;
    }
}
