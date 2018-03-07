package lc.inventory.invBlocks;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import lc.item.block.BlockProperty;
import lc.block.managers.ManagerBlocks;
import lc.inventory.CellInventory;
import lc.inventory.InventoryParent;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.utils.PropertyUtil;
import lc.world.World;
import lc.world.WorldStackItems;

/**
 * @author Death
 */
public abstract class InventoryBlock extends InventoryParent{
    
    public PropertyUtil property;
    public Block block;
    private boolean isAutoClose;

    public InventoryBlock(Block block, PropertyUtil property){
        this.property = property;
        this.block = block;
        isAutoClose = true;
    }
    
    public boolean isPlayerDst(int dis){
        Vector2 posP = new Vector2(ManagerBlocks.getGame().player.getCenterXId(), 
                                   ManagerBlocks.getGame().player.getCenterYId());
        Vector2 posB = new Vector2(property.getInt("x"), property.getInt("y"));
        if(posB.dst(posP) < dis){
            return true;
        }
        return false;
    }
    
    public boolean isClose(){
        if(isAutoClose){
            return !ManagerBlocks.getGame().player.isBlockDst(property.getInt("x"), property.getInt("y"), 5);
        }else{
            return false;
        }
    }
    
    public void crashDrop(World w, int x, int y){
        ArrayList<CellInventory> cells = this.getDrop();
        for(CellInventory cell : cells){
            if(cell.stack == null) continue;
            StackItems si = cell.stack;
            WorldStackItems wSi = new WorldStackItems(w, si);
            wSi.spawn(x, y);
            w.add(wSi);
            cell.stack = null;
        }
    }

    public void setAutoClose(boolean b){
        this.isAutoClose = b;
    }
    
    public abstract ArrayList<CellInventory> getDrop();
    
    public void processingEnd(Block b, PropertyUtil prop){}
    

}
