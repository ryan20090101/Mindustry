package io.anuke.mindustry.util;

import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.Blocks;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import static io.anuke.mindustry.Vars.stampDirectory;
import static io.anuke.mindustry.Vars.world;

public class StampUtil {
    /**
     * Basic format:
     * first two shorts are size
     * every block will have the size of four shorts
     * first is Short ID
     * second is Byte rotation
     * third is Short reserved for future use
     * fourth is Short reserved for future use
     */

    public static class Stamp {

        public int x, y;
        public BlockData[][] data;

        public Stamp() {
        }
    }

    public static class BlockData {

        public int id;
        public byte rotation;

        public BlockData(){}
    }

    /**
     * Write Stamp object to a file
     */
    public static void writeStamp(String filename, Stamp stamp) throws IOException {
        //Write data to ByteBuffer
        ByteBuffer stampBuffer = ByteBuffer.allocate(4 + (stamp.x * stamp.y) * 4);
        stampBuffer.putShort((short) stamp.x);
        stampBuffer.flip();
        stampBuffer.putShort((short) stamp.y);
        for (int x = 0; x < stamp.x; x++) {
            for (int y = 0; y < stamp.y; y++) {
                stampBuffer.putShort((short) stamp.data[x][y].id);
                stampBuffer.put(stamp.data[x][y].rotation);
                stampBuffer.putShort((short) 0);
                stampBuffer.putShort((short) 0);
            }
        }
        stampBuffer.flip();
        File stampFile = stampDirectory.child(filename+".minstamp").file();
        stampFile.createNewFile();
        FileOutputStream stampOutput = new FileOutputStream(stampFile);
        WritableByteChannel channel = Channels.newChannel(stampOutput);
        channel.write(stampBuffer);
        stampOutput.close();
    }

    /**
     * Read Stamp object from a file
     */
    public static Stamp readStamp(String filename) throws IOException {
        File stampFile = stampDirectory.child(filename+".minstamp").file();
        if(!stampFile.exists()) return null;
        FileInputStream stampInput = new FileInputStream(stampFile);
        ByteBuffer stampBuffer = ByteBuffer.wrap(IOUtils.toByteArray(stampInput));
        Stamp stamp = new Stamp();
        int sx = stampBuffer.getShort();
        int sy = stampBuffer.getShort();
        for (int x = 0; x < sx; x++) {
            for (int y = 0; y < sy; y++) {
                stamp.data[x][y].id = stampBuffer.getShort();
                stamp.data[x][y].rotation = stampBuffer.get();
                stampBuffer.getShort(); //RESERVED
                stampBuffer.getShort(); //RESERVED
            }
        }
        return stamp;
    }

    /**
     * Create Stamp object from tiles in World[dimension]
     */
    public static Stamp createStamp(int wx, int wy, int sx, int sy, int dimension) throws IOException {
        Stamp stamp = new Stamp();
        stamp.x = sx;
        stamp.y = sy;
        for (int x = 0; x < sx; x++) {
            for (int y = 0; y < sy; y++) {
                Tile tile = world[dimension].tile(wx+x,wy+y);
                stamp.data[x][y].id = tile.block().id;
                stamp.data[x][y].rotation = tile.getRotation();
            }
        }
        return stamp;
    }
}