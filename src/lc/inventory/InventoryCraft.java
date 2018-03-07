package lc.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import lc.game.Cursor;
import lc.game.GameScreen;
import lc.item.StackItems;
import lc.item.block.BlocksFinder;
import lc.item.craft.CraftManager;
import lc.item.craft.Recipe;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class InventoryCraft extends InventoryParent{

    public MechCellInventory bar;
    public MechCellInventory result;
    
    public int x, y;
    public int indent;
    
    private long timeBegin, timePause;
    private boolean isCrafting;
    private StackItems[] items;
    
    public String toolsBlock;
    
    public InventoryCraft(){
        this(4);
    }

    public InventoryCraft(int len){
        bar = new MechCellInventory(len, 1);
        result = new MechCellInventory(len, 0);
        indent = 20 - (int)result.buttonUp.height + 5 + CellInventory.SIZE + CellInventory.INTEND;
        isCrafting = false;
        items = new StackItems[bar.numCol];
        result.numRowMax = 2;
        toolsBlock = null;
        result.isScroll = true;
        
        this.addActor(bar);
        this.addActor(result);
        this.setWidth(bar.getWidth());
        this.setHeight(bar.getHeight() + indent + result.getHeight());
    }
    
    
    public void setMaxRowResult(int i){
        this.result.numRowMax = i;
    }

    @Override
    public void draw(Batch b, float parentAlpha){
//        bar.draw(b);
        updateResult();
//        result.draw(b);
        super.draw(b, parentAlpha);
        WCamera cam = InventoryManager.getCamera();
        if(isCrafting){
            if(!result.isMouseContains(cam.getMouseX(), cam.getMouseY())){
                isCrafting = false;
                return;
            }
            if(System.currentTimeMillis() - timeBegin > timePause){
                timeBegin = System.currentTimeMillis();
                timePause -= 10;
                if(timePause < 50) timePause = 50;
                craft(cam.getMouseX(), cam.getMouseY());
            }
        }
    }
    
    public void craft(int x, int y){
        this.craft(x, y, false);
    }
    
    public void craft(int x, int y, boolean isClickRight){
        for(int i = 0; i < bar.numCol; i++){
            items[i] = bar.get(i, 1).stack;
        }
        for(CellInventory ci : result.cells){
            if(ci.stack == null)
                continue;
            if(ci.isContains(x, y)){
                
                Recipe recipe = ci.stack.item.recipe;
                if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
                    if(isClickRight){
                        InventoryManager.getThis().addStack(CraftManager.craftMax(recipe, null, items));
                    }else{
                        InventoryManager.getThis().addStack(CraftManager.craft(recipe, null, items, 1));
                    }
                }else{
                    if(isClickRight){
                        if(CraftManager.isCraftMax(recipe, items)){
                            Cursor.STACK_ITEMS = CraftManager.craftMax(recipe, Cursor.STACK_ITEMS, items);
                        }
                    }else{
                        if(CraftManager.isCraft(recipe, items, 1)){
                            Cursor.STACK_ITEMS = CraftManager.craft(recipe, Cursor.STACK_ITEMS, items, 1);
                        }
                    }
                }
                return;
            }
        }
    }
    
    public void updateResult(){
        result.cells.clear();
        result.clear();
        for(int i = 0; i < bar.numCol; i++){
            items[i] = bar.get(i, 1).stack;
        }
        String[] tools = CraftManager.getTools();
        for(String tool : tools){
            ArrayList<Recipe> recipes = CraftManager.recipe.get(tool);
            if(tool.equals("this")){
                addResults(recipes);
                continue;
            }
            if(toolsBlock == null)
                continue;
            int xM = GameScreen.GAME_SCREEN.player.getXId();
            int yM = GameScreen.GAME_SCREEN.player.getYId();
            BlocksFinder.getFinder(GameScreen.GAME_SCREEN.world, xM, yM, 10)
                    .filter((block, x1, y1) -> {
                        return block.nameKod.equals(tool); //To change body of generated lambdas, choose Tools | Templates.
                    })
                    .apply((block, x1, y1) -> {
                        addResults(recipes);
                    }).setAloneBlock(true)
                    .find();
        }
    }
    
    public void addResults(ArrayList<Recipe> recipes){
        for(Recipe rec : recipes){
            if(CraftManager.isCraft(rec, items, 1)){
                CellInventory sli = new CellInventory(result);
                sli.stack = new StackItems(rec.itemRezult);
                sli.stack.num = rec.numResult;
                result.cells.add(sli);
                result.addActor(sli);
            }
        }
    }

    @Override
    public void setPosition(float x, float y){
        result.setX(x);
        result.setY(y);
        bar.setX(x);
        bar.setY(y + result.getHeight() + indent);
//        bar.y = y + result.getHeight() + indent;
    }

    @Override
    public float getHeight(){
        return (bar.getHeight() + indent + result.getHeight());
    }
    
    @Override
    public boolean isMouseContains(int x, int y){
        return bar.isMouseContains(x, y) || result.isMouseContains(x, y);
    }

    @Override
    public boolean addStack(StackItems si){
        return false;
    }

    @Override
    public CellInventory getCellClick(int xM, int yM){
        return bar.getCellClick(xM, yM);
    }

    @Override
    public CellInventory getCellMouseContains(int xM, int yM){
        if(bar.getCellClick(xM, yM) != null) return bar.getCellClick(xM, yM);
        return result.getCellClick(xM, yM);
    }
    

    @Override
    public ArrayList<CellInventory> getAllSaveCell(){
        return null;
    }

    @Override
    public void setAllSaveCell(ArrayList<CellInventory> cells){
    }
    
//    @Override
//    public boolean mouseDown(int x, int y, int button){
//        updateResult();
//        if(button == Buttons.LEFT){
//            if(result.mouseClick(x, y, button))
//                return true;
//        }
//        if(bar.isMouseContains(x, y)){
//            if(button == Input.Buttons.LEFT){
//                InventoryManager.getThis().clickDownMouseLeft(bar.getCellClick(x, y));
//            }else if(button == Input.Buttons.RIGHT){
//                InventoryManager.getThis().clickDownMouseRight(bar.getCellClick(x, y));
//            }
//            return true;
//        }
//        if(result.isMouseContains(x, y)){
//            result.synPosition();
//            if(button == Input.Buttons.LEFT){
//                isCrafting = true;
//                timeBegin = 0;
//                timePause = 150;
//            }else if(button == Input.Buttons.RIGHT){
//                WCamera cam = InventoryManager.getCamera();
//                craft(cam.getMouseX(), cam.getMouseY(), true);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean mouseUp(int x, int y, int button){
//        isCrafting = false;
//        return false;
//    }
//
//    @Override
//    public boolean scrollMouse(int arg){
//        WCamera cam = InventoryManager.getCamera();
//        if(result.isMouseContains(cam.getMouseX(), cam.getMouseY())){
//            System.out.println("lc.inventory.InventoryCraft.scrollMouse()");
//            if(arg > 0){
//                result.downRow();
//            }else if(arg < 0){
//                result.upRow();
//            }
//            return true;
//        }
//        return false;
//    }
}
