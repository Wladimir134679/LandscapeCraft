package lc.utils.particleEffect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 * @author Death
 */
public class ParticleEffect {

    private ManagerPartileEffect manager;
    
    public float x, y;
    public Rectangle border;
    public ArrayList<Particle> particles;

    public ParticleEffect(ManagerPartileEffect manager){
        this.manager = manager;
        particles = new ArrayList<>();
        border = new Rectangle();
    }
    
    public void createEffectCrashBlock(Sprite tex, int x, int y, int num){
        this.createEffectCrashBlock(tex, x, y, num, 1f);
    }
    
    public void createEffectCrashBlock(Sprite tex, int x, int y, int num, float speed){
        border.set(manager.camera.getX(), manager.camera.getY(),
                   manager.camera.getWidth(), manager.camera.getHeight());
        this.x = x;
        this.y = y;
        for(int i = 0; i < num; i++){
            Sprite reg = new Sprite(tex, 
                    MathUtils.random(0, tex.getRegionWidth() - Particle.SIZE),
                    MathUtils.random(0, tex.getRegionHeight() - Particle.SIZE),
                    Particle.SIZE,
                    Particle.SIZE);
            Particle part = new Particle();
            part.x = x;
            part.y = y;
            part.reg = reg;
            part.dirX = MathUtils.random(-5, 5) >= 0 ? 1 : -1;
            part.dirY = -1;
            part.accelerationX = -MathUtils.random(0.01f, 0.1f) * speed;
            part.accelerationY = MathUtils.random(0.01f, 0.1f) * 2 * speed;
            
            part.speedX = MathUtils.random(1, 5) * part.dirX;
            part.deltaSize *= speed;
            
            particles.add(part);
        }
    }
    
    public void createEffectCrashBoom(Sprite tex, int x, int y, int num){
        border.set(manager.camera.getX(), manager.camera.getY(),
                   manager.camera.getWidth(), manager.camera.getHeight());
        this.x = x;
        this.y = y;
        for(int i = 0; i < num; i++){
            Sprite reg = new Sprite(tex, 
                    MathUtils.random(0, tex.getRegionWidth() - Particle.SIZE),
                    MathUtils.random(0, tex.getRegionHeight() - Particle.SIZE),
                    Particle.SIZE,
                    Particle.SIZE);
            Particle part = new Particle();
            part.x = x;
            part.y = y;
            part.reg = reg;
            part.dirX = MathUtils.random(-5, 5) >= 0 ? 1 : -1;
            part.dirY = MathUtils.random(-5, 5) >= 0 ? 1 : -1;
            part.accelerationX = MathUtils.random(0.01f, 0.2f);
            part.accelerationY = MathUtils.random(0.01f, 0.2f);
            part.deltaSize = 0.5f;
            part.size = Particle.SIZE * 2;
            part.gravity = false;
            
            part.speedX = MathUtils.random(1, 4) * part.dirX;
            part.speedY = MathUtils.random(1, 4) * part.dirY;
            
            particles.add(part);
        }
    }
    
    public void update(){
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update(this);
        }
        if(particles.size() <= 0) 
            manager.particleEffects.remove(this);
    }
    
    public void draw(){
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).draw(manager.batch);
        }
    }
    
    
}
