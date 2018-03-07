package lc.item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import lc.Asset;
import lc.inventory.InventoryManager;
import static lc.inventory.InventoryManager.SIZE_ITEM;
import lc.item.block.Block;
import lc.item.craft.Recipe;
import lc.utils.PropertyUtil;
import lc.world.World;

/**
 * @author Death
 */
public class Item {

    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(Item.class, new ItemParser());
        gson.registerTypeAdapter(ItemTool.class, new ItemParser());
    }

    public static Item get(String name){
        return DataAll.get().getItem(name);
    }

    private static int intend = 3;
    public static StackItems drawStackItems(Batch b, StackItems si, float xI, float yI){
        if(si != null){
            if(si.isEmpty()){
                return null;
            }
            Sprite sp = DataAll.get().getSprite(si.item);
            if(sp == null){
                System.err.println(si.item.nameKod + ": " + si.item.nameTexture);
            }
            sp.setPosition(xI, yI);
            sp.setColor(1, 1, 1, 1);
            sp.setSize(SIZE_ITEM, SIZE_ITEM);
            sp.draw(b);
            String textNumItems = "";
            
            if(si.item instanceof ItemTool){
                ItemTool it = (ItemTool)si.item;
                float widthBar = SIZE_ITEM + 8;
                float oneProzStrength = (float) it.strengthMax / 100f;
                float prozStrength = (float) si.strngthTool / oneProzStrength;
                float oneProzWidth = (float) widthBar / 100f;
                float widthHe = oneProzWidth * prozStrength;
                b.draw(Asset.redPixel, xI - 4, yI - 4, widthBar, 5);
                b.draw(Asset.greenPixel, xI - 4, yI - 4, widthHe, 5);
            }else{
                textNumItems = String.valueOf(si.getNum());
                BitmapFont font = InventoryManager.getThis().font;
                font.draw(b, textNumItems, xI - intend, yI - intend + font.getCapHeight());
            }
        }
        return si;
    }
    
    public int id;
    public int maxNum;
    public String nameKod;
    public String nameRU;
    public String nameEN;
    public String nameTexture;
    public Recipe recipe = null;
    public PropertyUtil property;
    public ArrayList<String> namesMaterial;
    public String infaRU, infaEN;
    public String blockBet = null;
    
    public Block block;
    
    public TypeItem type;

    public Item(){
        type = TypeItem.ITEM_DEFAULT;
        namesMaterial = new ArrayList<>();
        property = new PropertyUtil();
    }
    
    public boolean isMaterial(String str){
        for(String name : namesMaterial)
            if(str.equals(name))
                return true;
        return false;
    }

    public Block getBlock(){
        if(blockBet != null)
            return DataAll.get().getBlock(blockBet);
        return block;
    }

    public String getNameLanguage(){
        return nameRU;
    }
    
    public void init(){};
    public void bet(World w, int x, int y){};
    public void crashed(World w, int x, int y){};
    
    public void deserialize(JsonObject jo, JsonDeserializationContext jsc){
        id = jo.get("id").getAsInt();
        maxNum = jo.get("maxNum").getAsInt();
        nameKod = jo.get("nameKod").getAsString();
        nameEN = jo.get("nameEN").getAsString();
        nameRU = jo.get("nameRU").getAsString();
        block = jsc.deserialize(jo.get("block"), Block.class);
        infaRU = jo.get("infaRU").getAsString();
        infaEN = jo.get("infaEN").getAsString();
        if(jo.get("blockBet") != null)
            blockBet = jo.get("blockBet").getAsString();
        nameTexture = jo.get("texture").getAsString();
        property = jsc.deserialize(jo.get("property"), PropertyUtil.class);
        recipe = jsc.deserialize(jo.get("recipe"), Recipe.class);
        if(recipe != null)
            recipe.itemRezult = this;
        namesMaterial.clear();
        namesMaterial.addAll(Arrays.asList(jo.get("materials").getAsString().split(":")));
        
        if(block != null){
            block.itemParent = this;
        }
        
        DataAll.get().addItem(this);
    }
    
    public void serialize(JsonObject jo, JsonSerializationContext jsc){
        jo.addProperty("type", type.toString());
        jo.addProperty("id", id);
        jo.addProperty("maxNum", maxNum);
        jo.addProperty("nameKod", nameKod);
        jo.addProperty("nameEN", nameEN);
        jo.addProperty("nameRU", nameRU);
        jo.addProperty("infaRU", infaRU);
        jo.addProperty("infaEN", infaEN);
        jo.addProperty("blockBet", blockBet);
        jo.addProperty("texture", nameTexture);
        jo.add("block", jsc.serialize(block));
        jo.add("recipe", jsc.serialize(recipe));
        jo.add("property", jsc.serialize(property));
        StringJoiner sj = new StringJoiner(":");
        for(String nameMat : namesMaterial)
            sj.add(nameMat);
        jo.addProperty("materials", sj.toString());
    }
}
