package lc.item.craft;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import lc.item.Item;

/**
 * @author Death
 */
public class Recipe {
    

    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(Recipe.class, new Recipe.Parser());
    }
    
    public String[] component;
    public int[] numCom;
    
    public String tool = "this";
    
    public Item itemRezult;
    public int numResult;

    public Recipe(){
        this(null);
    }
    
    public Recipe(Item itemRezult){
        this.itemRezult = itemRezult;
        this.numResult = 1;
    }

    public boolean equals(Item obj){
        return obj.id == itemRezult.id;
    }
    
    public void load(String com, String num){
        component = com.split(":");
        String[] nums = num.split(":");
        numCom = new int[nums.length];
        for(int i = 0; i < nums.length; i++)
            numCom[i] = Integer.parseInt(nums[i]);
    }
    
    public String getComponents(){
        StringBuilder sb = new StringBuilder();
        for(String str : component){
            sb.append(str).append(':');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    public String getResults(){
        StringBuilder sb = new StringBuilder();
        for(int i : numCom){
            sb.append(i).append(':');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    
    public static class Parser implements JsonSerializer<Recipe>, JsonDeserializer<Recipe>{

        @Override
        public JsonElement serialize(Recipe t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            jo.addProperty("tool", t.tool);
            jo.addProperty("numResult", t.numResult);
            
            JsonObject arrComs = new JsonObject();
            for(int i = 0; i < t.numCom.length; i++){
                arrComs.addProperty(t.component[i], t.numCom[i]);
            }
            jo.add("components", arrComs);
            
            return jo;
        }

        @Override
        public Recipe deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            JsonObject jo = (JsonObject) je;
            Recipe re = new Recipe();
            re.tool = jo.get("tool").getAsString();
            re.numResult = jo.get("numResult").getAsInt();
            JsonObject arrComs = jo.getAsJsonObject("components");
            Iterator<Map.Entry<String, JsonElement>> ite = arrComs.entrySet().iterator();
            re.component = new String[arrComs.size()];
            re.numCom = new int[arrComs.size()];
            for(int i = 0; ite.hasNext(); i++){
                Map.Entry<String, JsonElement> com = ite.next();
                re.component[i] = com.getKey();
                re.numCom[i] = com.getValue().getAsInt();
            }
            CraftManager.addRecipe(re);
            return re;
        }
    }
}
