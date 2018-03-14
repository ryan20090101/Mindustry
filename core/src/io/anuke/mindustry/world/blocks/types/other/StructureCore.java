package io.anuke.mindustry.world.blocks.types.other;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.Vars.*;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.mindustry.world.blocks.DistributionBlocks;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.entities.enemies.EnemyTypes;

public class StructureCore extends Block{

	public StructureCore(String name) {
		super(name);
		solid = true;
		destructible = true;
        health = 250;
	}
	@Override
	public void tapped(Tile tile){
        if(Vars.world.tile(tile.x, tile.y+2).block()==DistributionBlocks.conveyor){
            Vars.world.tile(tile.x, tile.y+2).setBlock(Blocks.air);
            Enemy enemy = new Enemy(EnemyTypes.demoneye);
            enemy.set(tile.x*8, tile.y*8);
            enemy.add();
        }
	}
}
