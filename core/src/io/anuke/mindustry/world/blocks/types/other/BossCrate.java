package io.anuke.mindustry.world.blocks.types.other;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.entities.enemies.EnemyTypes;
import io.anuke.mindustry.resource.Weapon;

import static io.anuke.mindustry.Vars.control;

public class BossCrate extends Block{

	public BossCrate(String name) {
		super(name);
		solid = true;
		destructible = true;
        health = 250;
	}
	@Override
	public void tapped(Tile tile){
        control.upgrades().addWeapon(tile.block().lootPool[0 + (int)(Math.random() * ((tile.block().lootPool.length-1 - 0) + 1))]);
        setConfigure(tile, (byte)0, (byte)0);
    }

    public void configure(Tile tile, byte... data) {
        tile.entity.onDeath();
    }
}
