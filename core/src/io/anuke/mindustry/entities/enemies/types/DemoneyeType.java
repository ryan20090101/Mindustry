package io.anuke.mindustry.entities.enemies.types;

import io.anuke.mindustry.entities.BulletType;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.entities.enemies.EnemyType;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;
import io.anuke.mindustry.entities.Player;
import static io.anuke.mindustry.Vars.*;
import io.anuke.ucore.entities.Entities;


public class DemoneyeType extends BossType {

	public DemoneyeType() {
		super("demoneye");
		phaseTotal = 3;
        doPhases = true;
		rotatespeed = 0.05f;
		reload = 30;
		health = 430;
		range = 100f;
		hitsize = 35f;
		domoving = false;
	}

	@Override
	public void updateShooting(Enemy enemy){
		/**Timers.get(enemy, "salvo", 240);
		
		if(Timers.getTime(enemy, "salvo") < 60){
			if(Timers.get(enemy, "salvoShoot", 6)){
				enemy.shoot(BulletType.flameshot, Mathf.range(20f));
			}
		}
		
		if(Timers.get(enemy, "shotgun", 80)){
			Angles.shotgun(5, 10f, 0f, f->{
				enemy.shoot(BulletType.smallSlow, f);
			});
		}
		
		if(Timers.get(enemy, "circle", 200)){
			Angles.circle(8, f->{
				enemy.shoot(BulletType.smallSlow, f);
			});
		}**/
        if(enemy.phase == 2){
            enemy.shoot(BulletType.demonring, 0f);
        }else if(enemy.phase == 3){
            enemy.shoot(BulletType.redlaser, 0f);
        }else{
            enemy.shoot(BulletType.blueBolt, 5f);
        }
        
	}
    @Override
    public void updateTargeting(Enemy enemy, boolean nearCore){
        if(enemy.target == null){
            enemy.target = Entities.getClosest(playerGroup, enemy.x, enemy.y, range, e -> !((Player)e).isFlying &&
                !((Player)e).isDead());
        }        
        if(enemy.target != null && bullet != null){
            updateShooting(enemy);
        }
    }
}
