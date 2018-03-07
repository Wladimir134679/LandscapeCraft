package lc.inventory;

import com.badlogic.gdx.Input.Buttons;
import lc.game.Cursor;
import lc.item.StackItems;

/**
 * @author Death
 */
public class CellClickMouse {
    
    private static CellClickMouse cellClickMouse = null;
    
    public static CellClickMouse get(StackItems ci, StackItems si){
        if(cellClickMouse == null)
            cellClickMouse = new CellClickMouse();
        cellClickMouse.start(ci, si);
        return cellClickMouse;
    }

    public StackItems click = null;
    public long timeBegin;
    public long timePause;
    public StackItems result = null;
    public int num = 1;
    
    public boolean isRigth = false;
    
    public void start(StackItems click, StackItems si){
        timeBegin = 0;
        timePause = 150;
        this.click = click;
        this.result = si;
        num = 1;
        isRigth = false;
    }
    
    public void update(){
        if(click == null || result == null) return;
        if(System.currentTimeMillis() - timeBegin > timePause){
            timeBegin = System.currentTimeMillis();
            timePause -= 10;
            if(timePause < 25) timePause = 0;
            if(isRigth){
                if(click != null && !click.isEmpty()){
                    result.addStack(click, num);
                }
                else{
                    clear();
                }
            }else{
                
            }
        }
    }
    
    public void stop(){
        clear();
    }
    
    public void clear(){
        result = null;
        click = null;
        timeBegin = 0;
        timePause = 0;
    }
}
