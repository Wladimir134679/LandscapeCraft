package lc.item.block;

import com.badlogic.gdx.math.MathUtils;
import lc.world.World;

/**
 * @author Death
 */
public class BlockFoliage extends Block{

    private boolean flagFindTreeReturn;
    
    @Override
    public int getNumItem(){
        if(MathUtils.random(0, 100) < 20)
            return 1;
        return 0;
    }
    
    public boolean isFindTree(World world, int x1, int y1){
        flagFindTreeReturn = false;
        BlocksFinder.getFinder(world, x1, y1, 5)
                .filter((block, x, y) -> {
                    return block.isMaterial("tree"); //To change body of generated lambdas, choose Tools | Templates.
                })
                .setAloneBlock(true)
                .apply((block, x, y) -> {
                    flagFindTreeReturn = true;
                })
                .find();
        return flagFindTreeReturn;
    }
}
