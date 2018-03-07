package lc.inventory.invBlocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import lc.inventory.CellInventory;
import lc.inventory.InventoryCraft;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class InventoryCraftingTable extends InventoryBlock{

    public InventoryCraft craft;
    
    public InventoryCraftingTable(Block block, PropertyUtil property){
        super(block, property);
        craft = new InventoryCraft(9);
        craft.toolsBlock = block.nameKod;
        craft.setMaxRowResult(2);
        craft.indent = (int)craft.result.buttonUp.height + 5 + 5;
        this.addActor(craft);
        this.setSize(craft.getWidth(), craft.getHeight());
    }

    @Override
    public boolean isMouseContains(int x, int y){
        return craft.isMouseContains(x, y);
    }

    @Override
    public boolean addStack(StackItems si){
        return craft.addStack(si);
    }

    @Override
    public CellInventory getCellClick(int xM, int yM){
        return craft.getCellClick(xM, yM);
    }

    @Override
    public ArrayList<CellInventory> getAllSaveCell(){
        return craft.bar.cells;
    }

    @Override
    public CellInventory getCellMouseContains(int xM, int yM){
        return craft.getCellMouseContains(xM, yM);
    }
    
    @Override
    public void setAllSaveCell(ArrayList<CellInventory> cells){
        craft.bar.cells = cells;
    }
    
    @Override
    public ArrayList<CellInventory> getDrop(){
        return craft.bar.cells;
    }
}
