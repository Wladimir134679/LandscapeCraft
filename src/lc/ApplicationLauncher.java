package lc;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.text.ParseException;
import static lc.SettingApp.*;

/**
 *
 * @author all
 */
public class ApplicationLauncher {
    
    public static LwjglApplication app;
    
    public static void main(String[] args) throws ParseException {
        SettingApp.load();
        LwjglApplicationConfiguration cnf = new LwjglApplicationConfiguration();
        cnf.width = WIDTH_WIN;
        cnf.height = HEIGHT_WIN;
        cnf.title = "Landscape Craft";
        cnf.resizable = true;
        cnf.forceExit = false;
        cnf.addIcon("res/icon.png", Files.FileType.Internal);
        cnf.allowSoftwareMode = true;
        cnf.resizable = false;
        cnf.fullscreen = FULLSCREEN;
        
        MyGame.game = new MyGame();
        app = new LwjglApplication(MyGame.game, cnf);
    }

}
