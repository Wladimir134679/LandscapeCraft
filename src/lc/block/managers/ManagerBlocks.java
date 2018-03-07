package lc.block.managers;

import com.badlogic.gdx.math.Vector2;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import lc.game.GameScreen;
import lc.inventory.InventoryParent;
import lc.inventory.invBlocks.InventoryBlock;
import lc.item.DataAll;
import lc.item.block.Block;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class ManagerBlocks {

    private static HashMap<String, InventoryBlock> toolBlocks;
    private static GameScreen game;
    
    public static void init(GameScreen game){
        ManagerBlocks.game = game;
        toolBlocks = new HashMap<>();
    }
    
    public static GameScreen getGame(){
        return game;
    }
    
    public static boolean isPlayerDst(int x, int y, int dis){
        Vector2 posP = new Vector2(getGame().player.getCenterXId(), 
                                   getGame().player.getCenterYId());
        Vector2 posB = new Vector2(x, y);
        if(posB.dst(posP) < dis){
            return true;
        }
        return false;
    }
    
    public static void load(GameScreen game, DataInputStream inChest) throws IOException{
        int numChest = inChest.readInt();
        for(int i = 0; i < numChest; i++){
            int idB = inChest.readInt(); // LOAD
            String paint = inChest.readUTF();// LOAD
            String[] posC = paint.split(":");
            int x = Integer.parseInt(posC[0]);
            int y = Integer.parseInt(posC[1]);
            PropertyUtil property = new PropertyUtil();
            int lenght = inChest.readInt();
            byte[] data = new byte[lenght];
            inChest.read(data);
            property.setData(data);
            InventoryBlock inv = DataAll.get().blocks.get(idB).getNewInventory(x, y);
            InventoryParent.load(inv, inChest);// LOAD
            inv.property.append(property);
            toolBlocks.put(paint, inv);
        }
    }
    
    public static void save(GameScreen game, DataOutputStream outChest) throws IOException{
        Iterator<String> it = toolBlocks.keySet().iterator();
        outChest.writeInt(toolBlocks.size());
        while(it.hasNext()){
            String paint = it.next();
            InventoryBlock inv = toolBlocks.get(paint);
            outChest.writeInt(inv.block.id);//SAVE
            outChest.writeUTF(paint);//SAVE
            byte[] data = inv.property.getData();
            outChest.writeInt(data.length);
            outChest.write(data);
            InventoryParent.save(inv, outChest);//SAVE
        }
    }
    
    public static InventoryBlock get(Block block, int x, int y){
        String point = String.valueOf(x + ":" + y);
        if(toolBlocks.get(point) == null){
            InventoryBlock in = block.getNewInventory(x, y);
            toolBlocks.put(point, in);
            return in;
        }
        return toolBlocks.get(point);
    }

    public static void add(int x, int y, InventoryBlock in){
        toolBlocks.put(String.valueOf(x + ":" + y), in);
    }

    public static void remove(int x, int y){
        String point = String.valueOf(x + ":" + y);
        toolBlocks.remove(point);
    }
}
