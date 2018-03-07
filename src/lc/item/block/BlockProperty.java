package lc.item.block;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import lc.utils.LCUtils;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class BlockProperty {

    public static HashMap<String, PropertyUtil> propertys;
    
    public static void init(){
        propertys = new HashMap<>();
    }

    public static void remove(int x, int y){
        String point = String.valueOf(x + ":" + y);
        propertys.remove(point);
    }
    
    public static PropertyUtil getData(int x, int y){
        String point = String.valueOf(x + ":" + y);
        PropertyUtil data = propertys.get(point);
        if(data == null){
            data = new PropertyUtil();
            data.set("x", x);
            data.set("y", y);
            propertys.put(point, data);
        }
        return data;
    }
    
    public static void setAllDataToString(byte[] data){
        propertys.clear();
        ByteBuffer buf = ByteBuffer.wrap(data);
        int size = buf.getInt();
        for(int i = 0; i < size; i++){
            String name = LCUtils.readString(buf);
            int lenght = buf.getInt();
            byte[] dataProp = new byte[lenght];
            buf.get(dataProp);
            
            PropertyUtil prop = new PropertyUtil();
            prop.setData(dataProp);
            propertys.put(name, prop);
        }
    }
    
    public static byte[] getAllDataToString(){
        ByteBuffer buf = ByteBuffer.allocate(1024 * 5);
        
        Iterator<String> iter = propertys.keySet().iterator();
        buf.putInt(propertys.size());
        while(iter.hasNext()){
            String namePropr = iter.next();
            PropertyUtil prop = propertys.get(namePropr);
            byte[] data = prop.getData();
            LCUtils.write(namePropr, buf);
            buf.putInt(data.length);
            buf.put(data);
        }
        return buf.array();
//        StringBuilder bl = new StringBuilder();
//        Iterator<PropertyUtil> iter = propertys.values().iterator();
//        while(iter.hasNext()){
//            PropertyUtil prop = iter.next();
//            
//            bl.append(prop.getData());
//        }
//        return bl.toString();
    }
}
