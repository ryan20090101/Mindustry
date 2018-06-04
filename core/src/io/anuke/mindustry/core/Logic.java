package io.anuke.mindustry.core;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.core.GameState.State;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.game.EnemySpawn;
import io.anuke.mindustry.game.EventType.GameOverEvent;
import io.anuke.mindustry.game.EventType.PlayEvent;
import io.anuke.mindustry.game.EventType.ResetEvent;
import io.anuke.mindustry.game.EventType.WaveEvent;
import io.anuke.mindustry.game.SpawnPoint;
import io.anuke.mindustry.game.WaveCreator;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.ProductionBlocks;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Events;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.modules.Module;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.*;

/**Logic module.
 * Handles all logic for entities and waves.
 * Handles game state events.
 * Does not store any game state itself.
 *
 * This class should <i>not</i> call any outside methods to change state of modules, but instead fire events.
 */
public class Logic extends Module {
    private final Array<EnemySpawn> spawns = WaveCreator.getSpawns();

    @Override
    public void init() {
        for(int i=0;i<dimensionIds;i++){
            world[i].ents.initPhysics();
            world[i].ents.collisions().setCollider(tilesize, world[i]::solid);
        }
    }

    public void play() {
        state.wavetime = wavespace * state.difficulty.timeScaling * 2;

        if (state.mode.infiniteResources) {
            state.inventory.fill();
        }

        Events.fire(PlayEvent.class);
    }

    public void reset() {
        state.wave = 1;
        state.extrawavetime = maxwavespace * state.difficulty.maxTimeScaling;
        state.wavetime = wavespace * state.difficulty.timeScaling;
        state.enemies = 0;
        state.lastUpdated = -1;
        state.gameOver = false;
        state.inventory.clearItems();

        Timers.clear();
        Entities.clear();

        Events.fire(ResetEvent.class);
    }

    public void runWave() {

        if (state.lastUpdated < state.wave + 1) {
            world[0].pathfinder().resetPaths();
            state.lastUpdated = state.wave + 1;
        }

        for (EnemySpawn spawn : spawns) {
            Array<SpawnPoint> spawns = world[0].getSpawns();

            for (int lane = 0; lane < spawns.size; lane++) {
                int fl = lane;
                Tile tile = spawns.get(lane).start;
                int spawnamount = spawn.evaluate(state.wave, lane);

                for (int i = 0; i < spawnamount; i++) {
                    float range = 12f;

                    Timers.runTask(i * 5f, () -> {

                        Enemy enemy = new Enemy(spawn.type);
                        enemy.set(tile.worldx() + Mathf.range(range), tile.worldy() + Mathf.range(range));
                        enemy.lane = fl;
                        enemy.tier = spawn.tier(state.wave, fl);
                        enemy.add();

                        //Effects.effect(Fx.spawn, enemy);

                        state.enemies++;
                    });
                }
            }
        }

        state.wave++;
        state.wavetime = wavespace * state.difficulty.timeScaling;
        state.extrawavetime = maxwavespace * state.difficulty.maxTimeScaling;

        Events.fire(WaveEvent.class);
    }

    @Override
    public void update() {

        for (int i = 0; i<dimensionIds; i++) {
            if (!state.is(State.menu)) {
                if (!Net.client())
                    world[i].pathfinder().update();
            }

            if (!state.is(State.paused) || Net.active()) {

<<<<<<< HEAD
                if (!state.mode.disableWaveTimer) {

                    if (state.enemies <= 0) {
                        if (state.lastUpdated < state.wave + 1 && state.wavetime < aheadPathfinding) { //start updating beforehand
                            world[i].pathfinder().resetPaths();
                            state.lastUpdated = state.wave + 1;
                        }
                    }
                }
=======
            if(control != null) control.triggerInputUpdate();

            if(!state.is(State.paused) || Net.active()){
                Timers.update();
>>>>>>> upstream/master
            }

            world[i].ents.collideGroups(world[i].bulletGroup, world[i].enemyGroup);
            world[i].ents.collideGroups(world[i].bulletGroup, world[i].playerGroup);

            world[i].ents.update(world[i].ents.defaultGroup());
            world[i].ents.update(world[i].bulletGroup);
            world[i].ents.update(world[i].enemyGroup);
            world[i].ents.update(world[i].tileGroup);
            world[i].ents.update(world[i].shieldGroup);
            world[i].ents.update(world[i].effectGroup);
            world[i].ents.update(world[i].playerGroup);
            world[i].ents.update(world[i].previewGroup);
        }

        if (!state.is(State.menu)) {

            if (!state.is(State.paused) || Net.active()) {
                Timers.update();
            }

            if (world[0].getCore() != null && world[0].getCore().block() != ProductionBlocks.core && !state.gameOver) {
                state.gameOver = true;
                if (Net.server()) NetEvents.handleGameOver();
                Events.fire(GameOverEvent.class);
            }

            if (!state.is(State.paused) || Net.active()) {

                if (!state.mode.disableWaveTimer) {

<<<<<<< HEAD
                    if (state.enemies <= 0) {
                        if (!world[0].getMap().name.equals("tutorial")) state.wavetime -= delta();
=======
                    if(state.enemies <= 0){
                        if(!world.getMap().name.equals("tutorial")) state.wavetime -= Timers.delta();
>>>>>>> upstream/master

                        if (state.lastUpdated < state.wave + 1 && state.wavetime < aheadPathfinding) { //start updating beforehand
                            world[0].pathfinder().resetPaths();
                            state.lastUpdated = state.wave + 1;
                        }
<<<<<<< HEAD
                    } else {
                        state.extrawavetime -= delta();
=======
                    }else if(!world.getMap().name.equals("tutorial")){
                        state.extrawavetime -= Timers.delta();
>>>>>>> upstream/master
                    }
                }

                if (!Net.client() && (state.wavetime <= 0 || state.extrawavetime <= 0)) {
                    runWave();
                }
                if (global.time >= maxTime) global.reversedTime = true;
                if (global.time <= 0) global.reversedTime = false;
                if(!global.reversedTime)global.time+= Timers.delta();
                else global.time-=Timers.delta();
            }
        }
    }
}