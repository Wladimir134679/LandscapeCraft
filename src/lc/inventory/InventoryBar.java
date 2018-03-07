package lc.inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import static lc.inventory.InventoryManager.PICKED_CELL_SP;
import lc.item.StackItems;

/**
 * @author Death
 */
public class InventoryBar extends InventoryParent{

    public MechCellInventory mech;
    
    public int pickedCell;

    public InventoryBar(){
        mech = new MechCellInventory(9, 1);
        pickedCell = 0;
        this.addActor(mech);
        this.setSize(mech.getWidth(), mech.getHeight());
    }

    @Override
    public void draw(Batch b, float parentAlpha){
//        SPRITE_BACKGROUND_INV.setPosition(getX(), getY());
//        SPRITE_BACKGROUND_INV.setSize(getWidth(), getHeight());
//        SPRITE_BACKGROUND_INV.draw(b);
        super.draw(b, parentAlpha);
        if(InventoryManager.getThis().isOpen()){
            PICKED_CELL_SP.setAlpha(0.5f);
        }else{
            PICKED_CELL_SP.setAlpha(1f);
        }
        PICKED_CELL_SP.setSize(CellInventory.SIZE, CellInventory.SIZE);
        PICKED_CELL_SP.setPosition(this.getX() + (CellInventory.SIZE + CellInventory.INTEND) * pickedCell, this.getY() - CellInventory.INTEND);
        PICKED_CELL_SP.draw(b);
    }
    
    public StackItems getPickedStack(){
        return mech.cells.get(pickedCell).stack;
    }
    
    public void minus(){
        if(!InventoryManager.getThis().isOpen()){
            pickedCell--;
            if(pickedCell < 0)
                pickedCell = mech.numCol - 1;   
        }
    }
    
    public void plus(){
        if(!InventoryManager.getThis().isOpen()){
            pickedCell++;
            if(pickedCell >= mech.numCol)
                pickedCell = 0;
        }
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x, y);
        mech.setPosition(0, 0);
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
