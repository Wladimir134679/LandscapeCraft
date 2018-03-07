package lc.item.block;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import lc.item.DataAll;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class MaterialBlock {
    
    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(MaterialBlock.class, new MaterialBlockParser());
    }
    
    public String name;
    public boolean isPlayerCrach;
    public PropertyUtil property;

    public MaterialBlock(){
        name = "";
        isPlayerCrach = true;
        property = new PropertyUtil();
    }
    
    
    public static class MaterialBlockParser implements JsonSerializer<MaterialBlock>, JsonDeserializer<MaterialBlock>{

        @Override
        public JsonElement serialize(MaterialBlock t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            jo.addProperty("name", t.name);
            jo.addProperty("isPlayerCrach", t.isPlayerCrach);
            jo.add("property", jsc.serialize(t.property));
            return jo;
        }

        @Override
        public MaterialBlock deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            JsonObject jo = (JsonObject) je;
            MaterialBlock mat = new MaterialBlock();
            mat.name = jo.get("name").getAsString();
            mat.isPlayerCrach = jo.get("isPlayerCrach").getAsBoolean();
            mat.property = jdc.deserialize(jo.get("property"), PropertyUtil.class);
//            DataAll.get().addMaterialBlock(mat);
            return mat;
        }
    }
}
