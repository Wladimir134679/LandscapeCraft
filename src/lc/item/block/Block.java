package lc.item.block;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import lc.inventory.invBlocks.InventoryBlock;
import lc.item.DataAll;
import lc.item.Item;
import lc.item.StackItems;
import lc.utils.PropertyUtil;
import lc.world.LayerBlock;
import lc.world.World;
import lc.world.WorldStackItems;

/**
 * @author Death
 */
public class Block {
    
    public static int SIZE = 32;
    
    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(Block.class, new BlockParser());
        gson.registerTypeAdapter(BlockChest.class, new BlockParser());
        gson.registerTypeAdapter(BlockCraftingTable.class, new BlockParser());
        gson.registerTypeAdapter(BlockFoliage.class, new BlockParser());
        gson.registerTypeAdapter(BlockFurnace.class, new BlockParser());
        gson.registerTypeAdapter(BlockGrass.class, new BlockParser());
        gson.registerTypeAdapter(BlockPlantGrass.class, new BlockParser());
        gson.registerTypeAdapter(BlockSeedling.class, new BlockParser());
    }
    
    public static Block get(String name){
        return DataAll.get().getBlock(name);
    }
    
    public static Sprite getSprite(Block b){
        return DataAll.get().getSprite(b.itemParent);
    }
    
    public static void crashed(Block block, World world, int xId, int yId){
        int numIt = block.getNumItem();
        if(numIt > 0){
            StackItems si = new StackItems(block.getItem());
            si.setNum(numIt);
            WorldStackItems wSi = new WorldStackItems(world, si);
            wSi.spawn(xId, yId);
            world.add(wSi);
        }
    }
    
    
    public int id;
    public int numItem;
    public float density;
    public String nameKod;
    public TypeBlock type;
    
    public String item;
    public ArrayList<String> namesMaterial;
    public Item itemParent;
    
    public AnimTextureBlock animTexBlock = null;
    public PropertyUtil propertyBlock;

    public Block(){
        namesMaterial = new ArrayList<>();
        type = TypeBlock.SOLID;
        propertyBlock = new PropertyUtil();
    }
    
    public boolean isMaterial(String ma){
        return isMaterial(DataAll.get().getMaterialBlock(ma));
    }
    
    public boolean isMaterial(MaterialBlock crashType1){
        if(crashType1 == null) return false;
        for(String str : namesMaterial){
            if(str.equals(crashType1.name))
                return true;
        }
        return false;
    }

    public int getNumItem(){
        return numItem;
    }
    
    public Item getItem(){
        return Item.get(item);
    }
    
    public MaterialBlock getMaterial(int i){
        if(i < 0 || i > namesMaterial.size() - 1)
            return null;
        return DataAll.get().materialsBlock.get(namesMaterial.get(i));
    }
    
    public MaterialBlock getMaterial(String ma){
        if(!isMaterial(ma)) return null;
        return DataAll.get().materialsBlock.get(ma);
    }

    public boolean isPlayerCrach(){
        for(MaterialBlock mat : DataAll.get().listPlayerCrashMaterialBlock){
            if(isMaterial(mat))
                return true;
        }
        return false;
    }

    public Sprite getSprite(int x, int y){
        if(animTexBlock != null){
            animTexBlock.act();
            return animTexBlock.getTex();
        }
        return Block.getSprite(this);
    }
    
    public void init(){}
    public void bet(LayerBlock w, int x, int y){}
    public void crashed(LayerBlock w, int x, int y){}
    public boolean isBet(LayerBlock w, int x, int y){return true;}
    public void action(LayerBlock w, int x, int y){}
    public boolean isAction(LayerBlock w, int x, int y){return true;}
    public InventoryBlock getNewInventory(int x, int y){return null;}

    
    public void deserialize(JsonObject jo, JsonDeserializationContext jsc){
        id = jo.get("id").getAsInt();
        nameKod = jo.get("nameKod").getAsString();
        item = jo.get("item").getAsString();
        numItem = jo.get("numItem").getAsInt();
        density = jo.get("density").getAsFloat();
        type = TypeBlock.valueOf(jo.get("type").getAsString());
        propertyBlock = jsc.deserialize(jo.get("property"), PropertyUtil.class);
        if(jo.get("anim") != null){
            JsonObject obj = jo.getAsJsonObject("anim");
            animTexBlock = new AnimTextureBlock();
            animTexBlock.pause = obj.get("pause").getAsInt();
            animTexBlock.nameTexs = obj.get("frames").getAsString().split(":");
        }
        if(jo.get("materials") != null){
            namesMaterial.clear();
            namesMaterial.addAll(Arrays.asList(jo.get("materials").getAsString().split(":")));
        }
        DataAll.get().addBlock(this);
    }
    
    public void serialize(JsonObject jo, JsonSerializationContext jsc){
        jo.addProperty("id", id);
        jo.addProperty("nameKod", nameKod);
        jo.addProperty("item", item);
        jo.addProperty("numItem", numItem);
        jo.addProperty("density", density);
        jo.addProperty("type", type.toString());
        jo.add("property", jsc.serialize(propertyBlock));
        
        if(animTexBlock != null){
            JsonObject obj = new JsonObject();
            obj.addProperty("pause", animTexBlock.pause);
            StringJoiner sj = new StringJoiner(":");
            for(String fram : animTexBlock.nameTexs)
                sj.add(fram);
            obj.addProperty("frames", sj.toString());
            jo.add("anim", obj);
        }
        
        if(namesMaterial.size() > 0){
            StringJoiner sj = new StringJoiner(":");
            for(String nameMat : namesMaterial)
                sj.add(nameMat);
            jo.addProperty("materials", sj.toString());
        }
    }
}
