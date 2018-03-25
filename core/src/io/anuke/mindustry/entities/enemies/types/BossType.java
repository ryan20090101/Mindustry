package io.anuke.mindustry.entities.enemies.types;

import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.entities.enemies.EnemyType;

import static io.anuke.mindustry.Vars.global;

public class BossType extends EnemyType {

    public int bossTier = 1;
    protected int phaseTotal;
    protected boolean doPhases = false;

    public BossType(String name) {
        super(name);
    }

    @Override
    public void update(Enemy enemy) {
        super.update(enemy);
        int currentPhase = Math.round(enemy.health/(enemy.maxhealth/phaseTotal));
        enemy.phase = doPhases && enemy.phase != currentPhase ? currentPhase : enemy.phase;
        enemy.idletime = 0f;
    }

    public void added(Enemy enemy){
        global.bossAmount++;
        super.added(enemy);
    }

    @Override
    public void removed(Enemy enemy){
        global.bossAmount--;
        super.removed(enemy);
    }
}
