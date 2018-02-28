package io.anuke.mindustry.world.blocks.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LiquidPowerBlock extends Block implements PowerAcceptor, LiquidAcceptor{
	protected final int timerFlow = timers++;

	protected float liquidCapacity = 10f;
	protected float flowfactor = 4.9f;
	public float powerCapacity = 10f;
	public float voltage = 0.001f;

	public LiquidPowerBlock(String name) {
		super(name);
		rotate = true;
		update = true;

		bars.add(new BlockBar(Color.YELLOW, true, tile -> tile.<PowerBlock.PowerEntity>entity().power / powerCapacity));
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[liquidinfo]Liquid Capacity: " + liquidCapacity);
		list.add("[powerinfo]Power Capacity: " + powerCapacity);
	}
	
	@Override
	public void update(Tile tile){
		LiquidPowerEntity entity = tile.entity();
		
		if(entity.liquidAmount > 0.01f && entity.timer.get(timerFlow, 1)){
			tryMoveLiquid(tile, tile.getNearby(tile.getRotation()));
		}
		
	}
	
	public void tryDumpLiquid(Tile tile){
		LiquidPowerEntity entity = tile.entity();
		
		if(entity.liquidAmount > 0.01f){
			tryMoveLiquid(tile, tile.getNearby(tile.getDump()));
			tile.setDump((byte)Mathf.mod(tile.getDump() + 1, 4));
		}
	}
	
	public void tryMoveLiquid(Tile tile, Tile next){
		LiquidPowerEntity entity = tile.entity();
		
		Liquid liquid = entity.liquid;
		
		if(next != null && next.block() instanceof LiquidAcceptor && entity.liquidAmount > 0.01f){
			LiquidAcceptor other = (LiquidAcceptor)next.block();
			
			float flow = Math.min(other.getLiquidCapacity(next) - other.getLiquid(next) - 0.001f, 
					Math.min(entity.liquidAmount/flowfactor * Timers.delta(), entity.liquidAmount));
			
			if(flow <= 0f || entity.liquidAmount < flow) return;
			
			if(other.acceptLiquid(next, tile, liquid, flow)){
				other.handleLiquid(next, tile, liquid, flow);
				entity.liquidAmount -= flow;
			}
		}
	}
	
	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidPowerEntity entity = tile.entity();
		
		return entity.liquidAmount + amount < liquidCapacity && (entity.liquid == liquid || entity.liquidAmount <= 0.01f);
	}
	
	@Override
	public void handleLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidPowerEntity entity = tile.entity();
		entity.liquid = liquid;
		entity.liquidAmount += amount;
	}
	
	@Override
	public float getLiquid(Tile tile){
		LiquidPowerEntity entity = tile.entity();
		return entity.liquidAmount;
	}

	@Override
	public float getLiquidCapacity(Tile tile){
		return liquidCapacity;
	}

	//TODO voltage requirement so blocks need specific voltage
	@Override
	public float addPower(Tile tile, float amount){
		if(amount < voltage){
			return amount;
		}
		LiquidPowerEntity entity = tile.entity();

		float canAccept = Math.min(powerCapacity - entity.power, amount);

		entity.power += canAccept;

		return canAccept;
	}

	@Override
	public void setPower(Tile tile, float power){
		LiquidPowerEntity entity = tile.entity();
		entity.power = power;
	}

	@Override
	public boolean acceptsPower(Tile tile){
		LiquidPowerEntity entity = tile.entity();

		return entity.power + 0.001f <= powerCapacity;
	}

	@Override
	public TileEntity getEntity(){
		return new LiquidPowerEntity();
	}
	
	public static class LiquidPowerEntity extends TileEntity{
		public float power;
		public float time; //generator time. this is a bit of a hack
		public Liquid liquid;
		public float liquidAmount;
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeFloat(power);
			stream.writeByte(liquid == null ? -1 : liquid.id);
			stream.writeByte((byte)(liquidAmount));
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			byte id = stream.readByte();
			power = stream.readFloat();
			liquid = id == -1 ? null : Liquid.getByID(id);
			liquidAmount = stream.readByte();
		}
	}
}
