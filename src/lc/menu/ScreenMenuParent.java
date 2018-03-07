package lc.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lc.SettingApp;
import lc.utils.WCamera;

/**
 * @author Death
 */
public abstract class ScreenMenuParent implements Screen{

    public WCamera camera;
    public SpriteBatch batch;
    public Stage stage;
    
    public ScreenMenuParent(){
        camera = new WCamera();
        batch = new SpriteBatch();
        stage = new Stage();
    }
    
    @Override
    public void show(){
        camera.setToOrtho(false, SettingApp.WIDTH_WIN, SettingApp.HEIGHT_WIN);
        stage.getActors().clear();
        show(stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        draw(batch);
        batch.end();
        
        stage.act();
        stage.draw();
    }
    
    public abstract void show(Stage st);
    public abstract void draw(SpriteBatch b);

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
    }

    @Override
    public void dispose(){
    }

}
