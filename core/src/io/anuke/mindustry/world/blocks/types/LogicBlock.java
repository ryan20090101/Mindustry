package io.anuke.mindustry.world.blocks.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.Blocks;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.graphics.Lines;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static io.anuke.mindustry.Vars.world;

public class LogicBlock extends Block implements LogicAcceptor{

    public int maxIO = 10;

    public LogicBlock(String name) {
        super(name);
        expanded = true;
        update = true;
        layer2 = Layer.laser;
    }

    @Override
    public boolean canLogicOutput(Tile tile) {
        return true;
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
    public void onBreak(Tile tile) {
        System.out.println("test");
        int pos;
        Iterator<Integer> it = tile.<LogicEntity>entity().inputBlocks.iterator();
        while(it.hasNext()) {
            pos = it.next();
            Tile logicTile = world.tile(pos % world.width(), pos / world.width());
            if (logicTile.block()!=Blocks.air) {
                logicTile.<LogicEntity>entity().outputBlocks.removeValue(tile.packedPosition(),false);
            }
        }

        it = tile.<LogicEntity>entity().outputBlocks.iterator();
        while(it.hasNext()) {
            pos = it.next();
            Tile logicTile = world.tile(pos % world.width(), pos / world.width());
            if (logicTile.block()!=Blocks.air) {
                logicTile.<LogicEntity>entity().inputBlocks.removeValue(tile.packedPosition(),false);
            }
        }
    }

    @Override
    public void updateOutputLogic(Tile tile) {
        LogicEntity ent = tile.entity();

        int pos;
        Iterator<Integer> it = ent.outputBlocks.iterator();
        while(it.hasNext()) {
            pos = it.next();
            Tile logicTile = world.tile(pos % world.width(), pos / world.width());
            if (logicTile.block()!=Blocks.air) {
                LogicAcceptor blck = ((LogicAcceptor) logicTile.block());
                blck.setLogic(logicTile, tile, ent.outputActive);
                logicTile.<LogicEntity>entity().inputBlocks.add(tile.packedPosition());
            }
            else
                ent.outputBlocks.removeValue(pos,false);
        }
    }
	@Override
	public void update(Tile tile){}
    @Override
    public boolean logicLink(Tile tile, Tile source) {
        LogicEntity ent = tile.entity();

        if (ent.connectedBlocks < maxIO) {
            if(ent.outputBlocks.contains(source.packedPosition(),true))
                return false;
            ent.outputBlocks.add(source.packedPosition());
            ((LogicAcceptor) source.block()).onLogicLink(source);
            source.<LogicEntity>entity().inputBlocks.add(tile.packedPosition());
            ent.connectedBlocks++;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean isLayer2(Tile tile) {
        return true; //check if viewing logic links
    }

    @Override
    public void drawLayer2(Tile tile) {
        LogicEntity ent = tile.entity();
        if (ent==null)
            return;
        Draw.color(Color.GREEN);
        Draw.alpha(0.3f);
        Lines.stroke(4f);
        Iterator<Integer> it = ent.outputBlocks.iterator();
        int pos;
        while(it.hasNext()) {
            pos = it.next();
            Lines.line(tile.x*8, tile.y*8, pos % world.width()*8, pos / world.width()*8);
        }
        Draw.reset();
        super.drawLayer2(tile);
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
        public Array<Integer> inputBlocks = new Array<>();

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
