package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lc.Asset;
import lc.SettingApp;
import lc.game.background.BackgroundGame;
import lc.item.Item;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class Display {

    public WCamera camera;
    public SpriteBatch batch;
    public boolean isDrawInfa;
    public BackgroundGame backgroundGame;
    private GameScreen game;
    private BitmapFont fontInfa, fontInfaBig;
    private String infa;
    
    public Stage stage;
    
    private Sprite healthBar, healthBackground;
    private int xHealthBar, yHealthBar;
    private int widthHealthBar, heigthHealthBar;

    public Display(GameScreen game){
        this.game = game;
        camera = new WCamera();
        camera.setToOrtho(false, SettingApp.WIDTH_WIN, SettingApp.HEIGHT_WIN);
        batch = new SpriteBatch();
        
        stage = new Stage();
        stage.getViewport().setCamera(camera);
//        stage.setDebugAll(true);
        
        fontInfa = Asset.skinUI.get("font-small", BitmapFont.class);
        fontInfaBig = Asset.skinUI.get("font-big", BitmapFont.class);
        infa = "";
        isDrawInfa = false;
        
        healthBar = new Sprite(new Texture(Gdx.files.internal("res/healthBar.png")));
        healthBackground = new Sprite(new Texture(Gdx.files.internal("res/healthBackground.png")));
        widthHealthBar = 220;
        heigthHealthBar = 25;
        xHealthBar = 5;
        yHealthBar = camera.getHeight() - 5 - heigthHealthBar;
        backgroundGame = new BackgroundGame(game);
    }
    
    public void draw(){
        stage.act();
        stage.draw();
        
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
//        game.inventory.draw(batch);
        this.drawStackItems();
        this.drawInfa();
        this.drawHelathBar();
        batch.end();
    }
    
    public void drawBackground(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        backgroundGame.render(camera, batch);
        batch.end();
    }
    
    public void drawSave(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        
        fontInfa.draw(batch, "Сохранение...", 10, 10 + fontInfa.getCapHeight());
        
        batch.end();
    }
    
    private void drawHelathBar(){
        float widthBar = widthHealthBar - 26;
        float oneProzHel = (float) game.player.healthMax / 100f;
        float prozHel = (float) game.player.health / oneProzHel;
        float oneProzWidth = (float) widthBar / 100f;
        float widthHe = oneProzWidth * prozHel;
        healthBackground.setPosition(xHealthBar, yHealthBar);
        healthBackground.setSize(widthHealthBar, heigthHealthBar);
        healthBackground.draw(batch);
        healthBar.setPosition(xHealthBar + 25, yHealthBar + 1);
        healthBar.setSize(widthHe, 25 - 2);
        healthBar.draw(batch);
        String infaHealth = String.valueOf((int)(prozHel) + "%");
        fontInfa.draw(batch, infaHealth, xHealthBar + 25, yHealthBar + heigthHealthBar / 2 + fontInfa.getCapHeight() / 2);
    }
    
    private void drawStackItems(){
        if(Cursor.STACK_ITEMS == null) return;
        Item.drawStackItems(batch, Cursor.STACK_ITEMS, camera.getMouseX(), camera.getMouseY() + 4);
        Cursor.drawInfaItem(camera, batch, fontInfa, Cursor.STACK_ITEMS);
    }
    
    private void drawInfa(){
        if(!isDrawInfa) return;
        infa = String.valueOf("FPS: " + Gdx.graphics.getFramesPerSecond());
        fontInfa.draw(batch, infa, 5, fontInfa.getCapHeight() + 5);
        infa = String.valueOf("Взаимодействие с фоном: " + game.player.isInteractionBackLavel());
        fontInfa.draw(batch, infa, 5, fontInfa.getCapHeight() + 5 + fontInfa.getCapHeight() + 5);
        
        infa = String.valueOf("Название мира: " + game.gameInfa.nameWorld);
        fontInfa.draw(batch, infa, 5, (fontInfa.getCapHeight() + 5) * 3);
        infa = String.valueOf("Папка: " + game.gameInfa.dirPath);
        fontInfa.draw(batch, infa, 5, (fontInfa.getCapHeight() + 5) * 4);
    }
}
