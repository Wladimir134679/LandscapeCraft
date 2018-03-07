package lc.inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lc.game.Cursor;
import static lc.inventory.InventoryManager.CELL_INV_SP;
import static lc.inventory.InventoryManager.SIZE_ITEM;
import lc.item.Item;
import lc.item.StackItems;

/**
 * @author Death
 */
public class CellInventory extends Actor{

    public static int SIZE = 36;
    public static int INTEND = 5;
    
    public StackItems stack = null;
    
    public int indexX = 0;
    public int indexY = 0;
    private MechCellInventory mech;

    public CellInventory(){
        this(null);
    }
    

    public CellInventory(MechCellInventory mech){
        this.mech = mech;
//        this.setPosition(0, 0);
        this.setSize(SIZE, SIZE);
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
            }
            
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount){
                return false;
            }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                InventoryManager.getThis().getStage().setScrollFocus(CellInventory.this);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                InventoryManager.getThis().getStage().setScrollFocus(null);
            }
        });
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(InventoryManager.getThis().isOpen()){
                    if(Cursor.STACK_ITEMS == null){
                        if(CellInventory.this.stack != null){
                            Cursor.STACK_ITEMS = CellInventory.this.stack;
                            CellInventory.this.stack = null;
                        }
                    }else{
                        if(CellInventory.this.stack != null){
                            StackItems si = CellInventory.this.stack;
                            CellInventory.this.stack = Cursor.STACK_ITEMS;
                            Cursor.STACK_ITEMS = si;
                        }else{
                            CellInventory.this.stack = Cursor.STACK_ITEMS;
                            Cursor.STACK_ITEMS = null;
                        }
                    }
                }else{
                    InventoryManager.getThis().bar.pickedCell = 
                            InventoryManager.getThis().bar.getCellClick(Math.round(x), Math.round(y)).indexX;
                }
            }
            
        });
    }

        
    @Override
    public void draw(Batch b, float parentAlpha){
//        System.out.println("lc.inventory.CellInventory.draw(): " + getX() + ":" + getY());
        super.draw(b, parentAlpha);
        CELL_INV_SP.setPosition(getX(), getY());
        CELL_INV_SP.setSize(SIZE, SIZE);
        CELL_INV_SP.draw(b);
        if(stack == null) return;
        if(stack.isEmpty()){
            stack = null;
            return;
        }
        Item.drawStackItems(b, stack, getX() + (SIZE - SIZE_ITEM) / 2, 
                                      getY() + (SIZE - SIZE_ITEM) / 2);
    }
    
    
    public boolean isContains(int xM, int yM){
        return (xM > getX() && xM < getX() + SIZE && 
                yM > getY() && yM < getY() + SIZE);
    }
}
