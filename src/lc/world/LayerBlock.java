package lc.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lc.item.block.Block;
import lc.utils.WCamera;

/**
 * @author Death
 */
public abstract class LayerBlock {

    public Block[][] blocks;
    public World world;

    public LayerBlock(World world){
        this.world = world;
    }
    
    public void draw(WCamera cam, SpriteBatch b){
        int xi = (int) (cam.getX() / Block.SIZE) - 2;
        int yi = (int) (cam.getY() / Block.SIZE) - 2;
        int wi = (int) (cam.getWidth() / Block.SIZE) + 5;
        int hi = (int) (cam.getHeight() / Block.SIZE) + 5;
        if(xi < 0) xi = 0;
        if(yi < 0) yi = 0;
        if(xi + wi > world.widthBlock) wi = world.widthBlock - xi;
        if(yi + hi > world.heightBlock) hi = world.heightBlock - yi;
        
        for(int i = xi; i < xi + wi; i++){
            for(int j = yi; j < yi + hi; j++){
                if(blocks[i][j] == null) continue;
                Block block = blocks[i][j];
                Sprite sp = block.getSprite(i, j);
                this.drawBlock(Block.SIZE * i, Block.SIZE * j, block, b, sp);
            }
        }
    }
    
    public abstract void drawBlock(float x, float y, Block block, SpriteBatch b, Sprite sp);
 
    public int numBlocks(int x, int y){
        int num = 0;
        int id = getBlock(x, y).id;
        for(int i = y+1; i < world.heightBlock; i++){
            if(getBlock(x, i) != null &&
               id == getBlock(x, i).id){
                num++;
            }else
                return num;
        }
        return num;
    }
    
    public int numNull(int x, int y){
        int num = 0;
        for(int i = y+1; i < world.heightBlock; i++){
            if(getBlock(x, i) == null)
                num++;
            else
                return num;
        }
        return num;
    }
    
    public Block getBlock(int x, int y){
        if(x < 0 || x >= world.widthBlock || y < 0 || y >= world.heightBlock) return null;
        return blocks[x][y];
    }
    
    public void setBlock(Block b, int x, int y){
        if(x < 0 || x >= world.widthBlock || y < 0 || y >= world.heightBlock) return;
        blocks[x][y] = b;
    }
    
    public void setNullBlock(Block b, int x, int y){
        if(x < 0 || x >= world.widthBlock || y < 0 || y >= world.heightBlock) return;
        if(blocks[x][y] == null) this.setBlock(b, x, y);
    }
    
    public boolean isCrachBlock(int x, int y){
        return getBlock(x, y) != null;
    }
    
    public boolean isBetBlock(int x, int y){
        return getBlock(x, y) == null;
    }
}
