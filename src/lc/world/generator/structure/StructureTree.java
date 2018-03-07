package lc.world.generator.structure;

import com.badlogic.gdx.math.MathUtils;
import lc.item.block.Block;
import lc.world.World;

/**
 * @author Death
 */
public class StructureTree {

    public final static void generate(World world, int x, int y, Block tree, Block foliage, int height){
        if(height <= 1) height = MathUtils.random(4, 6);
        for(int i = y; i < y + height; i++){
            world.ground.setBlock(tree, x, i);
        }
        y += height;
        world.ground.setNullBlock(foliage, x, y++);
        world.ground.setNullBlock(foliage, x, y++);
        world.ground.setNullBlock(foliage, x, y++);
        y -= 3;
        world.ground.setNullBlock(foliage, x++, y);
        world.ground.setNullBlock(foliage, x++, y);
        world.ground.setNullBlock(foliage, x++, y);
        x -= 3;
        world.ground.setNullBlock(foliage, x--, y);
        world.ground.setNullBlock(foliage, x--, y);
        world.ground.setNullBlock(foliage, x--, y);
        x += 3;
        y += 1;
        world.ground.setNullBlock(foliage, x+1, y);
        world.ground.setNullBlock(foliage, x-1, y);
        y -= 2;
        world.ground.setNullBlock(foliage, x+1, y);
        world.ground.setNullBlock(foliage, x-1, y);
    }
}
