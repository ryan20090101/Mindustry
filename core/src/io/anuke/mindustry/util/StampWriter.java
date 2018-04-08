package io.anuke.mindustry.util;

import java.nio.*;

import static io.anuke.mindustry.Vars.world;

public class StampWriter {
    /**Basic format:
     * first two shorts are size
     * every block will have the size of four shorts
     * first is ID
     * second is rotation
     * third is reserved for future use
     * fourth is reserved for future use
     * */

    /**Filename is self explanatory, WX and WY are world co-ordinates, SX and SY are stamp size, dimension is the world dimension*/
    public static void writeStamp(String filename, int wx, int wy, int sx, int sy, int dimension) {
        ByteBuffer stamp = ByteBuffer.allocate(4+(sx*sy)*4);
        stamp.putShort((short) sx);
        stamp.putShort((short) sy);
        for(int x=0;x<sx;x++){
            for(int y=0;y<sy;y++){
                stamp.putShort((short) world[dimension].tile(wx+x,wy+y).block().id);
                stamp.putShort((short) world[dimension].tile(wx+x,wy+y).getRotation());
                stamp.putShort((short) 0);
                stamp.putShort((short) 0);
            }
        }
        //Code for writing to filename
    }
}