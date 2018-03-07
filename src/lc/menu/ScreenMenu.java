package lc.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lc.Asset;
import lc.MyGame;
import org.lwjgl.opengl.GL11;

/**
 * @author Death
 */
public class ScreenMenu extends ScreenMenuParent{

    public ScreenSelectWorld selectWorld;
    
    private TextButton game, exitGame;
    
    public ScreenMenu(){
        super();
        selectWorld = null;
    }
    
    @Override
    public void show(Stage st){
        GL11.glClearColor(0, 0, 0f, 1);
        
        int width = 300;
        int height = 30;
        int x = Gdx.graphics.getWidth() / 2 - width / 2;
        int y = Gdx.graphics.getHeight() - 10 - height;
        int indent = height + 10;
        
        game = new TextButton("Игра", Asset.skinUI, "default");
        game.setSize(width, height);
        game.setPosition(x, y);
        game.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(selectWorld == null)
                    selectWorld = new ScreenSelectWorld(ScreenMenu.this);
                MyGame.game.setScreen(selectWorld);
            }
        });
        stage.addActor(game);
        
        exitGame = new TextButton("Выход", Asset.skinUI, "default");
        exitGame.setSize(width, height);
        exitGame.setPosition(x, 10);
        exitGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.exit(0);
            }
        });
        stage.addActor(exitGame);
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void draw(SpriteBatch b){
        Asset.backgroundImageScreenPrint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Asset.backgroundImageScreenPrint.setPosition(0, 0);
        Asset.backgroundImageScreenPrint.draw(b);
        Asset.background.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
