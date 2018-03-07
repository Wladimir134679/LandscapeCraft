package lc.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lc.item.block.Block;
import lc.world.World;
import lc.world.objectes.TypeObjectWorld;
import lc.world.objectes.WorldObject;

/**
 * @author Death
 */
public class LivingBody extends WorldObject{

    public int health, healthMax;
    public TextureRegion[] runAnim;
    public int numRunFrame;
    public int timeFrame;
    public TextureRegion stand;
    public TextureRegion jumpAnim;
    private int currentFrame;
    private long timeBeginAnim;

    public LivingBody(){
        super();
        currentFrame = 0;
    }
    
    public LivingBody(World world){
        this();
        this.type = TypeObjectWorld.DYNAMIC;
        this.initPhysics(world);
        this.heightJump = Block.SIZE * 2;
    }
    
    @Override
    public void draw(SpriteBatch b){
        if(health <= 0) death();
        super.draw(b);
    }
    
    public void update(){
        if(!onLand){
            sprite.setRegion(jumpAnim);
        }else if(speedX == 0){
            sprite.setRegion(stand);
        }else{
            if(!this.isCollisionBlock(-5, 0) && !this.isCollisionBlock((int)this.width + 5, 0)){
                if(System.currentTimeMillis() - timeBeginAnim > timeFrame){
                    timeBeginAnim = System.currentTimeMillis();
                    currentFrame++;
                    if(currentFrame > numRunFrame) currentFrame = 0;
                }
                sprite.setRegion(runAnim[currentFrame]);
            }
        }
        
        if(dirX == LEFT){
            sprite.setFlip(true, false);
        }else if(dirX == RIGHT){
            sprite.setFlip(false, false);
        }
    }

    @Override
    public void fall(float heightFall){
        int numBlock = Math.round(heightFall / Block.SIZE);
        if(numBlock > 10){
            this.health -= (numBlock - 5) / 100f * 75f;
        }
    }
    
    public void death(){
    }
}
