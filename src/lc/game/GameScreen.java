package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lc.SettingApp;
import lc.item.block.BlockProperty;
import lc.block.managers.ManagerBlocks;
import lc.block.processing.ManagerProcessingBlockTool;
import lc.game.menu.MenuGameScreen;
import lc.inventory.InventoryManager;
import lc.item.DataAll;
import lc.item.Item;
import lc.item.StackItems;
import lc.player.Player;
import lc.utils.ScreenshotFactory;
import lc.world.World;
import lc.utils.WCamera;
import lc.utils.particleEffect.ManagerPartileEffect;
import lc.world.LayerBlock;
import org.lwjgl.opengl.GL11;

/**
 * @author Death
 */
public class GameScreen implements Screen, InputProcessor{
    
    public static boolean IS_GAME_LOOP = true;
    
    public static GameScreen GAME_SCREEN;
    
    public World world;
    public WCamera camera;
    public SpriteBatch batch;
    public Player player;
    public Cursor cursor;
    public Display display;
    public InventoryManager inventory;
    public ManagerProcessingBlockTool processingBlockTool;
    
    public MenuGameScreen menuGame;
    
    public boolean showMenu;
    
    public boolean isSave;
    private boolean wsFlag1 = false;
    
    public GameInfa gameInfa;
    
    private InputMultiplexer multiplexer;

    public GameScreen(){
        GAME_SCREEN = this;
        DataAll.get().load(Gdx.files.local("./"));
        camera = new WCamera();
        camera.setToOrtho(false, SettingApp.WIDTH_WIN, SettingApp.HEIGHT_WIN);
        batch = new SpriteBatch();
        display = new Display(this);
        cursor = new Cursor(camera, this);
        menuGame = new MenuGameScreen(this);
        processingBlockTool = new ManagerProcessingBlockTool();
        showMenu = false;
        isSave = false;
        ManagerPartileEffect.init(this);
        ManagerBlocks.init(this);
        BlockProperty.init();
        inventory = InventoryManager.init(display.camera);
        display.stage.addActor(inventory);
        
        gameInfa = new GameInfa();
    }
    
    public void init(World world, GameInfa infa){
        this.world = world;
        this.gameInfa = infa;
        
        player = new Player(this);
        player.spawn();
        
        world.add(player);
    }
    
    @Override
    public void show(){
        IS_GAME_LOOP = true;
        multiplexer = new InputMultiplexer(display.stage, this, cursor);
        this.initShow();
        world.start();
        processingBlockTool.start();
    }
    
    public void initShow(){
        GL11.glClearColor(0.2f, 0.2f, 1f, 1);
        Gdx.input.setInputProcessor(multiplexer);
    }
    
    @Override
    public void render(float f){
        camera.position.set(player.x + player.width / 2, player.y + player.height / 2, 0);
        if(camera.getX() < 0) camera.setX(0);
        if(camera.getY() < 0) camera.setY(0);
        if(camera.getX() + camera.getWidth() > world.getWidth()) camera.setX(world.getWidth() - camera.getWidth());
        if(camera.getY() + camera.getHeight()> world.getHeight()) camera.setY(world.getHeight()- camera.getHeight());
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        display.drawBackground();
        batch.begin();
        world.draw(camera, batch);
        ManagerPartileEffect.drawManager();
        cursor.draw(batch);
        batch.end();
        
        if(showMenu){
            menuGame.render(display.camera, display.batch);
        }else{
            updateWorld();
            display.draw();
        }
        
        if(isSave){
            display.drawSave();
            if(wsFlag1){
                isSave = false;
                wsFlag1 = false;
                SaveGame.save(this);
            }else
                wsFlag1 = true;
        }
    }
    
    public void updateWorld(){
        player.update();
        world.update(camera);
        ManagerPartileEffect.updateManager();
    }
    
    public LayerBlock getInteractionLayerBlock(){
        if(player.isInteractionBackLayer){
            return world.backLayerBlock;
        }else{
            return world.ground;
        }
    }
    
    public void kitStart(){
        StackItems si = new StackItems(Item.get("stone_pixkaxe"));
        inventory.addStack(si);
        si = new StackItems(Item.get("stone_axe"));
        inventory.addStack(si);
        si = new StackItems(Item.get("stone_shovel"));
        inventory.addStack(si);
        si = new StackItems(Item.get("crafting_table"));
        inventory.addStack(si);
        si = new StackItems(Item.get("tree_oak"));
        si.num = 10;
        inventory.addStack(si);
    }

    @Override
    public void resize(int i, int i1){
    }

    @Override
    public void pause(){
    }

    @Override
    public void resume(){
    }

    @Override
    public void hide(){
        IS_GAME_LOOP = false;
    }

    @Override
    public void dispose(){
        GameScreen.IS_GAME_LOOP = false;
    }

    @Override
    public boolean keyDown(int i){
        switch(i){
            case Keys.SPACE: player.jump(true); break;
            case Keys.E: openInv(); break;
            case Keys.NUM_1: inventory.bar.pickedCell = 0; break;
            case Keys.NUM_2: inventory.bar.pickedCell = 1; break;
            case Keys.NUM_3: inventory.bar.pickedCell = 2; break;
            case Keys.NUM_4: inventory.bar.pickedCell = 3; break;
            case Keys.NUM_5: inventory.bar.pickedCell = 4; break;
            case Keys.NUM_6: inventory.bar.pickedCell = 5; break;
            case Keys.NUM_7: inventory.bar.pickedCell = 6; break;
            case Keys.NUM_8: inventory.bar.pickedCell = 7; break;
            case Keys.NUM_9: inventory.bar.pickedCell = 8; break;
            case Keys.F1: player.spawn(); break;
            case Keys.F2: ScreenshotFactory.saveScreenshot(); break;
            case Keys.F3: display.isDrawInfa = !display.isDrawInfa; break;
            case Keys.F5: camera.zoom += 0.5f; break;
            case Keys.F6: camera.zoom -= 0.5f; break;
            
            case Keys.I: world.lightWorld += 0.1f; break;
            case Keys.O: world.lightWorld -= 0.1f; break;
            
            
            case Keys.B: this.kitStart(); break;
        }
        return false;
    }
    
    private void openInv(){
        if(inventory.isOpen()){
            inventory.close();
        }else{
            inventory.showPocket();
        }
    }

    @Override
    public boolean keyUp(int i){
        switch(i){
            case Keys.SPACE: player.jump(false); break;
            case Keys.TAB: player.isInteractionBackLayer = !player.isInteractionBackLayer; break;
            
            case Keys.ESCAPE: showMenu = true;
                              menuGame.initShow(); break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c){
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3){
//        if(inventory.touchDown(i, i1, i3)) return false;
//        cursor.mouseClick(i3, i, i1);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3){
//        if(inventory.touchUp(i, i1, i3)) return false;
//        switch(i3){
//            case Buttons.LEFT: cursor.isCrash = false; break;
//            case Buttons.RIGHT: cursor.isBet = false; break;
//        }
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
//        return inventory.scrollMouse(i);
        return false;
    }

}
