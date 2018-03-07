package lc.inventory;

import java.util.ArrayList;
import lc.item.StackItems;

/**
 * @author Death
 */
public class InventoryPocket extends InventoryParent{

    public MechCellInventory mech;
    
    public InventoryPocket(){
        mech = new MechCellInventory(9, 4);
        this.addActor(mech);
        this.setSize(mech.getWidth(), mech.getHeight());
    }
    
    @Override
    public boolean isMouseContains(int x, int y){
        return mech.isMouseContains(x, y);
    }

    @Override
    public boolean addStack(StackItems si){
        return mech.addStack(si);
    }
    
    public boolean addStackNotSwob(StackItems si){
        return mech.addStack(si, false);
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
}
