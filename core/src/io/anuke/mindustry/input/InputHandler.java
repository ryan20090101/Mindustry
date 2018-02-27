package io.anuke.mindustry.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.resource.Recipe;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Placement;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.ucore.core.Graphics;
<<<<<<< HEAD
import io.anuke.ucore.core.Inputs;
import io.anuke.ucore.core.Sounds;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Input;
=======
>>>>>>> upstream/master
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.*;

public abstract class InputHandler extends InputAdapter{
	public float breaktime = 0;
	public Recipe recipe;
	public int rotation;
	public PlaceMode placeMode = android ? PlaceMode.cursor : PlaceMode.hold;
	public PlaceMode breakMode = android ? PlaceMode.none : PlaceMode.holdDelete;
	public PlaceMode lastPlaceMode = placeMode;
	public PlaceMode lastBreakMode = breakMode;

	public abstract void update();
	public abstract float getCursorX();
	public abstract float getCursorY();
	public abstract float getCursorEndX();
	public abstract float getCursorEndY();
	public int getBlockX(){ return Mathf.sclb(Graphics.world(getCursorX(), getCursorY()).x, tilesize, round2()); }
	public int getBlockY(){ return Mathf.sclb(Graphics.world(getCursorX(), getCursorY()).y, tilesize, round2()); }
	public int getBlockEndX(){ return Mathf.sclb(Graphics.world(getCursorEndX(), getCursorEndY()).x, tilesize, round2()); }
	public int getBlockEndY(){ return Mathf.sclb(Graphics.world(getCursorEndX(), getCursorEndY()).y, tilesize, round2()); }
	public void resetCursor(){}
	public boolean drawPlace(){ return true; }
	
	public boolean onConfigurable(){
		Tile tile = world.tile(getBlockX(), getBlockY());
		return tile != null && (tile.block().isConfigurable(tile) || (tile.isLinked() && tile.getLinked().block().isConfigurable(tile)));
	}
	
	public boolean cursorNear(){
		return Vector2.dst(player.x, player.y, getBlockX() * tilesize, getBlockY() * tilesize) <= placerange;
	}
	
	public boolean tryPlaceBlock(int x, int y, boolean sound){
		if(recipe != null && 
				validPlace(x, y, recipe.result) && !ui.hasMouse() && cursorNear() &&
				state.inventory.hasItems(recipe.requirements)){
			
			placeBlock(x, y, recipe.result, rotation, true, sound);
			
			for(ItemStack stack : recipe.requirements){
				state.inventory.removeItem(stack);
			}
			return true;
		}
		return false;
	}
	
	public boolean tryDeleteBlock(int x, int y, boolean sound){
		if(cursorNear() && validBreak(x, y)){
			breakBlock(x, y, sound);
			return true;
		}
		return false;
	}
	
	public boolean round2(){
		return !(recipe != null && recipe.result.isMultiblock() && recipe.result.height % 2 == 0);
	}
	
	public boolean validPlace(int x, int y, Block type){
<<<<<<< HEAD

		for(int i = 0; i < world.getSpawns().size; i ++){
			SpawnPoint spawn = world.getSpawns().get(i);
			if(Vector2.dst(x * tilesize, y * tilesize, spawn.start.worldx(), spawn.start.worldy()) < enemyspawnspace){
				return false;
			}
		}
		
		rect.setSize(type.width * tilesize, type.height * tilesize);
		Vector2 offset = type.getPlaceOffset();
		rect.setCenter(offset.x + x * tilesize, offset.y + y * tilesize);

		synchronized (Entities.entityLock) {
			for (SolidEntity e : Entities.getNearby(enemyGroup, x * tilesize, y * tilesize, tilesize * 2f)) {
				if (e == null) continue; //not sure why this happens?
				Rectangle rect = e.hitbox.getRect(e.x, e.y);

				if (this.rect.overlaps(rect)) {
					return false;
				}
			}
		}

		if(type.solid || type.solidifes) {
			for (Player player : playerGroup.all()) {
				if (!player.isAndroid && rect.overlaps(player.hitbox.getRect(player.x, player.y))) {
					return false;
				}
			}
		}
		
		Tile tile = world.tile(x, y);
		
		if(tile == null || (isSpawnPoint(tile) && (type.solidifes || type.solid))) return false;
=======
>>>>>>> upstream/master
		
		if(!type.isMultiblock() && control.tutorial().active() &&
				control.tutorial().showBlock()){
			
			GridPoint2 point = control.tutorial().getPlacePoint();
			int rotation = control.tutorial().getPlaceRotation();
			Block block = control.tutorial().getPlaceBlock();
			
			if(type != block || point.x != x - world.getCore().x || point.y != y - world.getCore().y
					|| (rotation != -1 && rotation != this.rotation)){
				return false;
			}
		}else if(control.tutorial().active()){
			return false;
		}

		return Placement.validPlace(x, y, type);
	}
	
	public boolean validBreak(int x, int y){
		if(control.tutorial().active()){
			
			if(control.tutorial().showBlock()){
				GridPoint2 point = control.tutorial().getPlacePoint();
				int rotation = control.tutorial().getPlaceRotation();
				Block block = control.tutorial().getPlaceBlock();
			
				if(block != Blocks.air || point.x != x - world.getCore().x || point.y != y - world.getCore().y
						|| (rotation != -1 && rotation != this.rotation)){
					return false;
				}
			}else{
				return false;
			}
		}
		
		return Placement.validBreak(x, y);
	}
	
	public void placeBlock(int x, int y, Block result, int rotation, boolean effects, boolean sound){
		if(!Net.client()){
			Placement.placeBlock(x, y, result, rotation, effects, sound);
			Tile tile = world.tile(x, y);
			if(tile != null) result.placed(tile);
		}

		if(Net.active()){
			NetEvents.handlePlace(x, y, result, rotation);
		}
	}

	public void breakBlock(int x, int y, boolean sound){
		if(!Net.client()) Placement.breakBlock(x, y, sound, sound);

		if(Net.active()){
			NetEvents.handleBreak(x, y);
		}
	}
}
