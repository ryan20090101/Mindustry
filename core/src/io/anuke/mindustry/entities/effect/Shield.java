package io.anuke.mindustry.entities.effect;

import com.badlogic.gdx.math.Interpolation;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.defense.ShieldBlock;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.BulletEntity;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.*;

public class Shield extends Entity {
	public boolean active;
	public boolean hitPlayers = false;
	public float radius = 0f;
	public int dimension;
	
	private float uptime = 0f;
	private Tile tile;
	private boolean portable = false;

	public Shield(Tile tile){
		this.tile = tile;
		this.x = tile.worldx();
		this.y = tile.worldy();
	}

	public Shield(int x, int y){
		this.x = x;
		this.y = y;
		this.portable = true;
	}
	
	public float drawSize(){
		return 150;
	}
	
	@Override
	public void update(){
		float alpha = 0.1f;
		Interpolation interp = Interpolation.fade;
		
		if(active){
			uptime = interp.apply(uptime, 1f, alpha * Timers.delta());
		}else{
			uptime = interp.apply(uptime, 0f, alpha * Timers.delta());
			if(uptime <= 0.05f)
				remove();
		}
		uptime = Mathf.clamp(uptime);
		
		if(!(tile.block() instanceof ShieldBlock) && !portable){
			remove();
			return;
		}
		if(!portable) {
			ShieldBlock block = (ShieldBlock) tile.block();

			world[dimension].ents.getNearby(world[dimension].bulletGroup, x, y, block.shieldRadius * 2 * uptime + 10, entity -> {
				BulletEntity bullet = (BulletEntity) entity;
				if ((bullet.owner instanceof Enemy || hitPlayers)) {

					float dst = entity.distanceTo(this);

					if (dst < drawRadius() / 2f) {
						((ShieldBlock) tile.block()).handleBullet(tile, bullet);
					}
				}
			});
		}else{
			world[dimension].ents.getNearby(world[dimension].bulletGroup, x, y, this.radius, entity -> {
				BulletEntity bullet = (BulletEntity) entity;
				if ((bullet.owner instanceof Enemy || hitPlayers)) {

					float dst = entity.distanceTo(this);

					if (dst < drawRadius() / 2f) {
						if (this.radius > bullet.getDamage() * 0.05f) {
							bullet.remove();
							if (!headless) renderer.addShieldHit(bullet.x, bullet.y);
							this.radius -= bullet.getDamage() * 0.005f;
						}


						//Effects.effect(bullet.damage > 5 ? Fx.shieldhit : Fx.laserhit, bullet);


					}
				}
			});
		}
	}
	
	@Override
	public void draw(){
		if(!(tile.block() instanceof ShieldBlock) || radius <= 1f){
			return;
		}
		
		float rad = drawRadius();
		Draw.rect("circle2", x, y, rad, rad);
	}
	
	float drawRadius(){
		return (radius*2 + Mathf.sin(Timers.time(), 25f, 2f));
	}
	
	public void removeDelay(){
		active = false;
	}
	
	@Override
	public Shield add(){
		return super.add(world[dimension].shieldGroup);
	}
	
	@Override
	public void added(){
		active = true;
	}
	
	@Override
	public void removed(){
		active = false;
		uptime = 0f;
	}
	
}