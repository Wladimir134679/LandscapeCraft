package lc.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import lc.Asset;
import lc.game.Cursor;
import lc.game.GameScreen;
import lc.inventory.invBlocks.InventoryBlock;
import lc.item.StackItems;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class InventoryManager extends WidgetGroup{

    public static int SIZE_ITEM = (int)Math.ceil(CellInventory.SIZE * 0.6f);
    
    public static Sprite CELL_INV_SP = null;
    public static Sprite PICKED_CELL_SP = null;
    public static Sprite TEXTURE_SLOT_PICKED = null;
    private static InventoryManager sInv;
    private static WCamera cam;
    
    public static InventoryManager init(WCamera cam){
        PICKED_CELL_SP = new Sprite(new Texture(Gdx.files.internal("res/textureSlotPicked.png")));
        CELL_INV_SP = new Sprite(Asset.textureSlotInventory);
        TEXTURE_SLOT_PICKED = new Sprite(new Texture(Gdx.files.internal("res/textureSlotPicked.png")));
        CELL_INV_SP.setColor(Color.GOLD);
        sInv = new InventoryManager(cam);
        return sInv;
    }
    
    public static InventoryManager getThis(){
        return sInv;
    }
    
    public static WCamera getCamera(){
        return cam;
    }

    public static enum TypeOpen{
        POCKET, BLOCK_INV
    }
    
    public InventoryPocket pocket;
    public InventoryBar bar;
    public InventoryCraft craft;
    public InventoryBlock inv_block;
    public BitmapFont font;
    
    private TypeOpen typeOpen;
    private boolean open;
    
    private CellInventory cellClickRight;
    private long timeBeginClick, timePauseClick;
    private boolean isClickRight;

    public InventoryManager(WCamera cam){
        this.cam = cam;
        pocket = new InventoryPocket();
        bar = new InventoryBar();
        craft = new InventoryCraft();
        open = false;
        inv_block = null;
        typeOpen = TypeOpen.POCKET;
        font = Asset.skinUI.get("font-small", BitmapFont.class);
        cellClickRight = null;
        isClickRight = false;
        this.setPosition(0, 0);
        this.setSize(cam.getWidth(), cam.getHeight());
        this.addActor(bar);
        this.addActor(pocket);
        this.addActor(craft);
//        this.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
//                return InventoryManager.this.touchDown(Math.round(x), Math.round(y), button);
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
//                InventoryManager.this.touchUp(Math.round(x), Math.round(y), button);
//            }
//
//            @Override
//            public boolean scrolled(InputEvent event, float x, float y, int amount){
//                InventoryManager.this.scrollMouse(amount);
//                return false;
//            }
//            
//            @Override
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
//                if(pointer == -1)
//                    InventoryManager.this.getStage().setScrollFocus(InventoryManager.this);
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
//                if(pointer == -1)
//                    InventoryManager.this.getStage().setScrollFocus(null);
//            }
//            
//        });
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha){
        pocket.setVisible(false);
        bar.setVisible(false);
        craft.setVisible(false);
        if(inv_block != null)
            inv_block.setVisible(false);
        this.updateClickRight();
        if(open){
            switch(typeOpen){
                case POCKET: drawPocket(batch); break;
                case BLOCK_INV: drawInvBlock(batch); break;
            }
            drawInfaCell(batch);
        }else{
            drawBar(batch);
        }
        super.draw(batch, parentAlpha);
    }
    
    private void drawInfaCell(Batch b){
        CellInventory c = null;
        if(c == null) c = bar.getCellMouseContains(cam.getMouseX(), cam.getMouseY());
        if(c == null) c = pocket.getCellMouseContains(cam.getMouseX(), cam.getMouseY());
        if(typeOpen == TypeOpen.POCKET &&
           c == null) c = craft.getCellMouseContains(cam.getMouseX(), cam.getMouseY());
        if(typeOpen == TypeOpen.BLOCK_INV && inv_block != null &&
           c == null) c = inv_block.getCellMouseContains(cam.getMouseX(), cam.getMouseY());
        if(c == null || c.stack == null) return;
        
        Cursor.drawInfaItem(cam, b, font, c.stack);
    }
    
    private void updateClickRight(){
        if(isClickRight){
            if(cellClickRight == null || cellClickRight.stack == null)
                return;
            if(!cellClickRight.isContains(cam.getMouseX(), cam.getMouseY())){
                cellClickRight = null;
                isClickRight = false;
                return;
            }
            if(System.currentTimeMillis() - timeBeginClick > timePauseClick){
                timeBeginClick = System.currentTimeMillis();
                timePauseClick -= 10;
                if(timePauseClick < 50) timePauseClick = 50;
                if(Cursor.STACK_ITEMS == null){
                    Cursor.STACK_ITEMS = new StackItems(cellClickRight.stack.item);
                    Cursor.STACK_ITEMS.setNum(0);
                }
                Cursor.STACK_ITEMS.addStack(cellClickRight.stack, 1);
            }
        }
    }
    
    private void drawBar(Batch b){
        float x = cam.getWidth() / 2 - bar.getWidth() / 2;
        float y = cam.getHeight() - bar.getHeight() - 20;
        bar.setPosition(x, y);
        
        bar.setVisible(true);
    }
    
    private void drawPocket(Batch b){
        drawBar(b);
        float xPo = cam.getWidth() / 2 - bar.getWidth() / 2;
        float yPo = cam.getHeight() - 20 - bar.getHeight() - 20 - pocket.getHeight();
        pocket.setPosition(xPo, yPo);
        pocket.setVisible(true);
        
        float xC = bar.getX() + bar.getWidth() + 20;
        float yC = cam.getHeight() - 20 - craft.getHeight();
        craft.setPosition(xC, yC);
        craft.setVisible(true);
    }
    
    private void drawInvBlock(Batch b){
        if(inv_block.isClose()){
            this.close();
            return;
        }
        drawBar(b);
//        int xPo = cam.getWidth() / 2 - pocket.getWidth() - 15;
//        int yPo = cam.getHeight() - 20 - bar.getHeight() - 20 - pocket.getHeight();
        float xPo = cam.getWidth() / 2 - pocket.getWidth() / 2;
        float yPo = bar.getY() - 20 - pocket.getHeight();
        pocket.setPosition(xPo, yPo);
        pocket.setVisible(true);
        
//        int xB = cam.getWidth() / 2 + 15;
//        int yB = cam.getHeight() - 20 - bar.getHeight() - 20 - inv_block.getHeight();;
        float xB = cam.getWidth() / 2 - inv_block.getWidth() / 2;
        float yB = pocket.getY() - 20 - inv_block.getHeight();
        inv_block.setPosition(xB, yB);
        inv_block.setVisible(true);
    }
    
//    private boolean resultClick = false;
//    public boolean touchDown(int x, int y, int button){
////        x = cam.getMouseX(x);
////        y = cam.getMouseY(y);
//        resultClick = false;
//        if(isOpen()){
//            isClickRight = button == Buttons.RIGHT;
//            if(!resultClick) resultClick = pocket.mouseDown(x, y, button);
//            if(!resultClick) resultClick = bar.mouseDown(x, y, button);
//            if(typeOpen == TypeOpen.POCKET){
//                if(!resultClick) resultClick = craft.mouseDown(x, y, button);
//            }
//            if(typeOpen == TypeOpen.BLOCK_INV){
//                if(!resultClick) resultClick = inv_block.mouseDown(x, y, button);
//            }
//        }
//        if(!resultClick){
//            GameScreen.GAME_SCREEN.player.dropItems(Cursor.STACK_ITEMS);
//            Cursor.STACK_ITEMS = null;
//        }
//        return resultClick;
//    }
//
//    public boolean touchUp(int x, int y, int button){
////        x = cam.getMouseX(x);
////        y = cam.getMouseY(y);
//        resultClick = false;
//        if(isOpen()){
//            isClickRight = false;
//            if(!resultClick) resultClick = pocket.mouseUp(x, y, button);
//            if(!resultClick) resultClick = bar.mouseUp(x, y, button);
//            if(typeOpen == TypeOpen.POCKET){
//                if(!resultClick) resultClick = craft.mouseUp(x, y, button);
//            }
//            if(typeOpen == TypeOpen.BLOCK_INV){
//                if(!resultClick) resultClick = inv_block.mouseUp(x, y, button);
//            }
//        }else{
//            if(bar.isMouseContains(x, y)){
//                CellInventory ci = bar.getCellClick(x, y);
//                if(ci != null){
//                    bar.pickedCell = ci.indexX;
//                }
//                resultClick = true;
//            }
//        }
//        return resultClick;
//    }
//
//    public void clickDownMouseRight(CellInventory cellClick){
//        if(cellClick == null) return;
//        if(cellClick.stack == null){
//            if(Cursor.STACK_ITEMS != null){
//                cellClick.stack = new StackItems(Cursor.STACK_ITEMS.item);
//                int num = Cursor.STACK_ITEMS.num;
//                cellClick.stack.num = (int)Math.ceil(num / 2f);
//                Cursor.STACK_ITEMS.num = num - cellClick.stack.num;
//                isClickRight = false;
//            }
//        }else{
//            this.cellClickRight = cellClick;
//            timePauseClick = 150;
//            isClickRight = true;
//        }
//    }
//    
//    public void clickDownMouseLeft(CellInventory ci){
//        if(ci == null){
//            return;
//        }
//        if(ci.stack == null){
//            ci.stack = Cursor.STACK_ITEMS;
//            Cursor.STACK_ITEMS = null;
//        }else{
//            if(Cursor.STACK_ITEMS != null){
//                if(ci.stack.equals(Cursor.STACK_ITEMS)){
//                    ci.stack.addStackAll(Cursor.STACK_ITEMS);
//                    if(Cursor.STACK_ITEMS.isEmpty())
//                        Cursor.STACK_ITEMS = null;
//                }else{
//                    StackItems sis = Cursor.STACK_ITEMS;
//                    Cursor.STACK_ITEMS = ci.stack;
//                    ci.stack = sis;
//                }
//            }else{
//                Cursor.STACK_ITEMS = ci.stack;
//                ci.stack = null;
//            }
//        }
//    }
    
    public boolean scrollMouse(int i){
        if(!this.isOpen()){
            if(i > 0){
                bar.plus();
                return true;
            }
            if(i < 0){
                bar.minus();
                return true;
            }
        }else{
//            resultClick = false;
//            if(!resultClick) resultClick = pocket.scrollMouse(i);
//            if(!resultClick) resultClick = bar.scrollMouse(i);
//            if(typeOpen == TypeOpen.POCKET){
//                if(!resultClick) resultClick = craft.scrollMouse(i);
//            }
//            if(typeOpen == TypeOpen.BLOCK_INV){
//                if(!resultClick) resultClick = inv_block.scrollMouse(i);
//            }
        }
        return false;
    }
    
    public boolean addStack(StackItems si){
        boolean add = false;
        if(!bar.addStackNotSwob(si)){
            add = pocket.addStackNotSwob(si);
        }else{
            add = true;
        }
        if(!add){
            if(!bar.addStack(si)){
                add =  pocket.addStack(si);
            }else{
                add = true;
            }
        }
        return add;
    }
    
    public void showPocket(){
        typeOpen = TypeOpen.POCKET;
        open = true;
    }
    
    public void showInvBlock(InventoryBlock inv){
        if(inv == null) return;
        if(inv_block != null)
            this.removeActor(inv_block);
        typeOpen = TypeOpen.BLOCK_INV;
        inv_block = inv;
        this.addActor(inv_block);
        open = true;
    }
    
    public void close(){
        open = false;
    }
    
    public boolean isOpen(){
        return open;
    }
}
