package io.anuke.mindustry.world.blocks.types.logic;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LogicAcceptor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static io.anuke.mindustry.Vars.world;

public class LogicBlock extends Block implements LogicAcceptor{

    public int maxIO = 10;

    public LogicBlock(String name) {
        super(name);
    }

    @Override
    public boolean canLogicLink(Tile tile) {
        return (tile.block() instanceof LogicAcceptor);
    }

    @Override
    public void onLogicLink(Tile tile) {

    }

    @Override
    public boolean setLogic(Tile tile, Tile source, Boolean logicState) {
        LogicEntity ent = tile.entity();
        ent.selfActive = logicState;
        return true;
    }

    @Override
    public void updateOutputLogic(Tile tile) {
        LogicEntity ent = tile.entity();

        int pos;
        Iterator<Integer> it = ent.outputBlocks.iterator();
        while(it.hasNext()) {
            pos = it.next();
            Tile logicTile = world.tile(pos % world.width(), pos / world.width());
            LogicAcceptor blck =((LogicAcceptor) tile.block());
            blck.setLogic(logicTile,tile,ent.outputActive);
        }
    }

    @Override
    public boolean logicLink(Tile tile, Tile source) {
        LogicEntity ent = tile.entity();

        if (ent.connectedBlocks < maxIO) {
            if(ent.outputBlocks.contains(source.packedPosition(),true))
                return false;
            ent.outputBlocks.add(source.packedPosition());
            ((LogicAcceptor) source.block()).onLogicLink(source);
            ent.connectedBlocks++;
            return true;
        }
        else
            return false;
    }

    @Override
    public TileEntity getEntity(){
        return new LogicEntity();
    }

    public static class LogicEntity extends TileEntity{

        public boolean selfActive;
        public boolean outputActive;
        public int connectedBlocks;
        public Array<Integer> outputBlocks = new Array<>();

        @Override
        public void write(DataOutputStream stream) throws IOException {
            stream.writeByte(selfActive ? 1 : 0);
        }

        @Override
        public void read(DataInputStream stream) throws IOException{
            selfActive = stream.readByte() == 1;
        }
    }
}
