package lc.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lc.item.block.Block;

/**
 * @author Death
 */
public class BackgroundLayer extends LayerBlock{

    public BackgroundLayer(World world){
        super(world);
        this.blocks = new Block[world.widthBlock][world.heightBlock];
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < world.heightBlock; j++){
                blocks[i][j] = null;
            }
        }
    }

    @Override
    public void drawBlock(float x, float y, Block block, SpriteBatch b, Sprite sp){
        if(sp == null) return;
        sp.setPosition(x, y);
        sp.setColor(0.5f, 0.5f, 0.5f, 1);
        sp.setSize(Block.SIZE, Block.SIZE);
        sp.setOriginCenter();
        sp.draw(b);
    }

    @Override
    public boolean isCrachBlock(int x, int y){
        if(world.ground.getBlock(x, y) != null)
            return false;
        if(getBlock(x, y) == null)
            return false;
        
        boolean crach = false;
        if(getBlock(x + 1, y) == null) crach = true;
        if(getBlock(x - 1, y) == null) crach = true;
        if(getBlock(x, y + 1) == null) crach = true;
        if(getBlock(x, y - 1) == null) crach = true;

        if(getBlock(x + 1, y + 1) == null) crach = true;
        if(getBlock(x + 1, y - 1) == null) crach = true;
        if(getBlock(x - 1, y + 1) == null) crach = true;
        if(getBlock(x - 1, y - 1) == null) crach = true;
        return crach;
    }

    @Override
    public boolean isBetBlock(int x, int y){
        if(world.ground.getBlock(x, y) != null)
            return false;
        if(getBlock(x, y) != null)
            return false;
        return true;
    }
}
