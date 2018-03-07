package lc.utils.particleEffect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Death
 */
public class Particle {

    public static int SIZE = 15;
    
    public Sprite reg;
    public float x, y;
    public float speedX, speedY;
    public float accelerationX, accelerationY;
    public int dirX, dirY;
    public float size = SIZE;
    public float deltaSize = 0.1f;
    public float deltaAlpha = 0.01f;
    private float alpha = 1;
    
    public boolean gravity = true;
    
    public void update(ParticleEffect eff){
        x += speedX;
        y += speedY;
        speedX += accelerationX * dirX;
        speedY += accelerationY * dirY;
        if(gravity){
            if(dirX == 1){
                if(speedX < 0) speedX = 0;
            }else if(dirX == -1){
                if(speedX > 0) speedX = 0;
            }
        }
        
        alpha -= deltaAlpha;
        size -= deltaSize;
        if(alpha < 0 || size <= 0) 
            eff.particles.remove(this);
        
        if(x < eff.border.x || y < eff.border.y ||
           x > eff.border.x + eff.border.width ||
           y > eff.border.y + eff.border.height)
            eff.particles.remove(this);
    }
    
    public void draw(SpriteBatch b){
        reg.setAlpha(alpha);
        reg.setPosition(x, y);
        reg.setSize(size, size);
        reg.setColor(0.9f, 0.9f, 0.9f, 1);
        reg.draw(b);
    }
}
