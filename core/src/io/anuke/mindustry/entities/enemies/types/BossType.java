package io.anuke.mindustry.entities.enemies.types;

import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.entities.enemies.EnemyType;

public class BossType extends EnemyType {

    public int bossTier;
    protected int phaseTotal = 1;
    protected boolean doPhases = false;

    public BossType(String name) {
        super(name);
    }

    @Override
    public void update(Enemy enemy) {
        super.update(enemy);
        if(doPhases&&enemy.health<=enemy.maxhealth/phaseTotal)
            enemy.phase=Math.round(enemy.health/(enemy.maxhealth/phaseTotal));
    }
}
