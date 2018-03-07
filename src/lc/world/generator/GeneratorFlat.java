package lc.world.generator;

import lc.item.block.Block;
import lc.world.World;
import lc.world.generator.structure.StructureTree;

/**
 * @author Death
 */
public class GeneratorFlat extends Generator{

    public int heightDirst, height;
    
    public GeneratorFlat(World world, int height, int heightDirst){
        super(0, world);
        this.height = height;
        this.heightDirst = heightDirst;
    }
    
    @Override
    public void gen(){
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = height - heightDirst; j < height; j++){
                world.ground.blocks[i][j] = Block.get("dirst");
            }
        }
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < height - heightDirst; j++){
                world.ground.blocks[i][j] = Block.get("stone");
            }
        }
        for(int i = 0; i < world.widthBlock; i++){
            world.ground.blocks[i][height - 1] = Block.get("grass");
            if(this.getBoolean()) world.ground.blocks[i][height] = Block.get("plant_grass");
        }
        
        int xTree = 0;
        int numTree = world.widthBlock / 7 - 1;
        for(int i = 0; i < numTree; i++)
            StructureTree.generate(world, xTree += 7, height, Block.get("tree_oak"), Block.get("foliage_oak"), 5);
    }

}
