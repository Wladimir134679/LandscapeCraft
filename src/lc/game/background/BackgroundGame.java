package lc.game.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
import lc.game.GameScreen;
import lc.player.Player;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class BackgroundGame {
    
    private Texture rootTexture;
    
    public ArrayList<Sprite> sprites; 
    public ArrayList<Cloud> clouds; 
    
    private final int maxNumCloud = 20;
    
    public final int widthTex;
    public final int heightTex;
    public int dirWind;
    public float speedWind;
    
    private GameScreen game;
    
    public BackgroundGame(GameScreen game){
        this.game = game;
        rootTexture = new Texture(Gdx.files.internal("res/clouds.png"));
        sprites = new ArrayList<>();
        clouds = new ArrayList<>();
        
        dirWind = 0;
        widthTex = 42;
        heightTex = 25;
        
        TextureRegion[][] regs = TextureRegion.split(rootTexture, widthTex, heightTex);
        for(int i = 0; i < regs.length; i++){
            for(int j = 0; j < regs[i].length; j++){
                Sprite sp = new Sprite(regs[i][j]);
                sprites.add(sp);
            }
        }
        
        for(int i = 0; i < maxNumCloud; i++){
            Cloud c = new Cloud(this);
            clouds.add(c);
        }
        setWind(1, 0.05f);
    }
    
    public void render(WCamera cam, SpriteBatch b){
        for(Cloud c : clouds){
            c.draw(b);
        }
        
        float delta = game.player.getDeltaX();
        if(delta > cam.getWidth() || delta < -cam.getWidth())
            delta = 0;
        for(Cloud c : clouds){
            if(delta > 0){
                c.update(-1, delta);
            }else if(delta < 0){
                c.update(1, delta);
            }else{
                c.update(0, 0);
            }
        }
    }
    
    public void setWind(int dirWind, float speed){
        if(dirWind != -1 && dirWind != 1) return;
        this.speedWind = speed;
        this.dirWind = dirWind;
        for(Cloud c : clouds){
            c.dirMove = dirWind;
        }
    }

}
