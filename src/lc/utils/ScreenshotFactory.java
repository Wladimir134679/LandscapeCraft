package lc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import java.nio.ByteBuffer;

/**
 * @author Death
 */
public class ScreenshotFactory {

    public static void saveScreenshot(){
        FileHandle file;
        do{
            file = new FileHandle("screenshot-" + System.currentTimeMillis() + ".png");
        }while(file.exists());
        Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        PixmapIO.writePNG(file, pixmap);
        pixmap.dispose();
    }
    
    public static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown){
//        final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
//        if(yDown){
//            ByteBuffer pixels = pixmap.getPixels();
//            int numByte = w * h * 4;
//            byte[] lines = new byte[numByte];
//            int numBytesPerLine = w * 4;
//            for(int i = 0; i < h; i++){
//                pixels.position((h - i - 1) * numBytesPerLine);
//                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
//            }
//            pixels.clear();
//            pixels.put(lines);
//        }
        byte[] bytes = ScreenUtils.getFrameBufferPixels(x, y, w, h, yDown);
        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pixmap.getPixels().clear();
        pixmap.getPixels().put(bytes);
        return pixmap;
    }
}
