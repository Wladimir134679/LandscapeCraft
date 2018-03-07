package lc.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lc.item.Item;
import lc.item.ItemTool;
import lc.item.TypeItem;

/**
 * @author Death
 */
public class ItemParser implements JsonDeserializer<Item>,JsonSerializer<Item>{

    @Override
    public JsonElement serialize(Item t, Type type, JsonSerializationContext jsc){
        JsonObject jo = new JsonObject();
        t.serialize(jo, jsc);
        return jo;
    }

    @Override
    public Item deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
        JsonObject jo = (JsonObject) je;
        Item it = null;
        switch(TypeItem.valueOf(jo.get("type").getAsString())){
            case ITEM_DEFAULT: it = new Item(); break;
            case TOOL: it = new ItemTool(); break;
        }
        if(it != null)
            it.deserialize(jo, jdc);
        return it;
    }

}
