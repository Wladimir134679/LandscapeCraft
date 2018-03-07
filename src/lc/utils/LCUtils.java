package lc.utils;

import java.nio.ByteBuffer;

/**
 * @author Death
 */
public class LCUtils {

    
    public final static void write(String str, ByteBuffer buf){
        buf.putInt(str.length());
        buf.put(str.getBytes());
    }
    
    public final static String readString(ByteBuffer buf){
        byte[] b = new byte[buf.getInt()];
        buf.get(b);
        return new String(b);
    }
}
