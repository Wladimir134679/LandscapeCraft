package lc.utils.particleEffect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import lc.game.GameScreen;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class ManagerPartileEffect {

    private static GameScreen games;
    private static ManagerPartileEffect managerPartileEffect;
    
    public static void init(GameScreen game){
        ManagerPartileEffect.games = game;
        managerPartileEffect = new ManagerPartileEffect(game.batch, game.camera);
    }
    
    public static void updateManager(){
        managerPartileEffect.update();
    }
    
    public static void drawManager(){
        managerPartileEffect.draw();
    }
    
    public static void createCrashBlock(Sprite tex, int x, int y, float speed){
        ParticleEffect pr = managerPartileEffect.create();
        pr.createEffectCrashBlock(tex, x, y, MathUtils.random(20, 50), speed);
    }
    
    public static void createBoom(Sprite tex, int x, int y){
        ParticleEffect pr = managerPartileEffect.create();
        pr.createEffectCrashBoom(tex, x, y, MathUtils.random(40, 50));
    }
    
    public SpriteBatch batch;
    public WCamera camera;
    
    public ArrayList<ParticleEffect> particleEffects; 

    public ManagerPartileEffect(SpriteBatch batch, WCamera camera){
        this.batch = batch;
        this.camera = camera;
        particleEffects = new ArrayList<>();
    }
    
    public ParticleEffect create(){
        ParticleEffect part = new ParticleEffect(this);
        particleEffects.add(part);
        return part;
    }
    
    public void update(){
        for(int i = 0; i < particleEffects.size(); i++){
            particleEffects.get(i).update();
        }
    }
    
    public void draw(){
        for(int i = 0; i < particleEffects.size(); i++){
            particleEffects.get(i).draw();
        }
    }
}
