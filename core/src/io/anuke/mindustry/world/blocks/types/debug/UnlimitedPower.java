package io.anuke.mindustry.world.blocks.types.debug;

import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerAcceptor;
import io.anuke.mindustry.world.blocks.types.production.Generator;

public class UnlimitedPower extends Generator { //star wars reference

    public UnlimitedPower(String name) {
        super(name);
    }
    
    @Override
    public void update(Tile tile){ distributeLaserPower(tile); }

    @Override
    protected void distributeLaserPower(Tile tile){
        PowerEntity entity = tile.entity();

        for(int i = 0; i < laserDirections; i++){
            int rot = (tile.getRotation() + i) - laserDirections / 2;
            Tile target = laserTarget(tile, rot);

            if(target == null)
                continue;

            PowerAcceptor p = (PowerAcceptor) target.block();
            if(p.acceptsPower(target)){
                p.addPower(target, 9999);
            }
        }
    }
}
