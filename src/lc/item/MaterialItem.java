package lc.item;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class MaterialItem {

    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(MaterialItem.class, new MaterialItemParser());
    }
    
    public String name;
    public PropertyUtil property;

    public MaterialItem(){
        name = "";
        property = new PropertyUtil();
    }
    
    
    public static class MaterialItemParser implements JsonSerializer<MaterialItem>, JsonDeserializer<MaterialItem>{

        @Override
        public JsonElement serialize(MaterialItem t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            jo.addProperty("name", t.name);
            jo.add("property", jsc.serialize(t.property));
            return jo;
        }

        @Override
        public MaterialItem deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            JsonObject jo = (JsonObject) je;
            MaterialItem mat = new MaterialItem();
            mat.name = jo.get("name").getAsString();
            mat.property = jdc.deserialize(jo.get("property"), PropertyUtil.class);
            DataAll.get().addMaterialItem(mat);
            return mat;
        }
    }
}
