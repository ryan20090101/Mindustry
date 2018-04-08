package io.anuke.mindustry.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.ucore.util.Bits;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.tilesize;
import static io.anuke.mindustry.Vars.world;


public class Tile{
	public static final Object tileSetLock = new Object();
	private static final Array<Tile> tmpArray = new Array<>();

	public int dimension;
	
	/**block data. floor, underlay, block, overlay.*/
	private short[] blocks = new short[4];
	/**Packed data. Left is rotation, right is extra data, packed into two half-bytes: left is dump, right is extra.*/
	private short data;
	/**The coordinates of the core tile this is linked to, in the form of two bytes packed into one.
	 * This is relative to the block it is linked to; negate coords to find the link.*/
	public byte link = 0;
	public short x, y;
	/**Whether this tile has any solid blocks near it.*/
	public boolean occluded = false;
	public TileEntity entity;
	
	public Tile(int x, int y){
		this.x = (short)x;
		this.y = (short)y;
	}
	
	public Tile(int x, int y, Block floor){
		this(x, y);
		iSetFloor(floor);
	}

	public int packedPosition(){
		return x + y * world[dimension].width();
	}
	
	private void iSetFloor(Block floor){
		short id = (short) floor.id;
		blocks[0] = id;
	}

	private void iSetUnderlay(Block underlay){
		short id = (short) underlay.id;
		blocks[1] = id;
	}
	
	private void iSetBlock(Block wall){
		short id = (short) wall.id;
		blocks[2] = id;
	}

	private void iSetOverlay(Block overlay){
		short id = (short) overlay.id;
		blocks[3] = id;
	}

	public short getFloorID(){
		return blocks[0];
	}

	public short getUnderlayID() { return blocks[1]; }

	public short getWallID(){ return blocks[2]; }

	public short getOverlayID() { return blocks[3]; }

	/**Return relative rotation to a coordinate. Returns -1 if the coordinate is not near this tile.*/
	public byte relativeTo(int cx, int cy){
		if(x == cx && y == cy - 1) return 1;
		if(x == cx && y == cy + 1) return 3;
		if(x == cx - 1 && y == cy) return 0;
		if(x == cx + 1 && y == cy) return 2;
		return -1;
	}

	public byte sizedRelativeTo(int cx, int cy) {
		if (x == cx && y == cy - 1 - block().size / 2) return 1;
		if (x == cx && y == cy + 1 + block().size / 2) return 3;
		if (x == cx - 1 - block().size / 2 && y == cy) return 0;
		if (x == cx + 1 + block().size / 2 && y == cy) return 2;
		return -1;
	}
	
	public <T extends TileEntity> T entity(){
		return (T)entity;
	}
	
	public void damageNearby(int rad, int amount, float falloff){
		for(int dx = -rad; dx <= rad; dx ++){
			for(int dy = -rad; dy <= rad; dy ++){
				float dst = Vector2.dst(dx, dy, 0, 0);
				if(dst > rad || (dx == 0 && dy == 0)) continue;
				
				Tile other = world[dimension].tile(x + dx, y + dy);
				if(other != null && other.entity != null){
					other.entity.damage((int)(amount * Mathf.lerp(1f-dst/rad, 1f, falloff)));
				}
			}
		}
	}
	
	public int id(){
		return x + y * world[dimension].width();
	}
	
	public float worldx(){
		return x * tilesize;
	}
	
	public float worldy(){
		return y * tilesize;
	}

	public float drawx(){
		return block().getPlaceOffset().x + worldx();
	}

	public float drawy(){
		return block().getPlaceOffset().y + worldy();
	}
	
	public Block floor(){
		return Block.getByID(getFloorID());
	}
	
	public Block block(){
		return Block.getByID(getWallID());
	}

	public Block underlay(){
		return Block.getByID(getUnderlayID());
	}

	public Block overlay(){
		return Block.getByID(getOverlayID());
	}

	public Block floorOrBlock(){
		return Block.getByID(getWallID()) == Blocks.air ? floor() : block();
	}

	public Block topBlock(){
		if(Block.getByID(getOverlayID()) == Blocks.air) {
			if (Block.getByID(getWallID()) == Blocks.air)
				return Block.getByID(getUnderlayID()) == Blocks.air ? floor() : underlay();
			else
				return block();
		}else
			return overlay();

	}
	
	/**Returns the breaktime of the block, <i>or</i> the breaktime of the linked block, if this tile is linked.*/
	public float getBreakTime(){
		Block block = block();
		return link == 0 ? block.breaktime : getLinked().block().breaktime;
	}
	
	public void setBlock(Block type, int rotation){
		synchronized (tileSetLock) {
			if(rotation < 0) rotation = (-rotation + 2);
			iSetBlock(type);
			setRotation((byte) (rotation % 4));
			this.link = 0;
			changed();
		}
	}
	
	public void setBlock(Block type){
		synchronized (tileSetLock) {
			iSetBlock(type);
			this.link = 0;
			changed();
		}
	}
	
	public void setFloor(Block type){
		iSetFloor(type);
	}
	public void setOverlay(Block type){
		iSetOverlay(type);
	}
	public void setUnderlay(Block type){
		iSetUnderlay(type);
	}

	public void setRotation(byte rotation){
		data = Bits.packShort(rotation, Bits.getRightByte(data));
	}
	
	public void setDump(byte dump){
		data = Bits.packShort(getRotation(), Bits.packByte(dump, getExtra()));
	}
	
	public void setExtra(byte extra){
		data = Bits.packShort(getRotation(), Bits.packByte(getDump(), extra));
	}
	
	public byte getRotation(){
		return Bits.getLeftByte(data);
	}
	
	public byte getDump(){
		return Bits.getLeftByte(Bits.getRightByte(data));
	}
	
	public byte getExtra(){
		return Bits.getRightByte(Bits.getRightByte(data));
	}

	public short getPackedData(){
		return data;
	}

	public void setPackedData(short data){
		this.data = data;
	}

	public boolean passable(){
		Block block = block();
		Block floor = floor();
		return isLinked() || !((floor.solid && (block == Blocks.air || block.solidifes)) || (block.solid && (!block.destructible && !block.update)));
	}
	
	public boolean solid(){
		Block block = block();
		Block floor = floor();
		return block.solid || (floor.solid && (block == Blocks.air || block.solidifes)) || block.isSolidFor(this);
	}
	
	public boolean breakable(){
		Block block = block();
		if(link == 0){
			return (block.destructible || block.breakable || block.update);
		}else{
			return getLinked().breakable();
		}
	}
	
	public boolean isLinked(){
		return link != 0;
	}
	
	/**Sets this to a linked tile, which sets the block to a blockpart. dx and dy can only be -8-7.*/
	public void setLinked(byte dx, byte dy){
		setBlock(Blocks.blockpart);
		link = Bits.packByte((byte)(dx + 8), (byte)(dy + 8));
	}
	
	/**Returns the list of all tiles linked to this multiblock, or an empty array if it's not a multiblock.
	 * This array contains all linked tiles, including this tile itself.*/
	public synchronized Array<Tile> getLinkedTiles(Array<Tile> tmpArray) {
		Block block = block();
		tmpArray.clear();
		if (block.isMultiblock()) {
			int offsetx = -(block.size - 1) / 2;
			int offsety = -(block.size - 1) / 2;
			for (int dx = 0; dx < block.size; dx++) {
				for (int dy = 0; dy < block.size; dy++) {
					Tile other = world[dimension].tile(x + dx + offsetx, y + dy + offsety);
					tmpArray.add(other);
				}
			}
		} else {
			tmpArray.add(this);
		}
		return tmpArray;
	}
	
	/**Returns the block the multiblock is linked to, or null if it is not linked to any block.*/
	public Tile getLinked(){
		if(link == 0){
			return null;
		}else{
			byte dx = Bits.getLeftByte(link);
			byte dy = Bits.getRightByte(link);
			return world[dimension].tile(x - (dx - 8), y - (dy - 8));
		}
	}

	public Tile target(){
	    Tile link = getLinked();
	    return link == null ? this : link;
    }


	public Tile getNearby(int rotation){
		return getNearby(rotation,1);
	}

	public Tile getNearby(int rotation, int addition){
		if(rotation == 0) return world[dimension].tile(x + addition, y);
		if(rotation == 1) return world[dimension].tile(x, y + addition);
		if(rotation == 2) return world[dimension].tile(x - addition, y);
		if(rotation == 3) return world[dimension].tile(x, y - addition);
		return null;
	}

	/**WARNING: THIS IS EXTREMELY LAGGY
	public Tile getNearby(int rotation, int height, int width){
		Array<Tile> til = getNearbyArray(rotation, height, width);
		if (til == null)
			return null;
		return til.first();
	}
	public Array<Tile> getNearbyArray(int rotation, int height, int width){
		if(rotation >= 4 || height != width || height % 2 == 0) return null;
		Array<Tile> returnMe = new Array<>();
		if(height != 1) {
			height = (height + 2) / 2;
			width = (width + 2) / 2;
		}
		for (int dy = -height; dy < height+1; dy++) {
			for (int dx = -height; dx < width+1; dx++) {
				System.out.println(dx);
				System.out.println(dy);
				//corners
				if((dy == height && dx == width)
					|| (dy == height && dx == -width)
					|| (dy == -height && dx == width)
					|| (dy == -height && dx == -width)) continue;
				System.out.println("cor");
				//inside
				if((dx < width && dx > -width)
					&& (dy < height && dy > -height)) continue;
				System.out.println("ins");
				//check rotation
				if((rotation == 0 && dx != width)
					|| (rotation == 1 && dy != -height)
					|| (rotation == 2 && dx != -width)
					|| (rotation == 3 && dy != height)) continue;
				System.out.println("rot");
				returnMe.add(world.tile(x+dx,y+dy));
			}
		}
		return returnMe;
	}*/
	public Tile[] getNearby(Tile[] temptiles){
		temptiles[0] = world[dimension].tile(x+1, y);
		temptiles[1] = world[dimension].tile(x, y+1);
		temptiles[2] = world[dimension].tile(x-1, y);
		temptiles[3] = world[dimension].tile(x, y-1);
		return temptiles;
	}

	public void updateOcclusion(){
		occluded = false;
		for(int dx = -1; dx <= 1; dx ++){
			for(int dy = -1; dy <= 1; dy ++){
				Tile tile = world[dimension].tile(x + dx, y + dy);
				if(tile != null && tile.solid()){
					occluded = true;
					break;
				}
			}
		}
	}
	
	public void changed(){
		synchronized (tileSetLock) {
			if (entity != null) {
				entity.remove();
				entity = null;
			}

			Block block = block();

			if (block.destructible || block.update) {
				entity = block.getEntity().init(this, block.update);
			}

			updateOcclusion();
		}
	}
	
	@Override
	public String toString(){
		Block block = block();
		Block floor = floor();
		
		return floor.name() + ":" + block.name() + "[" + x + "," + y + "] " + "entity=" + (entity == null ? "null" : ClassReflection.getSimpleName(entity.getClass())) +
				(link != 0 ? " link=[" + (Bits.getLeftByte(link) - 8) + ", " + (Bits.getRightByte(link) - 8) +  "]" : "");
	}
}
