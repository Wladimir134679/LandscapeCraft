package lc.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lc.item.DataAll;
import lc.item.StackItems;

/**
 * @author Death
 */
public class PropertyUtil{

    public static final PropertyUtil temp = new PropertyUtil();
    
    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(PropertyUtil.class, new PropertyParser());
    }
    
    public HashMap<String, String> map;

    public PropertyUtil(){
        map = new HashMap<>();
    }
    
    public void set(String key, String val){
        map.put(key, val);
    }
    
    public void set(String key, int i){
        this.set(key, String.valueOf(i));
    }
    
    public void set(String key, boolean i){
        this.set(key, String.valueOf(i));
    }
    
    public String get(String key){
        return map.get(key);
    }
    
    public int getInt(String key){
        return Integer.parseInt(get(key));
    }
    
    public boolean getBool(String key){
        return Boolean.parseBoolean(get(key));
    }
    
    public void clear(){
        map.clear();
    }
    
    public void setData(PropertyUtil util){
        this.map = util.map;
    }

    public void append(PropertyUtil property){
        this.map.putAll(property.map);
    }
    
    public String[] getKeys(){
        Iterator<String> ite = map.keySet().iterator();
        String[] keys = new String[map.keySet().size()];
        for(int i = 0; ite.hasNext(); i++){
            keys[i] = ite.next();
        }
        return keys;
    }
    
    public String[] getValues(){
        Iterator<String> ite = map.values().iterator();
        String[] values = new String[map.values().size()];
        for(int i = 0; ite.hasNext(); i++){
            values[i] = ite.next();
        }
        return values;
    }
    
    public ArrayList<Map.Entry<String, String>> getEntryArray(){
        ArrayList<Map.Entry<String, String>> arr = new ArrayList<>();
        Iterator<Map.Entry<String, String>> ite = map.entrySet().iterator();
        while(ite.hasNext()){
            arr.add(ite.next());
        }
        return arr;
    }
    
    
//    public void load(DataInputStream inChest) throws IOException{
//        int size = inChest.readInt();
//        for(int i = 0; i < size; i++){
//            String key = inChest.readUTF();
//            String value = inChest.readUTF();
//            
//            this.set(key, value);
//        }
//    }
    
    public void setData(byte[] data){
        ByteBuffer buf = ByteBuffer.wrap(data);
        int size = buf.getInt();
        for(int i = 0; i < size; i++){
            String key = LCUtils.readString(buf);
            String value = LCUtils.readString(buf);
            
            this.set(key, value);
        }
    }
    
    
    public byte[] getData(){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.putInt(map.size());
        Iterator<String> keys = map.keySet().iterator();
        if(keys.hasNext()){
            String key = keys.next();
            String value = map.get(key);
            
            LCUtils.write(key, buf);
            LCUtils.write(value, buf);
        }
        return buf.array();
    }
//    public void save(DataOutputStream outChest) throws IOException{
//        Iterator<String> keys = map.keySet().iterator();
//        outChest.writeInt(map.size());
//        if(keys.hasNext()){
//            String key = keys.next();
//            String value = map.get(key);
//            
//            outChest.writeUTF(key);
//            outChest.writeUTF(value);
//        }
//    }


    public static class PropertyParser implements JsonSerializer<PropertyUtil>, JsonDeserializer<PropertyUtil>{

        @Override
        public JsonElement serialize(PropertyUtil t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            ArrayList<Map.Entry<String, String>> entry = t.getEntryArray();
            for(Map.Entry<String, String> field : entry){
                jo.addProperty(field.getKey(), field.getValue());
            }
            return jo;
        }

        @Override
        public PropertyUtil deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            PropertyUtil pro = new PropertyUtil();
            JsonObject jo = (JsonObject) je;
            Iterator<Map.Entry<String, JsonElement>> ite = jo.entrySet().iterator();
            while(ite.hasNext()){
                Map.Entry<String, JsonElement> entry = ite.next();
                pro.set(entry.getKey(), entry.getValue().getAsString());
            }
            return pro;
        }
        
    }
}
