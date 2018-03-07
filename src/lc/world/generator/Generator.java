package lc.world.generator;

import java.util.Random;
import lc.item.block.Block;
import lc.world.World;

/**
 * @author Death
 */
public abstract class Generator {

    public static String PROCESS_INFA = "Создание мира...";
    
    protected long seed;
    protected Random rnd;
    protected World world;
    
    public Generator(long seed, World world){
        this.world = world;
        this.seed = seed;
        rnd = new Random(seed);
    }
    
    public abstract void gen();
    
    public boolean getBoolean(){
        return rnd.nextBoolean();
    }
    
    public int rndInt(int min, int max){
        return rnd.nextInt(max - min) + min;
    }
    
    public void genOre(int x, int y, Block block){
        int t = this.rndInt(1, 5);
        switch(t){
            case 1: genOreCross(x, y, block); break;
            case 2: genOreCross2(x, y, block); break;
            case 3: genOreCross3(x, y, block); break;
            case 4: genOreRect(x, y, block); break;
            case 5: genOreType1(x, y, block); break;
            default:
                genOreCross(x, y, block);
        }
    }
    
    private void genOreCross(int x, int y, Block b){
        if(world.ground.getBlock(x, y) != null)
            world.ground.setBlock(b, x, y);
        if(world.ground.getBlock(x+1, y) != null)
            world.ground.setBlock(b, x+1, y);
        if(world.ground.getBlock(x-1, y) != null)
            world.ground.setBlock(b, x-1, y);
        if(world.ground.getBlock(x, y+1) != null)
            world.ground.setBlock(b, x, y+1);
        if(world.ground.getBlock(x, y-1) != null)
            world.ground.setBlock(b, x, y-1);
    }
    
    private void genOreCross2(int x, int y, Block b){
        genOreCross(x, y, b);
        genOreCross(x + 1, y + 1, b);
    }
    
    private void genOreCross3(int x, int y, Block b){
        genOreCross(x, y, b);
        genOreCross(x + 1, y - 1, b);
    }
    
    private void genOreRect(int x, int y, Block b){
        int w = this.rndInt(1, 4);
        int h = this.rndInt(1, 4);
        for(int i = 0; i < w; i ++)
            for(int j = 0; j < h; j++)
                if(world.ground.getBlock(x + i, y + j) != null)
                    world.ground.setBlock(b, x + i, y + j);
    }
    
    private void genOreType1(int x, int y, Block b){
        int dx = rnd.nextInt(2) - 1;
        int dy = rnd.nextInt(2) - 1;
        dx = dx == 0 && dy == 0 ? 1 : 0;
        if(world.ground.getBlock(x, y) != null)
            world.ground.setBlock(b, x, y);
        if(world.ground.getBlock(x + dx, y + dy) != null)
            world.ground.setBlock(b, x + dx, y + dy);
    }
}
