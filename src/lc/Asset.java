package lc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Death
 */
public class Asset {

    public static Skin skinUI;
    public static TextureRegion textureSlotInventory;
    public static TextureRegion textureCursor;
    public static TextureRegion redPixel;
    public static TextureRegion greenPixel;
    public static TextureRegion bluePixel;
    public static TextureRegion whitePixel;
    public static TextureRegion arrow;
    public static NinePatch background;
    
    public static Sprite backgroundImageScreenPrint;
    
    public static void load(){
        textureCursor = new TextureRegion(new Texture(Gdx.files.internal("res/cursorTexture.png")));
        skinUI = new Skin(Gdx.files.internal("res/style/uiskin.json"));
        textureSlotInventory = new TextureRegion(new Texture(Gdx.files.internal("res/textureSlotInventory.png")));
        TextureRegion[][] strengthTool = TextureRegion.split(new Texture(Gdx.files.internal("res/colorPixel.png")), 1, 1);
        arrow = new TextureRegion(new Texture(Gdx.files.internal("res/arrow.png")));
        redPixel = strengthTool[0][0];
        greenPixel = strengthTool[0][1];
        bluePixel = strengthTool[0][2];
        whitePixel = strengthTool[0][3];
        background = skinUI.getPatch("border-background");
        
        backgroundImageScreenPrint = new Sprite(new Texture(Gdx.files.internal("res/backgroundMenu.png")));
    }
}
