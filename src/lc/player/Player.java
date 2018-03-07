package lc.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import lc.game.Cursor;
import lc.game.GameScreen;
import lc.item.DataAll;
import lc.item.ItemTool;
import lc.item.StackItems;
import lc.item.block.Block;
import lc.item.block.MaterialBlock;
import lc.mobs.LivingBody;
import lc.world.World;
import lc.world.WorldStackItems;
import lc.world.objectes.WorldObject;

/**
 * @author Death
 */
public class Player extends LivingBody{
    
    private GameScreen game;
    private float force;
    private int dstCrashBlock, dstBetBlock;
    
    public boolean isInteractionBackLayer;
    
    public Player(GameScreen game){
        super(game.world);
        this.game = game;
        this.sprite = new Sprite();
        this.force = 1f;
        this.heightJump = Block.SIZE * 2.5f;
        dstCrashBlock = 5;
        dstBetBlock = 6;
        
        this.width = 30;
        this.height = 60;
        this.timeFrame = 75;
        this.healthMax = 200;
        this.health = healthMax;
        
        this.maxSpeedX = 5;
        
        TextureRegion[][] regs = TextureRegion.split(new Texture(Gdx.files.internal("res/playerTexture.png")), 30, 60);
        this.stand = regs[0][0];
        this.runAnim = regs[1];
        this.numRunFrame = 7;
        this.jumpAnim = regs[2][0];
        sprite.setRegion(stand);
        
        isInteractionBackLayer = false;
    }
    
    public void spawn(){
        game.world.spawnObject("playerObject", this);
    }
    
    @Override
    public void update(){
        super.update();
        this.collisionObj();
        
        if(Gdx.input.isKeyPressed(Keys.A)){
            moveKey[WorldObject.LEFT] = true;
        }else{
            moveKey[WorldObject.LEFT] = false;
        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            moveKey[WorldObject.RIGHT] = true;
        }else{
            moveKey[WorldObject.RIGHT] = false;
        }
    }
    
    public boolean isInteractionBackLavel(){
        return isInteractionBackLayer;
    }
    
    public boolean isCrashBlock(World world, int x, int y){
        if(!game.getInteractionLayerBlock().isCrachBlock(x, y))
            return false;
        if(!isCrashBlockDst(x, y))
            return false;
        Block bl = game.getInteractionLayerBlock().getBlock(x, y);
//        if(bl.isPlayerCrach()){
//            return true;
//        }
//        for(String mat : bl.namesMaterial){
//            if(MaterialBlock.isMaterialPlayerCrach(mat))
//                return true;
//        }
        if(bl.isPlayerCrach()){
            return true;
        }
        StackItems it = Cursor.getTool(game.inventory);
        if(it == null){
            return true;
        }else{
            ItemTool tool = (ItemTool) it.item;
            if(tool.isCrashBlock(bl))
                return true;
        }
        return false;
    }
    
    public boolean isBetBlock(World world, int x, int y){
        if(!game.getInteractionLayerBlock().isBetBlock(x, y))
            return false;
        if(!isBetBlockDst(x, y))
            return false;
        return true;
    }
    
    public boolean isCrashBlockDst(int x, int y){
        return isBlockDst(x, y, dstCrashBlock);
    }
    
    public boolean isBetBlockDst(int x, int y){
        return isBlockDst(x, y, dstBetBlock);
    }
    
    public boolean isBlockDst(int x, int y, int dis){
        Vector2 posP = new Vector2(getCenterXId(), getCenterYId());
        Vector2 posB = new Vector2(x, y);
        if(posB.dst(posP) < dis){
            return true;
        }
        return false;
    }
    
    public void collisionObj(){
        ArrayList<WorldObject> siAr = game.world.gridObject.getObjectes(this.getRect(), WorldStackItems.GROUP);
        for(WorldObject ob : siAr){
            if(ob.getRect().overlaps(this.getRect())){
                WorldStackItems wsi = (WorldStackItems) ob;
                if(game.inventory.addStack(wsi.si)){
                    game.world.remove(ob);
                }
            }
        }
    }
    
    public float getForce(Block block){
        StackItems si = Cursor.getTool(game.inventory);
        if(si != null){
            ItemTool it = (ItemTool)si.item;
            if(isDropBlock(block)){
                return this.force + it.force;
            }else{
                return this.force;
            }
        }
        
        return force;
    }
    
    public boolean isDropBlock(Block block){
        StackItems si = Cursor.getTool(game.inventory);
        if(si != null){
            ItemTool it = (ItemTool)si.item;
            if(it.isCrashBlock(block)){
                return true;
            }else{
                return block.isPlayerCrach();
            }
        }
        return block.isPlayerCrach();
    }
    
    public int getXId(){
        return Math.round(this.x / Block.SIZE);
    }
    
    public int getYId(){
        return Math.round(this.y / Block.SIZE);
    }
    
    public void dropItems(StackItems si){
        if(si == null) return;
        WorldStackItems wsi = new WorldStackItems(game.world, si);
        wsi.y = this.y + this.height - wsi.height;
        if(dirX == RIGHT){
            wsi.speedX = 2;
            wsi.x = this.x + this.width;
        }
        else if(dirX == LEFT){
            wsi.speedX = -2;
            wsi.x = this.x - wsi.width;
        }else{
            wsi.speedX = 0;
            wsi.x = this.x - wsi.width;
            wsi.speedY = 2;
        }

        game.world.add(wsi);
    }
    
}
