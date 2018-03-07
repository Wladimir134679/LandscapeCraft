package lc.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import lc.item.block.Block;

/**
 * @author Death
 */
public class ItemTool extends Item{

    public float force;
    public int strengthMax;
    public ArrayList<String> crashType;

    public ItemTool(){
        type = TypeItem.TOOL;
        crashType = new ArrayList<>();
    }
    
    public boolean isCrashBlock(Block block){
        if(crashType.get(0).equals("all")) return true;
        for(String crashType1 : crashType){
            if(block.isMaterial(crashType1))
                return true;
        }
        return false;
    }
    
    @Override
    public void deserialize(JsonObject jo, JsonDeserializationContext jsc){
        super.deserialize(jo, jsc);
        force = jo.get("force").getAsFloat();
        strengthMax = jo.get("strengthMax").getAsInt();
        crashType.clear();
        crashType.addAll(Arrays.asList(jo.get("crashType").getAsString().split(":")));
    }

    @Override
    public void serialize(JsonObject jo, JsonSerializationContext jsc){
        super.serialize(jo, jsc);
        jo.addProperty("force", force);
        jo.addProperty("strengthMax", strengthMax);
        jo.remove("block");
        StringJoiner sj = new StringJoiner(":");
        for(String crT : crashType)
            sj.add(crT);
        jo.addProperty("crashType", sj.toString());
    }
}
