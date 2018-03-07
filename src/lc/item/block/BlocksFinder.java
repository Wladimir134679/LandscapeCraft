package lc.item.block;

import java.util.ArrayList;
import lc.world.World;

/**
 * @author Death
 */
public class BlocksFinder {

    public static interface ApplyBlock{
        public void apply(Block block, int x, int y);
    }
    
    public static interface ApplyFilter{
        public boolean filter(Block block, int x, int y);
    }
    
    public static interface SleepThread{
        public void sleep();
    }
    
    private static ArrayList<BlocksFinder> blockFinders = new ArrayList<>();
    
    public static BlocksFinder getFinder(World world, Block block, int x, int y, int rad){
        for(BlocksFinder b : blockFinders){
            if(!b.processing){
                b.setData(world, block, x, y, rad);
                return b;
            }
        }
        BlocksFinder finder = new BlocksFinder();
        finder.setData(world, block, x, y, rad);
        blockFinders.add(finder);
        return finder;
    }
    
    public static BlocksFinder getFinder(World world, int x, int y, int rad){
        return BlocksFinder.getFinder(world, null, x, y, rad);
    }
    
    private World world;
    private int x, y;
    private int radius;
    private ApplyBlock applyBlock = null;
    private ApplyFilter applyFilter = null;
    private boolean findBlock;
    private Block block = null;
    private boolean aloneBlock;
    int xE, xB, yE, yB;
    
    public SleepThread sleepThread = null;
    public boolean processing;
    
    private void setData(World world, Block block, int x, int y, int rad){
        processing = true;
        sleepThread = null;
        aloneBlock = false;
        this.world = world;
        this.block = block;
        this.x = x;
        this.y = y;
        this.radius = rad;
        findBlock = false;
    }
    
    public BlocksFinder find(){
        xB = x - radius;
        yB = y - radius;
        xE = x + radius;
        yE = y + radius;
        if(xB < 0) xB = 0;
        if(yB < 0) yB = 0;
        if(xE >= world.widthBlock) xE = world.widthBlock - 1;
        if(yE >= world.heightBlock) yE = world.heightBlock - 1;
        for(int i = xB; i < xE; i++){
            for(int j = yB; j < yE; j++){
                findBlock = false;
                Block blockW = world.ground.getBlock(i, j);
                if(blockW == null) continue;
                if(applyFilter != null && applyFilter.filter(blockW, x, y)){
                    this.applyBlock.apply(blockW, i, j);
                    findBlock = true;
                }else if(block != null && blockW.equals(block)){
                    this.applyBlock.apply(blockW, i, j);
                    findBlock = true;
                }
                
                if(findBlock){
                    findBlock = false;
                    if(sleepThread != null){
                        sleepThread.sleep();
                    }
                    if(aloneBlock){
                        processing = false;
                        return this;
                    }
                }
            }
        }
        processing = false;
        return this;
    }
    
    public BlocksFinder filter(ApplyFilter fil){
        this.applyFilter = fil;
        return this;
    }
    
    public BlocksFinder apply(ApplyBlock app){
        this.applyBlock = app;
        return this;
    }
    
    public BlocksFinder sleep(SleepThread s){
        this.sleepThread = s;
        return this;
    }
    
    public BlocksFinder setAloneBlock(boolean s){
        this.aloneBlock = s;
        return this;
    }
}
