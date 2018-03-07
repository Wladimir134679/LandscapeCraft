package lc.item;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import lc.item.block.Block;
import lc.item.block.BlockProperty;
import lc.item.block.MaterialBlock;
import lc.item.craft.CraftManager;
import lc.item.craft.Recipe;
import lc.utils.Textures;

/**
 * @author Death
 */
public class DataAll{

    public static DataAll DATA_ALL = null;
    
    public static DataAll get(){
        if(DATA_ALL == null)
            DATA_ALL = new DataAll();
        return DATA_ALL;
    }
    
    public static DataAll load(FileHandle dir){
        GsonBuilder gb = get().init();
        FileHandle fileMain = dir.child("DataAll.json");
        Gson gson = gb.create();
        String json = fileMain.readString();
        DATA_ALL = gson.fromJson(json, DataAll.class);
        DATA_ALL.textures.load(dir);
        return get();
    }
    
    public static void save(FileHandle dir){
        FileHandle fileMain = dir.child("DataAll.json");
        GsonBuilder gb = get().init();
        gb.setPrettyPrinting();
        Gson gson = gb.create();
        
        String json = gson.toJson(get());
        fileMain.writeString(json, false);
    }
    
    public HashMap<Integer, Item> items;
    public HashMap<String, Integer> idItems;
    public HashMap<String, MaterialItem> materialsItem;
    
    public HashMap<Integer, Block> blocks;
    public HashMap<String, Integer> idBlocks;
    public HashMap<String, MaterialBlock> materialsBlock;
    
    public ArrayList<MaterialBlock> listPlayerCrashMaterialBlock;
    
    public Textures textures;
    
    public GsonBuilder gson;
    
    public DataAll(){
        items = new HashMap<>();
        idItems = new HashMap<>();
        materialsItem = new HashMap<>();
        
        blocks = new HashMap<>();
        idBlocks = new HashMap<>();
        materialsBlock = new HashMap<>();
        
        listPlayerCrashMaterialBlock = new ArrayList<>();
        
        textures = null;
    }
    
    public GsonBuilder init(){
        gson = new GsonBuilder();
        Item.init(gson);
        Block.init(gson);
        MaterialItem.init(gson);
        MaterialBlock.init(gson);
        CraftManager.init();
        Recipe.init(gson);
        Textures.init(gson);
        
        gson.registerTypeAdapter(DataAll.class, new Parser());
        return gson;
    }

    public Item getItem(String name){
        return items.get(idItems.get(name));
    }

    public Block getBlock(String name){
        return blocks.get(idBlocks.get(name));
    }
    
    public MaterialItem getMaterialItem(String name){
        return materialsItem.get(name);
    }

    public MaterialBlock getMaterialBlock(String name){
        return materialsBlock.get(name);
    }

    public void parseJsonPropertBlocks(byte[] data){
        BlockProperty.setAllDataToString(data);
    }

    public byte[] getJsonPropertyBlocks(){
        return BlockProperty.getAllDataToString();
    }
    
    public ArrayList<Item> getListItem(){
        return new ArrayList<Item>(items.values());
    }
    
    public ArrayList<MaterialBlock> getListMaterialBlock(){
        return new ArrayList<MaterialBlock>(materialsBlock.values());
    }
    
    public ArrayList<MaterialItem> getListMaterialItem(){
        return new ArrayList<MaterialItem>(materialsItem.values());
    }
    
    public void addItem(Item it){
        idItems.put(it.nameKod, it.id);
        items.put(it.id, it);
    }
    
    public void addBlock(Block bl){
        idBlocks.put(bl.nameKod, bl.id);
        blocks.put(bl.id, bl);
    }
    
    public void addMaterialItem(MaterialItem mat){
        materialsItem.put(mat.name, mat);
    }
    
    public void addMaterialBlock(MaterialBlock mat){
        materialsBlock.put(mat.name, mat);
    }

    public Sprite getSprite(Item item){
        if(item == null) return null;
        return textures.get(item.nameTexture);
    }

    
    public void clear(){
        materialsBlock.clear();
        materialsItem.clear();
        listPlayerCrashMaterialBlock.clear();
        
        items.clear();
        idItems.clear();
        blocks.clear();
        idBlocks.clear();
    }
    
    public static class Parser implements JsonSerializer<DataAll>, JsonDeserializer<DataAll>{

        @Override
        public JsonElement serialize(DataAll t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            
            JsonArray arrMatItem = new JsonArray();
            ArrayList<MaterialItem> matersItem = t.getListMaterialItem();
            for(MaterialItem item : matersItem){
                arrMatItem.add(jsc.serialize(item));
            }
            jo.add("materialItem", arrMatItem);
            
            JsonArray arrMatBlock = new JsonArray();
            ArrayList<MaterialBlock> matersBlock = t.getListMaterialBlock();
            for(MaterialBlock block : matersBlock){
                arrMatBlock.add(jsc.serialize(block));
            }
            jo.add("materialBlock", arrMatBlock);
            
            jo.add("textures", jsc.serialize(t.textures));
            
            JsonArray arrItem = new JsonArray();
            ArrayList<Item> items = t.getListItem();
            for(Item item : items){
                arrItem.add(jsc.serialize(item));
            }
            jo.add("items", arrItem);
            return jo;
        }

        @Override
        public DataAll deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            JsonObject jo = (JsonObject) je;
            DataAll t = DataAll.get();
            t.clear();
            
            JsonArray arrMatItem = jo.getAsJsonArray("materialItem");
            for(int i = 0; i < arrMatItem.size(); i++){
                MaterialItem mat = jdc.deserialize(arrMatItem.get(i), MaterialItem.class);
                t.addMaterialItem(mat);
            }
            JsonArray arrMatBlock = jo.getAsJsonArray("materialBlock");
            for(int i = 0; i < arrMatBlock.size(); i++){
                MaterialBlock mat = jdc.deserialize(arrMatBlock.get(i), MaterialBlock.class);
                if(mat.isPlayerCrach){
                    t.listPlayerCrashMaterialBlock.add(mat);
                }
                t.addMaterialBlock(mat);
            }
            t.textures = jdc.deserialize(jo.get("textures"), Textures.class);
            
            JsonArray arrItem = jo.getAsJsonArray("items");
            for(int i = 0; i < arrItem.size(); i++){
                Item it = jdc.deserialize(arrItem.get(i), Item.class);
                t.addItem(it);
            }
            return t;
        }
        
    }
}
