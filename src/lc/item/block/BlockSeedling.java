package lc.item.block;

import com.badlogic.gdx.math.MathUtils;
import lc.world.LayerBlock;
import lc.world.World;
import lc.world.generator.structure.StructureTree;

/**
 * @author Death
 */
public class BlockSeedling extends Block{

    
    public void grow(int x, int y, World world){
        if(MathUtils.random(0, 100) < 5){
            world.ground.setBlock(null, x, y);
            StructureTree.generate(world, x, y, Block.get(getNameTree()), Block.get(getNameFoliage()), 0);
        }
    }

    @Override
    public boolean isBet(LayerBlock w, int x, int y){
        return w.getBlock(x, y - 1) != null && w.getBlock(x + 1, y - 1) != null
               && (w.getBlock(x, y - 1) instanceof BlockGrass || w.getBlock(x + 1, y - 1).isMaterial("dirst"))
               && w.getBlock(x + 1, y) == null && w.getBlock(x - 1, y) == null
               && !(w.getBlock(x + 1, y) instanceof BlockSeedling)
               && !(w.getBlock(x - 1, y) instanceof BlockSeedling);
    }
    
    public String getNameTree(){
        return propertyBlock.get("nameTree");
    }
    
    public String getNameFoliage(){
        return propertyBlock.get("nameFoliage");
    }
}
