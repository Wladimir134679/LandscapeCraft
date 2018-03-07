package lc.inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import lc.item.StackItems;

/**
 * @author Death
 */
public abstract class InventoryParent extends WidgetGroup{

    public static void load(InventoryParent inv, DataInputStream in) throws IOException{
        ArrayList<CellInventory> cells = new ArrayList<>();
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            CellInventory ci = new CellInventory();
            cells.add(ci);
            inv.addActor(ci);
            
            int len = in.readInt();
            if(len == -1)
                continue;
            byte[] data = new byte[len];
            in.read(data);
            ci.stack = StackItems.setData(data);
        }
        inv.setAllSaveCell(cells);
    }
    
    public static void save(InventoryParent inv, DataOutputStream in) throws IOException{
        byte[] data;
        ArrayList<CellInventory> cellsAll = inv.getAllSaveCell();
        in.writeInt(cellsAll.size());
        for(int i = 0; i < cellsAll.size(); i++){
            CellInventory ci = cellsAll.get(i);
            if(ci.stack == null){
                in.writeInt(-1);
                continue;
            }
            data = StackItems.getData(ci.stack);
            in.writeInt(data.length);
            in.write(data);
        }
    }
    public boolean isLayStack(CellInventory si){return true;}
    public void open(){}
    public void close(){}
    
    public abstract boolean isMouseContains(int x, int y);
    public abstract boolean addStack(StackItems si);
    
    public abstract CellInventory getCellClick(int xM, int yM);
    public abstract CellInventory getCellMouseContains(int xM, int yM);
    public abstract ArrayList<CellInventory> getAllSaveCell();
    public abstract void setAllSaveCell(ArrayList<CellInventory> cells);

//    public boolean defaultMouseDown(CellInventory cell, int x, int y, int button){
//        if(this.isMouseContains(x, y)){
//            if(this.isLayStack(cell)){
//                if(button == Input.Buttons.LEFT){
//                    InventoryManager.getThis().clickDownMouseLeft(cell);
//                }else if(button == Input.Buttons.RIGHT){
//                    InventoryManager.getThis().clickDownMouseRight(cell);
//                }
//                return true;
//            }
//        }
//        return false;
//    }
}
