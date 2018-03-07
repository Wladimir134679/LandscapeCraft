package lc.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import lc.item.DataAll;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.world.objectes.TypeObjectWorld;
import lc.world.objectes.WorldObject;

/**
 * @author Death
 */
public class WorldStackItems extends WorldObject{

    public static int SIZE = Math.round(Block.SIZE * 0.75f);
    public static int GROUP = 1;
    
    public StackItems si;
    
    private World world;

    public WorldStackItems(World world, StackItems si){
        super();
        this.world = world;
        this.si = si;
        this.initPhysics(world);
        this.type = TypeObjectWorld.DYNAMIC;
        this.sprite = new Sprite(DataAll.get().getSprite(si.item));
        this.sprite.setAlpha(1);
        float color = 0.85f;
        this.sprite.setColor(color, color, color, 1);
        this.width = SIZE;
        this.height = SIZE;
        this.group = GROUP;
        this.maxSpeedGravity = 10;
        this.autoBrakeX = true;
        
        this.maxSpeedX = 5;
    }
    
    public WorldStackItems spawn(int xId ,int yId){
        this.x = xId * Block.SIZE + MathUtils.random(0, Block.SIZE - SIZE);
        this.y = yId * Block.SIZE + MathUtils.random(0, Block.SIZE - SIZE);
        this.sprite.setRotation(MathUtils.random(-45 / 2, 45 / 2));
        while(this.speedX == 0){
            this.speedX = MathUtils.random(-3, 3);
        }
        return this;
    }

    @Override
    public void draw(SpriteBatch b){
        super.draw(b); //To change body of generated methods, choose Tools | Templates.
        
        sprite.setOriginCenter();
        if(onLand){
            sprite.setRotation(0);
            return;
        }
        if(sprite.getRotation() > 0){
            sprite.setRotation(sprite.getRotation() - 1f);
        }else if(sprite.getRotation() < 0){
            sprite.setRotation(sprite.getRotation() + 1f);
        }
    }
}
