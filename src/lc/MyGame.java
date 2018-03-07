package lc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglCursor;
import com.badlogic.gdx.graphics.Pixmap;
import lc.item.DataAll;
import lc.item.DataAllGeneratorTest;
import lc.menu.ScreenMenu;
import org.lwjgl.opengl.GL11;

/**
 * @author Death
 */
public class MyGame extends Game{

    public static MyGame game;
    public static Music backgroundMusic;
    
    @Override
    public void create(){
//        DataAllGeneratorTest.gen();
//        DataAll.save(Gdx.files.local("./"));
        
        Asset.load();
        Pixmap pixmapCursor = new Pixmap(Gdx.files.internal("res/textureCursorMouse.png"));
        LwjglCursor cur = new LwjglCursor(pixmapCursor, 0, 0);
        Gdx.graphics.setCursor(cur);
        
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("res/fonRelaxsing.ogg"));
        backgroundMusic.setVolume(0.01f);
        backgroundMusic.play();
        
        this.setScreen(new ScreenMenu());
    }

    @Override
    public void render(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        super.render(); //To change body of generated methods, choose Tools | Templates.
    }
}
