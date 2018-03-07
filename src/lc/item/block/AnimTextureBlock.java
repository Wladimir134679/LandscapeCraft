package lc.item.block;

import com.badlogic.gdx.graphics.g2d.Sprite;
import lc.item.DataAll;

/**
 * @author Death
 */
public class AnimTextureBlock {

    public String[] nameTexs;
    public long beginTime;
    public long pause;
    private int currentFrame;
    
    public AnimTextureBlock(){
        
    }
    
    public void act(){
        if(System.currentTimeMillis() - beginTime > pause){
            beginTime = System.currentTimeMillis();
            currentFrame++;
            if(currentFrame >= nameTexs.length)
                currentFrame = 0;
        }
    }
    
    public Sprite getTex(){
        return DataAll.get().textures.tex.get(nameTexs[currentFrame]);
    }
}
