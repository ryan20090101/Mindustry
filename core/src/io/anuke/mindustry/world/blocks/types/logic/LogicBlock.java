package io.anuke.mindustry.world.blocks.types.logic;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class LogicBlock extends Block{

    public LogicBlock(String name) {
        super(name);
    }

    public void logicChange(Tile source, Tile tile) {}

    public void setLogic(Tile source, Tile tile) {
        if (tile.block()instanceof LogicBlock)
            ((LogicBlock)tile.block()).logicChange(source,tile);
    }

    @Override
    public TileEntity getEntity(){
        return new LogicEntity();
    }

    public static class LogicEntity extends TileEntity{

        public boolean active;
        public int[] inputBlocks;
        public int[] outputBlocks;

        @Override
        public void write(DataOutputStream stream) throws IOException {
            stream.writeByte(active ? 1 : 0);
            stream.writeByte(inputBlocks.length);
            for(int i = 0; i < inputBlocks.length; i ++){
                stream.writeInt(inputBlocks[i]);
            }
            stream.writeByte(outputBlocks.length);
            for(int i = 0; i < outputBlocks.length; i ++){
                stream.writeInt(outputBlocks[i]);
            }
        }

        @Override
        public void read(DataInputStream stream) throws IOException{
            active = stream.readByte() == 1;
            byte length = stream.readByte();
            for(int i = 0; i < length; i ++){
                inputBlocks[i] = stream.readInt();
            }
            length = stream.readByte();
            for(int i = 0; i < length; i ++){
                outputBlocks[i] = stream.readInt();
            }
        }
    }
}
