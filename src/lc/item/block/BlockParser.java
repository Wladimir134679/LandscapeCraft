package lc.item.block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Death
 */
public class BlockParser implements JsonSerializer<Block>, JsonDeserializer<Block>{

    @Override
    public JsonElement serialize(Block t, Type type, JsonSerializationContext jsc){
        JsonObject jo = new JsonObject();
        String ext = null;
        if(t instanceof BlockChest)
            ext = "CHEST";
        if(t instanceof BlockPlantGrass)
            ext = "PLANT_GRASS";
        if(t instanceof BlockCraftingTable)
            ext = "CRAFTING_TABLE";
        if(t instanceof BlockFoliage)
            ext = "FOLIAGE";
        if(t instanceof BlockFurnace)
            ext = "FURNACE";
        if(t instanceof BlockSeedling)
            ext = "SEEDLING";
        if(t instanceof BlockGrass)
            ext = "GRASS";
        
//        System.out.println("lc.item.block.BlockParser.serialize(): " + ext);
        if(ext != null)
            jo.addProperty("extends", "#" + ext);
        t.serialize(jo, jsc);
        return jo;
    }

    @Override
    public Block deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
        JsonObject jo = (JsonObject) je;
        Block bl = null;
        if(jo.get("extends") == null)
            bl = new Block();
        else{
            String ext = jo.get("extends").getAsString();
            if(ext.charAt(0) == '#'){
                String nameClassBlock = ext.substring(1);
                switch(nameClassBlock){
                    case "CHEST": bl = new BlockChest(); break;
                    case "CRAFTING_TABLE": bl = new BlockCraftingTable(); break;
                    case "FOLIAGE": bl = new BlockFoliage(); break;
                    case "FURNACE": bl = new BlockFurnace(); break;
                    case "SEEDLING": bl = new BlockSeedling(); break;
                    case "GRASS": bl = new BlockGrass(); break;
                    case "PLANT_GRASS": bl = new BlockPlantGrass(); break;
                }
            }else{
                try{
                    bl = (Block) Class.forName(ext).newInstance();
                }
                catch(ClassNotFoundException | InstantiationException | IllegalAccessException  ex){
                    Logger.getLogger(BlockParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(bl == null) return null;
        bl.deserialize(jo, jdc);
        return bl;
    }

}
