package lc.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import java.util.ArrayList;
import lc.Asset;
import lc.item.StackItems;

/**
 * @author Death
 */
public class MechCellInventory extends WidgetGroup{

    public ArrayList<CellInventory> cells;
    public int sizeCells;
    
    public int numRow, numCol, numRowMax;
    public int numScroll;
    
    public Rectangle buttonUp;
    public Rectangle buttonDown;
    public Sprite spButtonUp, spButtonDown;
    public boolean isScroll;

    public MechCellInventory(int col, int row){
        cells = new ArrayList<>();
        numRow = row;
        numCol = col;
        sizeCells = row * col;
        for(int i = 0; i < sizeCells; i++){
            CellInventory ci = new CellInventory(this);
            cells.add(ci);
            this.addActor(ci);
        }
        numRowMax = row;
        numScroll = 0;
        buttonUp = new Rectangle();
        buttonDown = new Rectangle();
        buttonUp.setSize(40, 20);
        buttonDown.setSize(40, 20);
        spButtonDown = new Sprite(Asset.arrow);
        spButtonUp = new Sprite(Asset.arrow);
        spButtonUp.setFlip(false, true);
        isScroll = false;
        
        this.setWidth(getWidth());
        this.setHeight(getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        synPosition();
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
//    public void draw(Batch b){
//        this.draw(b, x, y);
//    }
    
//    public void draw(Batch b, int x, int y){
//        this.x = x;
//        this.y = y;
//        y += getHeight();
//        y += CellInventory.INTEND;
//        sizeCells = cells.size();
//        
//        if(sizeCells == 0)
//            return;
//        
//        int beginI = 0;
//        int endI = numCol * numRowMax;
//        
//        beginI += (numScroll * numCol);
//        endI += (numScroll * numCol);
//        
//        if(endI > sizeCells)
//            endI = sizeCells;
//        
//        if(isScroll){
//            if(isNextUp()){
//                spButtonUp.setColor(InventoryManager.CELL_INV_SP.getColor());
//            }else{
//                spButtonUp.setColor(Color.DARK_GRAY);
//            }
//            buttonUp.x = this.x + this.getWidth() / 2 - buttonUp.width / 2;
//            buttonUp.y = y - buttonUp.height;
//            spButtonUp.setSize(buttonUp.width, buttonUp.height);
//            spButtonUp.setPosition(buttonUp.x, buttonUp.y);
//            spButtonUp.draw(b);
//            y -= buttonUp.height - 5;
//        }
//        
//        for(int i = beginI; i < endI; i++){
//            x += (CellInventory.SIZE + CellInventory.INTEND);
//            if(i % numCol == 0){
//                y -= (CellInventory.SIZE + CellInventory.INTEND);
//                x = this.x;
//            }
//            cells.get(i).draw(b, x, y);
//        }
//        
//        if(isScroll){
//            if(isNextDown()){
//                spButtonDown.setColor(InventoryManager.CELL_INV_SP.getColor());
//            }else{
//                spButtonDown.setColor(Color.DARK_GRAY);
//            }
//            buttonDown.x = this.x + this.getWidth() / 2 - buttonDown.width / 2;
//            buttonDown.y = y - buttonDown.height - 5;
//            spButtonDown.setSize(buttonDown.width, buttonDown.height);
//            spButtonDown.setPosition(buttonDown.x, buttonDown.y);
//            spButtonDown.draw(b);
//        }
//        
//    }
    
    public void synPosition(){
        this.setWidth(getWidth());
        this.setHeight(getHeight());
        sizeCells = cells.size();
        int beginI = 0;
        int endI = numCol * numRowMax;
        
        beginI += (numScroll * numCol);
        endI += (numScroll * numCol);
        if(endI > sizeCells)
            endI = sizeCells;
        
        if(isScroll){
            if(isNextUp()){
                spButtonUp.setColor(InventoryManager.CELL_INV_SP.getColor());
            }else{
                spButtonUp.setColor(Color.DARK_GRAY);
            }
            buttonUp.x = this.getWidth() / 2 - buttonUp.width / 2;
            buttonUp.y = this.getHeight();
            spButtonUp.setSize(buttonUp.width, buttonUp.height);
            spButtonUp.setPosition(buttonUp.x, buttonUp.y);
        }
        float x = 0;
        float y = getHeight();
        for(int i = beginI; i < endI; i++){
            x += (CellInventory.SIZE + CellInventory.INTEND);
            if(i % numCol == 0){
                y -= (CellInventory.SIZE + CellInventory.INTEND);
                x = 0;
            }
            cells.get(i).setPosition(x, y);
        }
        
        if(isScroll){
            if(isNextDown()){
                spButtonDown.setColor(InventoryManager.CELL_INV_SP.getColor());
            }else{
                spButtonDown.setColor(Color.DARK_GRAY);
            }
            buttonDown.x = this.getWidth() / 2 - buttonDown.width / 2;
            buttonDown.y = 0 - buttonDown.height;
            spButtonDown.setSize(buttonDown.width, buttonDown.height);
            spButtonDown.setPosition(buttonDown.x, buttonDown.y);
        }
    }
    
//    public void synPosition(float x, float y){
//        this.setX(x);
//        this.setY(y);
//        y += getHeight();
//        y += CellInventory.INTEND;
//        sizeCells = cells.size();
//        
//        if(sizeCells == 0)
//            return;
//        
//        int beginI = 0;
//        int endI = numCol * numRowMax;
//        
//        beginI += (numScroll * numCol);
//        endI += (numScroll * numCol);
//        
//        if(endI > sizeCells)
//            endI = sizeCells;
//        
//        if(isScroll){
//            if(isNextUp()){
//                spButtonUp.setColor(InventoryManager.CELL_INV_SP.getColor());
//            }else{
//                spButtonUp.setColor(Color.DARK_GRAY);
//            }
//            buttonUp.x = this.getX() + this.getWidth() / 2 - buttonUp.width / 2;
//            buttonUp.y = y - buttonUp.height;
//            spButtonUp.setSize(buttonUp.width, buttonUp.height);
//            spButtonUp.setPosition(buttonUp.x, buttonUp.y);
//            y -= buttonUp.height - 5;
//        }
//        
//        for(int i = beginI; i < endI; i++){
//            x += (CellInventory.SIZE + CellInventory.INTEND);
//            if(i % numCol == 0){
//                y -= (CellInventory.SIZE + CellInventory.INTEND);
//                x = (int)this.getX();
//            }
//            cells.get(i).setX(x);
//            cells.get(i).setY(y);
//        }
//        
//        if(isScroll){
//            if(isNextDown()){
//                spButtonDown.setColor(InventoryManager.CELL_INV_SP.getColor());
//            }else{
//                spButtonDown.setColor(Color.DARK_GRAY);
//            }
//            buttonDown.x = this.getX() + this.getWidth() / 2 - buttonDown.width / 2;
//            buttonDown.y = y - buttonDown.height - 5;
//            spButtonDown.setSize(buttonDown.width, buttonDown.height);
//            spButtonDown.setPosition(buttonDown.x, buttonDown.y);
//        }
//    }
    
    public boolean isNextUp(){
        if(!isScroll) return false;
        return numScroll > 0;
    }
    
    public boolean isNextDown(){
        if(!isScroll) return false;
        return numScroll < this.getMaxScrollRow();
    }
    
    public CellInventory get(int col, int row){
        return cells.get(col * row);
    }
    
    public CellInventory getCellClick(int xM, int yM){
        for(int i = 0; i < cells.size(); i++)
            if(cells.get(i).isContains(xM, yM)){
                CellInventory ci = cells.get(i);
                ci.indexX = this.getIndexCol(i);
                ci.indexY = this.getIndexRow(i);
                return ci;
            }
        return null;
    }
    
    public boolean addStack(StackItems si){
        if(addStack(si, 0, false)) return true;
        else return addStack(si, 0, true);
    }
    
    public boolean addStack(StackItems si, boolean flag){
        return addStack(si, 0, flag);
    }
    
    private boolean addStack(StackItems si, int pos, boolean swob){
        if(si == null || si.isEmpty())
            return false;
        for(int i = pos; i < cells.size(); i++){
            StackItems stackSlot = cells.get(i).stack;
                if(swob){
                    if(stackSlot == null){
                        cells.get(i).stack = si;
                        return true;
                    }
                }else{
                    if(stackSlot != null){
                        if(stackSlot.equals(si)){
                            if(stackSlot.addStackAll(si)){
                                if(si.isEmpty()){
                                    return true;
                                }else{
                                    return this.addStack(si, i, swob);
                                }
                            }
                        }
                    }
                }
        }
        return false;
    }
    
    public boolean isMouseContains(int xM, int yM){
        return (xM > getX() && 
                yM > getY() && 
                xM < getX() + getWidth() && 
                yM < getY() + getHeight());
    }

    @Override
    public float getWidth(){
        return (numCol * (CellInventory.SIZE + CellInventory.INTEND)) - CellInventory.INTEND;
    }

    @Override
    public float getHeight(){
        sizeCells = cells.size();
        if(sizeCells == 0) return 0;
        int nums = (int)Math.floor(sizeCells / (float)numCol);
        int plus = 0;
        if(sizeCells % numCol != 0)
            nums++;
        if(nums > numRowMax){
            nums = numRowMax;
        }
        if(isNextUp()){
            plus += buttonUp.height + 5;
        }
        if(isNextDown()){
            plus += buttonDown.height + 5;
        }
        return (int)(nums * (float)(CellInventory.SIZE + CellInventory.INTEND)) - CellInventory.INTEND + plus;
    }

    public int getIndexCol(int i){
        if(numCol == 0) return 0;
        return (i - MathUtils.floor(i / numCol));
    }

    public int getIndexRow(int i){
        if(numRow == 0) return 0;
        return (MathUtils.floor(i / numRow));
    }
    
    public int getNumRow(){
        return (int)(Math.floor(cells.size() / numCol));
    }
    
    public int getMaxScrollRow(){
        return (getNumRow() - numRowMax) + 1;
    }
    
    public void upRow(){
        if(!isScroll) return;
        numScroll -= 1;
        if(numScroll < 0)
            numScroll = 0;
    }
    
    public void downRow(){
        if(!isScroll) return;
        numScroll += 1;
        if(numScroll > getMaxScrollRow())
            numScroll = getMaxScrollRow();
        
    }
    
    public boolean mouseClick(int x, int y, int button){
        if(!isScroll) return false;
        if(isNextUp()){
            if(buttonUp.contains(x, y)){
                upRow();
                return true;
            }
        }
        if(isNextDown()){
            if(buttonDown.contains(x, y)){
                downRow();
                return true;
            }
        }
        return false;
    }
}
