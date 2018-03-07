package lc.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lc.item.block.Block;

/**
 * @author Death
 */
public class Ground extends LayerBlock{

    public Ground(World world){
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
        sp.setSize(Block.SIZE, Block.SIZE);
        sp.setColor(1, 1, 1, 1);
        sp.setOriginCenter();
        sp.draw(b);
    }
}
