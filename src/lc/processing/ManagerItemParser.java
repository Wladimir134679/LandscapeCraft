package lc.processing;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lc.item.block.Block;
import lc.item.Item;

/**
 * @author Death
 */
public class ManagerItemParser implements JsonSerializer<ManagerItemAndBlock>, JsonDeserializer<ManagerItemAndBlock>{

    @Override
    public JsonElement serialize(ManagerItemAndBlock t, Type type, JsonSerializationContext jsc){
        JsonObject obj = new JsonObject();
        
        JsonArray arrItem = new JsonArray();
        for(Item it : t.item){
            arrItem.add(jsc.serialize(it));
        }
        
//        JsonArray arrBlock = new JsonArray();
//        for(Block it : t.block){
//            arrBlock.add(jsc.serialize(it));
//        }
        
        obj.add("items", arrItem);
//        obj.add("blocks", arrBlock);
        
        return obj;
    }

    @Override
    public ManagerItemAndBlock deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
        ManagerItemAndBlock man = new ManagerItemAndBlock();
        JsonObject jo = (JsonObject) je;
        
//        JsonArray arrBlock = jo.getAsJsonArray("blocks");
//        for(int i = 0; i < arrBlock.size(); i++){
//            Block block = jdc.deserialize(arrBlock.get(i), Block.class);
//            man.block.add(block);
//        }
        
        JsonArray arrItem = jo.getAsJsonArray("items");
        for(int i = 0; i < arrItem.size(); i++){
            Item item = jdc.deserialize(arrItem.get(i), Item.class);
            man.item.add(item);
        }
        return man;
    }
}
