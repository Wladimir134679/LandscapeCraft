package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import lc.Asset;
import lc.inventory.InventoryManager;
import lc.item.ItemTool;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.utils.WCamera;
import lc.utils.particleEffect.ManagerPartileEffect;

/**
 * @author Death
 */
public class Cursor implements InputProcessor{

    public static StackItems STACK_ITEMS = null;
    
    public static StackItems getStack(InventoryManager inv){
        if(STACK_ITEMS == null){
            return inv.bar.getPickedStack();
        }else{
            return STACK_ITEMS;
        }
    }
    
    public static StackItems getTool(InventoryManager inv){
        if(STACK_ITEMS == null){
            StackItems si = inv.bar.getPickedStack();
            if(si != null){
                if(si.item instanceof ItemTool)
                    return si;
            }
        }else{
            if(STACK_ITEMS.item instanceof ItemTool)
                return STACK_ITEMS;
        }
        return null;
    }
    
    public static void drawInfaItem(WCamera camera, Batch batch, BitmapFont font, StackItems si){
        String name = si.item.getNameLanguage();
        font.draw(batch, name, camera.getMouseX(), camera.getMouseY() - 32);
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
            String infa = String.valueOf(si.num + "/" + si.max);
            font.draw(batch, infa, camera.getMouseX(), camera.getMouseY() - 32 - 5 - font.getCapHeight());
            infa = si.item.infaRU;
            font.draw(batch, infa, camera.getMouseX(), camera.getMouseY() - 32 - ((5 + font.getCapHeight()) * 2));
        }else{
            font.draw(batch, "(LeftSHIFT)", camera.getMouseX(), camera.getMouseY() - 32 - 5 - font.getCapHeight());
        }
    }
    
    public Sprite sprite;
    public int xIdEvent, yIdEvent;
    public int xId, yId;
    public boolean isBet;
    public boolean isCrash;
    public boolean isMoveCursorEvent;
    private WCamera cam;
    private GameScreen game;
    
    private TextureRegion[] crashBlockAnim;
    private Sprite spCrash;
    private long timeBeginCrashBlock, timeBeginCrachBlockFrame;
    private long timeBeginBetBlock, timeDeltaBet;
    private float timeCrashBlock, timeCrashBlockFrame;
    private int indexFrameCrash;
    private StackItems toolCrashBlock;
    private boolean flagTemp1 = false;

    public Cursor(WCamera cam, GameScreen game){
        this.cam = cam;
        this.game = game;
        isBet = false;
        isCrash = false;
        isMoveCursorEvent = false;
        timeDeltaBet = 500;
        
        sprite = new Sprite(Asset.textureCursor);
        sprite.setSize(Block.SIZE, Block.SIZE);
        
        TextureRegion[][] regCrash = TextureRegion.split(new Texture(Gdx.files.internal("res/crashBlock.png")), 32, 32);
        crashBlockAnim = regCrash[0];
        spCrash = new Sprite();
        timeBeginCrashBlock = 0;
    }
    
    public void draw(SpriteBatch b){
        if(STACK_ITEMS != null){
            if(STACK_ITEMS.isEmpty()){
                STACK_ITEMS = null;
                System.out.println("lc.game.Cursor.draw()");
            }
            return;
        }
        xId = Math.round(cam.getMouseX() / Block.SIZE);
        yId = Math.round(cam.getMouseY() / Block.SIZE);
        if(xId < 0) xId = 0;
        if(yId < 0) yId = 0;
        if(xId >= game.world.widthBlock) xId = game.world.widthBlock - 1;
        if(yId >= game.world.heightBlock) yId = game.world.heightBlock - 1;
        int x = xId * Block.SIZE;
        int y = yId * Block.SIZE;
        
        if(!InventoryManager.getThis().isOpen()){
            sprite.setPosition(x, y);

            this.moveCursor();

            sprite.setSize(Block.SIZE, Block.SIZE);
            this.crachUpdate(b, x, y);
            this.betUpdate(b, x, y);
            
            sprite.setColor(Color.WHITE);
            sprite.setAlpha(1f);
            
            flagTemp1 = false;
            Block bl = game.getInteractionLayerBlock().getBlock(xId, yId);
            if(bl == null){
                if(!game.player.isBetBlockDst(xId, yId)){
                    sprite.setColor(Color.rgba8888(0.75f, 0.75f, 0.75f, 1f));
                    sprite.setAlpha(0.5f);
                }else{
                    sprite.setColor(Color.rgba8888(1f, 0, 0, 0.9f));
                    sprite.setAlpha(1f);
                    flagTemp1 = true;
                }
            }else{
                if(!game.player.isCrashBlock(game.world, xId, yId)){
                    sprite.setColor(Color.rgba8888(0.75f, 0.75f, 0.75f, 1f));
                    sprite.setAlpha(0.5f);
                }else{
                    sprite.setColor(Color.rgba8888(0, 1f, 0, 0.9f));
                    sprite.setAlpha(1f);
                    flagTemp1 = true;
                }
            }
            if(game.player.isInteractionBackLavel()){
                Color color = sprite.getColor();
                sprite.setColor(color.r - 0.1f, color.g - 0.1f, color.b - 0.1f, color.a);
                sprite.setAlpha(0.75f);
            }
            if(!flagTemp1){
                sprite.setColor(Color.DARK_GRAY);
                sprite.setAlpha(0.25f);
            }
            
            sprite.draw(b);
        }
    }
    
    public void betUpdate(SpriteBatch b, int x, int y){
        if(isBet){
            if(System.currentTimeMillis() - timeBeginBetBlock > timeDeltaBet){
                if(game.player.isBetBlock(game.world, xId, yId)){
                    StackItems si = Cursor.getStack(game.inventory);
                    if(si != null && !si.isEmpty()){
                        Block blockBet = si.item.getBlock();
                        if(blockBet != null){
                            if(game.world.isBetBlock(blockBet, this, xId, yId)){
                                if(blockBet.isBet(game.getInteractionLayerBlock(), xId, yId)){
                                    timeBeginBetBlock = System.currentTimeMillis();
                                    game.getInteractionLayerBlock().setBlock(blockBet, xId, yId);
                                    blockBet.bet(game.getInteractionLayerBlock(), xId, yId);
                                    si.num--;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void crachUpdate(SpriteBatch b, int x, int y){
        if(isCrash){
            sprite.setAlpha(0.5f);
            Block block = game.getInteractionLayerBlock().getBlock(xId, yId);
            if(block == null) return;
            if(!game.player.isCrashBlock(game.world, xId, yId)) return;
            if(System.currentTimeMillis() - timeBeginCrashBlock > timeCrashBlock){
                block.crashed(game.getInteractionLayerBlock(), xId, yId);
                game.getInteractionLayerBlock().setBlock(null, xId, yId);
                ManagerPartileEffect.createCrashBlock(block.getSprite(xId, yId), xId * Block.SIZE, yId * Block.SIZE, 2);
                if(game.player.isDropBlock(block)){
                    Block.crashed(block, game.world, xId, yId);
                }
                if(toolCrashBlock != null){
                    toolCrashBlock.crash();
                    toolCrashBlock = null;
                }
            }
            float sizeC = Block.SIZE * 1.2f;
            sprite.setSize(sizeC, sizeC);
            sprite.setPosition(x - (sizeC - Block.SIZE) / 2, y - (sizeC - Block.SIZE) / 2);
            this.drawCrashBlock(b);
        }else{
            sprite.setAlpha(1);
        }
    }
    
    private void drawCrashBlock(SpriteBatch b){
        if(System.currentTimeMillis() - timeBeginCrachBlockFrame > timeCrashBlockFrame){
            timeBeginCrachBlockFrame = System.currentTimeMillis();
            indexFrameCrash++;
            if(indexFrameCrash >= crashBlockAnim.length) indexFrameCrash = crashBlockAnim.length - 1;
        }
        spCrash.setPosition(xId * Block.SIZE, yId * Block.SIZE);
        spCrash.setRegion(crashBlockAnim[indexFrameCrash]);
        spCrash.setSize(Block.SIZE, Block.SIZE);
        spCrash.draw(b);
    }
    
    private void clickRight(int xM, int yM){
        int x = Math.round(cam.getMouseX(xM) / Block.SIZE);
        int y = Math.round(cam.getMouseY(yM) / Block.SIZE);
        Block b = game.getInteractionLayerBlock().getBlock(x, y);
        if(b != null){
            if(b.isAction(game.getInteractionLayerBlock(), x, y)){
                b.action(game.getInteractionLayerBlock(), x, y);
            }
        }else{
            bet(x, y);
        }
    }
    
    public void bet(int x, int y){
        if(isCrash) return;
        isBet = true;
        xIdEvent = x;
        yIdEvent = y;
        timeBeginBetBlock = 0;
    }
    
    public void crash(){
        this.crash(Gdx.input.getX(), Gdx.input.getY());
    }
    
    public void crash(int x, int y){
        if(isBet) return;
        xIdEvent = Math.round(cam.getMouseX(x) / Block.SIZE);
        yIdEvent = Math.round(cam.getMouseY(y) / Block.SIZE);
        if(xIdEvent < 0) xIdEvent = 0;
        if(yIdEvent < 0) yIdEvent = 0;
        if(xIdEvent >= game.world.widthBlock) xIdEvent = game.world.widthBlock - 1;
        if(yIdEvent >= game.world.heightBlock) yIdEvent = game.world.heightBlock - 1;
        Block block = game.getInteractionLayerBlock().getBlock(xIdEvent, yIdEvent);
        isCrash = true;
        indexFrameCrash = 0;
        if(block != null){
            StackItems stack = Cursor.getTool(game.inventory);
            if(stack != null && stack.isTool()){
                toolCrashBlock = stack;
            }else{
                toolCrashBlock = null;
            }
            timeBeginCrashBlock = System.currentTimeMillis();
            timeCrashBlock = 1000f * (float)((float)block.density / (float)game.player.getForce(block));
            timeCrashBlockFrame = timeCrashBlock / (crashBlockAnim.length - 2);
        }
    }
    
    private boolean moveCursor(){
        if(xId != xIdEvent || yId != yIdEvent){
            if(isCrash){
                crash();
            }
            return true;
        }
        return false;
    }
    
    private final Rectangle size = new Rectangle();
    public Rectangle getRectangle(){
        return size.set(xId * Block.SIZE, yId * Block.SIZE, Block.SIZE, Block.SIZE);
    }

    @Override
    public boolean keyDown(int i){
        return false;
    }

    @Override
    public boolean keyUp(int i){
        return false;
    }

    @Override
    public boolean keyTyped(char c){
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3){
        switch(i3){
            case Input.Buttons.LEFT: crash(i, i1); break;
            case Input.Buttons.RIGHT: clickRight(i, i1); break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3){
        isBet = false;
        isCrash = false;
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2){
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1){
        return false;
    }

    @Override
    public boolean scrolled(int i){
        game.inventory.scrollMouse(i);
        return false;
    }
}
