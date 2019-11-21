package io.anuke.mindustry.game;

import io.anuke.arc.util.ArcAnnotate.*;
import io.anuke.mindustry.core.Events;
import io.anuke.mindustry.core.Events.Event;
import io.anuke.mindustry.core.GameState.State;
import io.anuke.mindustry.ctype.UnlockableContent;
import io.anuke.mindustry.entities.traits.BuilderTrait;
import io.anuke.mindustry.entities.type.*;
import io.anuke.mindustry.entities.units.*;
import io.anuke.mindustry.type.*;
import io.anuke.mindustry.world.Tile;

public class EventType{

    //events that occur very often
    /*
    public enum Trigger{
        shock,
        phaseDeflectHit,
        impactPower,
        thoriumReactorOverheat,
        itemLaunch,
        fireExtinguish,
        newGame,
        tutorialComplete,
        flameAmmo,
        turretCool,
        enablePixelation,
        drown,
        exclusionDeath,
        suicideBomb,
        openWiki,
        teamCoreDamage
    }
    */

    public static class ShockEvent implements Event{}

    public static class PhaseDeflectHitEvent implements Event{}

    public static class ImpactPowerEvent implements Event{}

    public static class ThoriumReactorOverheatEvent implements Event{}

    public static class ItemLaunchEvent implements Event{}

    public static class FireExtinguishEvent implements Event{}

    public static class NewGameEvent implements Event{}

    public static class TutorialCompleteEvent implements Event{}

    public static class FlameAmmoEvent implements Event{}

    public static class TurretCoolEvent implements Event{}

    public static class EnablePixelationEvent implements Event{}

    public static class DrownEvent implements Event{}

    public static class ExclusionDeathEvent implements Event{}

    public static class SuicideBombEvent implements Event{}

    public static class OpenWikiEvent implements Event{}

    public static class TeamCoreDamageEvent implements Event{}

    public static class WinEvent implements Event{}

    public static class LoseEvent implements Event{}

    public static class LaunchEvent implements Event{}

    public static class MapMakeEvent implements Event{}

    public static class MapPublishEvent implements Event{}

    public static class CommandIssueEvent implements Event{
        public Tile tile;
        public UnitCommand command;

        public void set(Tile tile, UnitCommand command){
            this.tile = tile;
            this.command = command;
        }

        public void reset(){
            tile = null;
            command = null;
        }
    }

    public static class PlayerChatEvent implements Event{
        public Player player;
        public String message;

        public void set(Player player, String message){
            this.player = player;
            this.message = message;
        }

        public void reset(){
            player = null;
            message = null;
        }
    }

    /** Called when a zone's requirements are met. */
    public static class ZoneRequireCompleteEvent implements Event{
        public Zone zoneMet, zoneForMet;
        public Objective objective;

        public void set(Zone zoneMet, Zone zoneForMet, Objective objective){
            this.zoneMet = zoneMet;
            this.zoneForMet = zoneForMet;
            this.objective = objective;
        }

        public void reset(){
            zoneMet = null;
            zoneForMet = null;
            objective = null;
        }
    }

    /** Called when a zone's requirements are met. */
    public static class ZoneConfigureCompleteEvent implements Event{
        public Zone zone;

        public void set(Zone zone){
            this.zone = zone;
        }

        public void reset(){
            zone = null;
        }
    }

    /** Called when the client game is first loaded. */
    public static class ClientLoadEvent implements Event{}

    public static class ContentReloadEvent implements Event{}

    public static class DisposeEvent implements Event{}

    public static class PlayEvent implements Event{}

    public static class ResetEvent implements Event{}

    public static class WaveEvent implements Event{}

    /** Called when the player places a line, mobile or desktop.*/
    public static class LineConfirmEvent implements Event{}

    /** Called when a turret recieves ammo, but only when the tutorial is active! */
    public static class TurretAmmoDeliverEvent implements Event{}

    /** Called when a core recieves ammo, but only when the tutorial is active! */
    public static class CoreItemDeliverEvent implements Event{}

    /** Called when the player opens info for a specific block.*/
    public static class BlockInfoEvent implements Event{}

    /** Called when a player withdraws items from a block. Tutorial only.*/
    public static class WithdrawEvent implements Event{}

    /** Called when a player deposits items to a block.*/
    public static class DepositEvent implements Event{
        public Tile tile;
        public Player player;
        public Item item;
        public int amount;
        
        public void set(Tile tile, Player player, Item item, int amount){
            this.tile = tile;
            this.player = player;
            this.item = item;
            this.amount = amount;
        }

        public void reset(){
            tile = null;
            player = null;
            item = null;
            amount = 0;
        }
    }
    
    /** Called when the player taps a block. */
    public static class TapEvent implements Event{
        public Tile tile;
        public Player player;

        public void set(Tile tile, Player player){
            this.tile = tile;
            this.player = player;
        }

        public void reset(){
            tile = null;
            player = null;
        }
    }
    
    /** Called when the player sets a specific block. */
    public static class TapConfigEvent implements Event{
        public Tile tile;
        public Player player;
        public int value;

        public void set(Tile tile, Player player, int value){
            this.tile = tile;
            this.player = player;
            this.value = value;
        }

        public void reset(){
            tile = null;
            player = null;
            value = 0;
        }
    }

    public static class GameOverEvent implements Event{
        public Team winner;

        public void set(Team winner){
            this.winner = winner;
        }

        public void reset(){
            winner = null;
        }
    }

    /** Called when a game begins and the world is loaded. */
    public static class WorldLoadEvent implements Event{}

    /** Called from the logic thread. Do not access graphics here! */
    public static class TileChangeEvent implements Event{
        public Tile tile;

        public void set(Tile tile){
            this.tile = tile;
        }

        public void reset(){
            tile = null;
        }
    }

    public static class StateChangeEvent implements Event{
        public State from, to;

        public void set(State from, State to){
            this.from = from;
            this.to = to;
        }

        public void reset(){
            from = null;
            to = null;
        }
    }

    public static class UnlockEvent implements Event{
        public UnlockableContent content;

        public void set(UnlockableContent content){
            this.content = content;
        }

        public void reset(){
            content = null;
        }
    }

    public static class ResearchEvent implements Event{
        public UnlockableContent content;

        public void set(UnlockableContent content){
            this.content = content;
        }

        public void reset(){
            content = null;
        }
    }

    /**
     * Called when block building begins by placing down the BuildBlock.
     * The tile's block will nearly always be a BuildBlock.
     */
    public static class BlockBuildBeginEvent implements Event{
        public Tile tile;
        public Team team;
        public boolean breaking;

        public void set(Tile tile, Team team, boolean breaking){
            this.tile = tile;
            this.team = team;
            this.breaking = breaking;
        }

        public void reset(){
            tile = null;
            team = null;
            breaking = false;
        }
    }

    public static class BlockBuildEndEvent implements Event{
        public Tile tile;
        public Team team;
        public @Nullable
        Player player;
        public boolean breaking;

        public void set(Tile tile, @Nullable Player player, Team team, boolean breaking){
            this.tile = tile;
            this.team = team;
            this.player = player;
            this.breaking = breaking;
        }

        public void reset(){
            tile = null;
            team = null;
            player = null;
            breaking = false;
        }
    }

    /**
     * Called when a player or drone begins building something.
     * This does not necessarily happen when a new BuildBlock is created.
     */
    public static class BuildSelectEvent implements Event{
        public Tile tile;
        public Team team;
        public BuilderTrait builder;
        public boolean breaking;

        public void set(Tile tile, Team team, BuilderTrait builder, boolean breaking){
            this.tile = tile;
            this.team = team;
            this.builder = builder;
            this.breaking = breaking;
        }

        public void reset(){
            tile = null;
            team = null;
            builder = null;
            breaking = false;
        }
    }

    /** Called right before a block is destroyed.
     * The tile entity of the tile in this event cannot be null when this happens.*/
    public static class BlockDestroyEvent implements Event{
        public Tile tile;

        public void set(Tile tile){
            this.tile = tile;
        }

        public void reset(){
            tile = null;
        }
    }

    public static class UnitDestroyEvent implements Event{
        public Unit unit;

        public void set(Unit unit){
            this.unit = unit;
        }

        public void reset(){
            unit = null;
        }
    }

    public static class UnitCreateEvent implements Event{
        public BaseUnit unit;

        public void set(BaseUnit unit){
            this.unit = unit;
        }

        public void reset(){
            unit = null;
        }
    }

    public static class ResizeEvent implements Event{}

    public static class MechChangeEvent implements Event{
        public Player player;
        public Mech mech;

        public void set(Player player, Mech mech){
            this.player = player;
            this.mech = mech;
        }

        public void reset(){
            player = null;
            mech = null;
        }
    }

    /** Called after connecting; when a player recieves world data and is ready to play.*/
    public static class PlayerJoin implements Event{
        public Player player;
        
        public void set(Player player){
            this.player = player;
        }

        public void reset(){
            player = null;
        }
    }

    /** Called when a player connects, but has not joined the game yet.*/
    public static class PlayerConnect implements Event{
        public Player player;

        public void set(Player player){
            this.player = player;
        }

        public void reset(){
            player = null;
        }
    }

    public static class PlayerLeave implements Event{
        public Player player;

        public void set(Player player){
            this.player = player;
        }

        public void reset(){
            player = null;
        }
    }
}

