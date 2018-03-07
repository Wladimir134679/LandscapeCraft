package lc.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lc.Asset;
import lc.MyGame;
import lc.game.GameScreen;
import lc.menu.ScreenMenu;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class MenuGameScreen{

    public Stage stage;
    public GameScreen game;

    public MenuGameScreen(GameScreen game){
        this.game = game;
        
        stage = new Stage(){
            @Override
            public boolean keyUp(int keyCode){
                if(keyCode == Keys.ESCAPE){
                    game.showMenu = false;
                    game.initShow();
                }
                return super.keyUp(keyCode); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        int width = 300;
        int height = 30;
        int x = Gdx.graphics.getWidth() / 2 - width / 2;
        int y = Gdx.graphics.getHeight() - 10 - height;
        int indent = height + 10;
        
        TextButton saveGame = new TextButton("Сохранить", Asset.skinUI, "default");
        saveGame.setSize(width, height);
        saveGame.setPosition(x, y);
        saveGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.isSave = true;
            }
        });
        stage.addActor(saveGame);
        
        TextButton backspacke = new TextButton("Назад в игру", Asset.skinUI, "default");
        backspacke.setSize(width, height);
        backspacke.setPosition(x, 10 + indent * 2);
        backspacke.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.showMenu = false;
                game.initShow();
            }
        });
        stage.addActor(backspacke);
        
        TextButton backMainMenu = new TextButton("В главное меню", Asset.skinUI, "default");
        backMainMenu.setSize(width, height);
        backMainMenu.setPosition(x, 10 + indent);
        backMainMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.game.setScreen(new ScreenMenu());
            }
        });
        stage.addActor(backMainMenu);
        
        TextButton exitGame = new TextButton("Выход из игры", Asset.skinUI, "default");
        exitGame.setSize(width, height);
        exitGame.setPosition(x, 10);
        exitGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }
        });
        stage.addActor(exitGame);
    }
    
    public void initShow(){
        Gdx.input.setInputProcessor(stage);
    }
    
    public void render(WCamera camera, SpriteBatch b){
        camera.update();
        b.setProjectionMatrix(camera.combined);
        
        b.begin();
        Asset.background.draw(b, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        b.end();
        stage.act();
        stage.draw();
    }
}
