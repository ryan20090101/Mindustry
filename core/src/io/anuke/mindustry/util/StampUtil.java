package io.anuke.mindustry.util;

import com.badlogic.gdx.files.FileHandle;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Placement;
import io.anuke.mindustry.world.Tile;

import java.io.*;
import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static io.anuke.mindustry.Vars.stampDirectory;
import static io.anuke.mindustry.Vars.world;

public class StampUtil {
    /*
     * Basic format:
     * first two shorts are size
     * every block will have the size of four shorts
     * first is Short ID
     * second is Byte rotation saved as Short for ease of use
     * third is dump
     * fourth is extra
     */

    /**
     * Stamp Object contains block data, size and id
     */
    public static class Stamp {

        public String name;
        public int x, y;
        public BlockData[][] data;

        Stamp() {
        }
    }

    /**
     * BlockData object for use in Stamp
     */
    public static class BlockData {

        public int id;
        public byte rotation;
        public short dump;
        public short extra;

        BlockData() {
        }
    }

    /**
     * Write Stamp object to a file
     */
    public static void writeStampFile(String filename, Stamp stamp) throws IOException {
        //Write data to ByteBuffer
        ByteBuffer stampBuffer = ByteBuffer.allocate(4 + (stamp.x * stamp.y) * 2 * 4);
        stampBuffer.putShort((short) stamp.x);
        stampBuffer.putShort((short) stamp.y);
        for (int x = 0; x < stamp.x; x++) {
            for (int y = 0; y < stamp.y; y++) {
            	BlockData data = stamp.data[x][y];
                stampBuffer.putShort((short) data.id);
                stampBuffer.putShort(data.rotation);
                stampBuffer.putShort((short) data.dump);
                stampBuffer.putShort((short) data.extra);
            }
        }
        stampBuffer.flip();
        File stampFile = stampDirectory.child(filename + ".minstamp").file();
        stampFile.createNewFile();
        FileOutputStream stampOutput = new FileOutputStream(stampFile);
        WritableByteChannel channel = Channels.newChannel(stampOutput);
        channel.write(stampBuffer);
        stampOutput.close();
    }

    /**
     *
     * @param filename the filename of stamp file in Vars.stampDiretory
     * @return returns Stamp
     * @throws IOException
     */
    public static Stamp readStampFile(String filename) throws IOException {
        return readStampFile(stampDirectory.child(filename + ".minstamp"));
    }

    /**
     * Read Stamp object from a file
     */
    public static Stamp readStampFile(FileHandle file) throws IOException {
        if (!file.exists()) return null;
        ByteBuffer stampBuffer = ByteBuffer.wrap(Files.readAllBytes(Paths.get(file.path())));
        Stamp stamp = new Stamp();
        int sx = stampBuffer.getShort();
        int sy = stampBuffer.getShort();
        stamp.data = new BlockData[sx+1][sy+1];
        for (int x = 0; x < sx; x++) {
            for (int y = 0; y < sy; y++) {
                BlockData data = new BlockData();
                data.id = stampBuffer.getShort();
                data.rotation = (byte) stampBuffer.getShort();
                data.dump = stampBuffer.getShort();
                data.extra = stampBuffer.getShort();
                stamp.data[x][y] = data;
            }
        }
        return stamp;
    }

    /**
     * Create Stamp object from tiles in World
     */
    public static Stamp createStamp(int wx, int wy, int sx, int sy) {

        //if size is negative do this
        if (sx < 0 || sy < 0) {
            int tmpSX, tmpSY;
            sx = wx + sx;
            sy = wy + sy;
            if (sx < wx) {
                tmpSX = wx;
                wx = sx;
                sx = tmpSX;
            }
            if (sy < wy) {
                tmpSY = wy;
                wy = sy;
                sy = tmpSY;
            }
            sy = sy - wy;
            sx = sx - wx;
        }

        Stamp stamp = new Stamp();
        stamp.x = sx;
        stamp.y = sy;
        stamp.data = new BlockData[sx + 1][sy + 1];
        for (int x = 0; x < sx; x++) {
            for (int y = 0; y < sy; y++) {
                Tile tile = world.tile(wx + x, wy + y);
                BlockData data = new BlockData();
                data.id = tile.block().id;
                data.rotation = tile.getRotation();
                data.dump = tile.getDump();
                data.extra = tile.getExtra();
                stamp.data[x][y] = data;
            }
        }
        stamp.name = Integer.toString(new Random().nextInt());
        return stamp;
    }

    /**
     * Load Stamp object to tiles in World
     */
    public static void loadStamp(int wx, int wy, Stamp stamp) {
        for (int x = 0; x < stamp.x; x++) {
            for (int y = 0; y < stamp.y; y++) {
                Block block = Block.getByID(stamp.data[x][y].id);
                if(!Placement.validPlace(wx+x,wy+y,block)) continue;
                Placement.placeBlock(wx+x,wy+y, block, stamp.data[x][y].rotation,true,false);
            }
        }
    }

    /**
     * Get stamps from folder
     * @param folder folder to get stamps from
     * @return Stamps array
     * @throws IOException
     */
    public static Stamp[] loadStampsFromFolder(FileHandle folder) throws IOException {
        Stamp[] stamps = new Stamp[folder.list().length];
        int i=0;
        for(FileHandle file : folder.list()){
            stamps[i] = readStampFile(file);
            stamps[i].name = file.nameWithoutExtension();
        }
        return stamps;
    }
}
