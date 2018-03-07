package lc.item.block;

import com.badlogic.gdx.math.MathUtils;
import lc.world.LayerBlock;
import lc.world.World;

/**
 * @author Death
 */
public class BlockPlantGrass extends Block{

    @Override
    public int getNumItem(){
        if(MathUtils.random(0, 100) < 20)
            return 1;
        return 0;
    }

    @Override
    public boolean isBet(LayerBlock w, int x, int y){
        return w.getBlock(x, y - 1) != null &&
               (w.getBlock(x, y - 1) instanceof BlockGrass || w.getBlock(x + 1, y - 1).isMaterial("dirst"));
    }
}
