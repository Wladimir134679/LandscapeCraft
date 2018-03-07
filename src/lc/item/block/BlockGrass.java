package lc.item.block;

import com.badlogic.gdx.math.MathUtils;
import lc.game.GameScreen;
import lc.world.LayerBlock;
import lc.world.World;

/**
 * @author Death
 */
public class BlockGrass extends Block{

    public void grow(int x, int y, World world){
        if(MathUtils.random(0, 100) < 2){
            int len = 1;
            if(MathUtils.random(0, 100) < 5)
                len = 2;
            if(!growBlock(x + len, y, world))
                if(!growBlock(x - len, y, world))
                    if(!growBlock(x + 1, y + 1, world))
                        if(!growBlock(x + 1, y - 1, world))
                            if(!growBlock(x - 1, y + 1, world))
                                growBlock(x - 1, y - 1, world);
        }
    }
    
    public void growPlantGrass(int x, int y, World world){
        if(world.ground.getBlock(x, y + 1) == null){
            if(MathUtils.random(0, 100) < 5){
                world.ground.setBlock(Block.get(getPlantGrass()), x, y + 1);
            }
        }
//            if(world.ground.getBlock(x, y + 1) != null){
//                if(world.ground.getBlock(x, y + 1).id != Block.get(getPlantGrass()).id){
//                    ((BlockPlantGrass)Block.get(getPlantGrass())).growGrass(world, x, y + 1);
//                }
//            }
    }
    
    public void update(int x, int y, World world){
        if(MathUtils.random(0, 100) < 5){
            if(y + 1 >= world.heightBlock) return;
            if(world.ground.getBlock(x, y + 1) != null && !world.ground.getBlock(x, y + 1).isMaterial("plant")){
                world.ground.setBlock(Block.get(getDirstBlocks()), x, y);
            }
        }
    }
    
    private boolean growBlock(int x, int y, World world){
        if(x < 0 || y < 0 || x >= world.widthBlock || y >= world.heightBlock) return false;
        if(world.ground.getBlock(x, y) == null) return false;
        if(world.ground.getBlock(x, y).nameKod.equals(getDirstBlocks())){
            if(world.ground.getBlock(x, y + 1) == null){
                world.ground.setBlock(this, x, y);
                return true;
            }
        }
        return false;
    }

    @Override
    public void crashed(LayerBlock w, int x, int y){
        y += 1;
        if(w.getBlock(x, y) != null){
            if(w.getBlock(x, y).isMaterial("plant")){
                Block.crashed(w.getBlock(x, y), GameScreen.GAME_SCREEN.world, x, y);
                w.setBlock(null, x, y);
            }
        }
    }
    
    public String getPlantGrass(){
        return propertyBlock.get("plantGrass");
    }
    
    public String getDirstBlocks(){
        return propertyBlock.get("dirstBlock");
    }
    
}
