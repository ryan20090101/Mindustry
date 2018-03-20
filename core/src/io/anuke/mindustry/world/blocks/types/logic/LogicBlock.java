package io.anuke.mindustry.world.blocks.types.logic;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicAcceptor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LogicBlock extends Block implements LogicAcceptor{

    public int maxIO;

    public LogicBlock(String name) {
        super(name);
    }

    @Override
    public void onLogicChange(Tile source, Tile tile) {

    }

    @Override
    public boolean canLogicLink(Tile tile) {
        return (tile.block() instanceof LogicAcceptor);
    }

    @Override
    public void onLogicLink(Tile tile) {

    }

    @Override
    public void setLogic(Tile tile, Tile source, Boolean logicState) {
        LogicEntity ent = tile.entity();
        ent.active = logicState;
    }

    @Override
    public boolean logicLink(Tile tile, Tile source) {
        LogicEntity ent = tile.entity();

        if (ent.connectedBlocks < maxIO) {
            ent.connectedBlocks++;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public TileEntity getEntity(){
        return new LogicEntity();
    }

    public static class LogicEntity extends TileEntity{

        public boolean active;
        public int connectedBlocks;
        public int[] outputBlocks;

        @Override
        public void write(DataOutputStream stream) throws IOException {
            stream.writeByte(active ? 1 : 0);
        }

        @Override
        public void read(DataInputStream stream) throws IOException{
            active = stream.readByte() == 1;
        }
    }
}
