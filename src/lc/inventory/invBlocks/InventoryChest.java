package lc.inventory.invBlocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import lc.inventory.CellInventory;
import lc.inventory.MechCellInventory;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.utils.PropertyUtil;

/**
 * @author Death
 */
public class InventoryChest extends InventoryBlock{

    public MechCellInventory mech;
    
    public InventoryChest(Block b, PropertyUtil property, int numRow){
        super(b, property);
        super.setAutoClose(true);
        mech = new MechCellInventory(9, numRow);
        this.addActor(mech);
        this.pack();
    }

    @Override
    public boolean isMouseContains(int x, int y){
        return mech.isMouseContains(x, y);
    }

    @Override
    public boolean addStack(StackItems si){
        return mech.addStack(si);
    }

    @Override
    public CellInventory getCellClick(int xM, int yM){
        return mech.getCellClick(xM, yM);
    }

    @Override
    public CellInventory getCellMouseContains(int xM, int yM){
        return mech.getCellClick(xM, yM);
    }
    
    @Override
    public ArrayList<CellInventory> getAllSaveCell(){
        return mech.cells;
    }

    @Override
    public void setAllSaveCell(ArrayList<CellInventory> cells){
        mech.cells = cells;
    }
    
    @Override
    public ArrayList<CellInventory> getDrop(){
        return mech.cells;
    }
    
}
