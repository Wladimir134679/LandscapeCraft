package lc.inventory.invBlocks;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import lc.Asset;
import lc.item.block.BlockProperty;
import lc.block.processing.ProcessingBlockFurnace;
import lc.game.Cursor;
import lc.inventory.CellInventory;
import lc.inventory.InventoryManager;
import lc.inventory.MechCellInventory;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.item.block.BlockFurnace;
import lc.item.craft.CraftManager;
import lc.item.craft.Recipe;
import lc.utils.PropertyUtil;
import lc.utils.Timer;

/**
 * @author Death
 */
public class InventoryFurnace extends InventoryBlock{
    
    public MechCellInventory main;
    public MechCellInventory fuel;
    public MechCellInventory result;
    
    public Sprite processBar;
    public ProcessingBlockFurnace processing;
    public int numMelt;
    public Timer timerCool; // Таймер остывания
    
    public InventoryFurnace(Block block, PropertyUtil property){
        super(block, property);
        main = new MechCellInventory(1, 1);
        fuel = new MechCellInventory(1, 1);
        result = new MechCellInventory(1, 1);
        this.setWidth(InventoryManager.getThis().pocket.getWidth());
        this.setHeight(InventoryManager.getThis().pocket.getHeight());
        processBar = new Sprite(Asset.whitePixel);
        numMelt = 0;
        processing = new ProcessingBlockFurnace();
        timerCool = new Timer();
        timerCool.stop();
        
        this.addActor(main);
        this.addActor(fuel);
        this.addActor(result);
    }

    @Override
    public void draw(Batch b, float parentAlpha){
        super.draw(b, parentAlpha);
        
        int heightBar = 6;
        float widthBarMax = getWidth() / 2 + 40;
        float xB = getX() + getWidth() / 2 - (int)widthBarMax / 2;
        float yB = getY() + getHeight() / 2 - heightBar / 2;
        processBar.setSize(widthBarMax, heightBar);
        processBar.setColor(Color.DARK_GRAY);
        processBar.setAlpha(0.75f);
        processBar.setPosition(xB, yB);
        processBar.draw(b);
        if(processing.isRun()){
            if(timerCool.isRun)
                timerCool.stop();
            float widthBar = widthBarMax / 100f * processing.timer.getRemainingPercent();
            
            processBar.setSize(widthBar, heightBar);
            processBar.setColor(Color.RED);
            processBar.setAlpha(1f);
            processBar.setPosition(xB, yB);
            processBar.draw(b);
        }else{
            if(numMelt > 0){
                if(!timerCool.isRun){
                    timerCool.startMillis(5000);
                }else{
                    if(timerCool.isEnd()){
                        numMelt = 0;
                        timerCool.stop();
                    }
                }
            }
        }
        
        float heightMaxCool = CellInventory.SIZE + 4;
        float widthCool = 6;
        //Тут остывание
        float xCool = this.getX() + fuel.getX() + CellInventory.SIZE + 5;
        float yCool = this.getY() + fuel.getY() + CellInventory.SIZE / 2 - heightMaxCool / 2;
        processBar.setSize(widthCool, heightMaxCool);
        processBar.setColor(Color.DARK_GRAY);
        processBar.setAlpha(0.75f);
        processBar.setPosition(xCool , yCool);
        processBar.draw(b);
        float heigthCool = 0;
        if(!processing.isRun()){
            heigthCool = heightMaxCool;
        }
        if(numMelt > 0){
            if(timerCool.isRun){
                heigthCool = (heightMaxCool / 100f) * timerCool.getRemainingPercent();
            }
        }
        processBar.setSize(widthCool, heightMaxCool - heigthCool);
        processBar.setColor(Color.RED);
        processBar.setAlpha(1f);
        processBar.setPosition(xCool , yCool);
        processBar.draw(b);
    }

    public boolean isProcessingBegin(){
        if(fuel.cells.get(0).stack == null){
            if(numMelt <= 0)
                return false;//Топлева нет
        }
        StackItems si = main.cells.get(0).stack;
        if(si == null)
            return false;// Компонент нет
        Recipe re = CraftManager.getRecipe(si, "stone_furnace");
        if(re != null){
            if(si.num < re.numCom[0])
                return false;//Компонента не хватает
        }else{
            return false;//Рецепта нет
        }
        CellInventory cellResult = result.cells.get(0);
        if(cellResult.stack != null){
            if(cellResult.stack.item.id != re.itemRezult.id)
                return false;//В результате не он
            if(cellResult.stack.num + re.numResult > cellResult.stack.max)
                return false;//Не вмещается в результат
        }
        return true;
    }

    @Override
    public void processingEnd(Block b, PropertyUtil prop){
        Recipe re = CraftManager.getRecipe(main.cells.get(0).stack, "stone_furnace");
        if(re == null) return;
        
        if(!isProcessingBegin())
            return;
        CellInventory cellResult = result.cells.get(0);

        StackItems resu = new StackItems(re.itemRezult);
        resu.setNum(re.numResult);
        main.cells.get(0).stack.num -= re.numCom[0];
        if(cellResult.stack != null){
            cellResult.stack.addStackAll(resu);
        }else{
            cellResult.stack = resu;
        }
        numMelt -= 1;
        ((BlockFurnace)block).off(property);
        
        processinStart();
    }
    
    public void processinStart(){
        if(main.cells.get(0).stack == null) return;
        Recipe re = CraftManager.getRecipe(main.cells.get(0).stack, "stone_furnace");
        boolean start = true;
        if(isProcessingBegin()){
            if(main.cells.get(0).stack.num < re.numCom[0]) start = false;
            if(numMelt <= 0){
                StackItems fuelStack = fuel.cells.get(0).stack;
                if(fuelStack == null) start = false;
                if(fuelStack.isEmpty()) start = false;
                if(!fuelStack.item.isMaterial("fuel")) start = false;
                if(start){
                    numMelt = fuelStack.item.property.getInt("numMelt");
                    fuelStack.num -= 1;
                }
            }
            if(start && numMelt >= 0){
                processing.start(((BlockFurnace)block).getTimeMelt(), block, property);
                ((BlockFurnace)block).on(property);
            }
        }
    }
    
    @Override
    public void setPosition(float x, float y){
        this.setX(x);
        this.setY(y);
        main.setX(20);
        main.setY(getHeight() / 2 - CellInventory.SIZE / 2);
        fuel.setX(getWidth() / 2 - CellInventory.SIZE / 2);
        fuel.setY(getHeight() / 2 - CellInventory.SIZE - 20);
        result.setX(getWidth() - CellInventory.SIZE - 20);
        result.setY(getHeight() / 2 - CellInventory.SIZE / 2);
    }

    @Override
    public boolean isMouseContains(int x, int y){
        return (x > this.getX() && x < this.getX() + getWidth() && 
                y > this.getY() && y < this.getY() + getHeight());
    }

    @Override
    public boolean addStack(StackItems si){
        return main.addStack(si);
    }

    @Override
    public CellInventory getCellClick(int xM, int yM){
        return main.getCellClick(xM, yM);
    }

    @Override
    public CellInventory getCellMouseContains(int xM, int yM){
        CellInventory r = result.getCellClick(xM, yM);
        if(r == null)
            r = main.getCellClick(xM, yM);
        if(r == null)
            r = fuel.getCellClick(xM, yM);
        return r;
    }

    @Override
    public ArrayList<CellInventory> getAllSaveCell(){
        ArrayList<CellInventory> cells = new ArrayList<>();
        cells.add(main.cells.get(0));
        cells.add(result.cells.get(0));
        cells.add(fuel.cells.get(0));
        return cells;
    }

    @Override
    public ArrayList<CellInventory> getDrop(){
        ArrayList<CellInventory> cells = new ArrayList<>();
        cells.addAll(main.cells);
        cells.addAll(result.cells);
        cells.addAll(fuel.cells);
        return cells;
    }

    @Override
    public void setAllSaveCell(ArrayList<CellInventory> cells){
        main.cells.set(0, cells.get(0));
        result.cells.set(0, cells.get(1));
        fuel.cells.set(0, cells.get(2));
    }

//    @Override
//    public boolean mouseDown(int x, int y, int button){
//        if(this.isMouseContains(x, y)){
//            if(result.isMouseContains(x, y)){
//                CellInventory cell = result.getCellClick(x, y);
//                if(cell.stack == null) 
//                    return true;
//                if(button == Buttons.RIGHT){
//                    if(Cursor.STACK_ITEMS == null){
//                        Cursor.STACK_ITEMS = new StackItems(cell.stack.item);
//                        Cursor.STACK_ITEMS.setNum(0);
//                    }
//                    Cursor.STACK_ITEMS.addStack(cell.stack, 1);
////                  if(cell.stack.isEmpty()) // Ячейка сама позаботится о уделении пустого
//                }else if(button == Buttons.LEFT){
//                    if(Cursor.STACK_ITEMS == null){
//                        Cursor.STACK_ITEMS = cell.stack;
//                        cell.stack = null;
//                        return true;
//                    }
//                }
//            }else if(main.isMouseContains(x, y)){
//                super.defaultMouseDown(main.getCellClick(x, y), x, y, button);
//                if(main.cells.get(0).stack != null && 
//                   (fuel.cells.get(0).stack != null || numMelt > 0)){
//                    processinStart();
//                }
//            }else if(fuel.isMouseContains(x, y)){
//                super.defaultMouseDown(fuel.getCellClick(x, y), x, y, button);
//                if(fuel.cells.get(0).stack != null && numMelt == 0){
//                    processinStart();
//                }
//            }
//            return true;
//        }
//        return false;
//    }
    
}
