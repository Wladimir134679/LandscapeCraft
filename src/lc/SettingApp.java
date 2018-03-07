package lc;

import java.awt.Toolkit;

/**
 * @author Death
 */
public class SettingApp {

    public static int WIDTH_WIN;
    public static int HEIGHT_WIN;
    public static boolean FULLSCREEN;
    public static String DIRICTORY_WORLD;
    
    public static void load(){
        FULLSCREEN = false;
        if(FULLSCREEN){
            WIDTH_WIN = Toolkit.getDefaultToolkit().getScreenSize().width;
            HEIGHT_WIN = Toolkit.getDefaultToolkit().getScreenSize().height;
        }else{
            WIDTH_WIN = 1024;
            HEIGHT_WIN = 600;
        }
        DIRICTORY_WORLD = "./worlds/";
    }
}
