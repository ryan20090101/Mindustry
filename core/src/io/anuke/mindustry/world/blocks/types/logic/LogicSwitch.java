package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicBlock;
import io.anuke.ucore.graphics.Draw;

public class LogicSwitch extends LogicBlock {
    public LogicSwitch(String name) {
        super(name);
        layer2 = Layer.laser;
    }

    @Override
    public void draw(Tile tile) {
        LogicEntity ent = tile.entity();
        Draw.rect(name()+(ent.selfActive ? "-on" : "-off"),tile.drawx(), tile.drawy());
    }

    @Override
    public void tapped(Tile tile){
        LogicEntity entity = tile.entity();
        entity.selfActive = !entity.selfActive;
        entity.outputActive = entity.selfActive;
        updateOutputLogic(tile);
        System.out.println(entity.selfActive);
        setConfigure(tile, (byte)(entity.selfActive ? 1 : 0));
    }

    @Override
    public void configure(Tile tile, byte... data) {
        LogicEntity entity = tile.entity();
        if(entity != null){
            entity.selfActive = data[0] == 1;
        }
    }
}
