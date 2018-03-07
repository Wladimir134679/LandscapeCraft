package lc.world.generator;

import com.badlogic.gdx.math.Vector2;
import lc.item.block.Block;
import lc.utils.NoisePerlin;
import lc.world.World;
import lc.world.generator.structure.StructureTree;

/**
 * @author Death
 */
public class GeneratorNormal extends Generator{

    private int[] heightBlocks;
    private int maxHeightBlocks;
    private int step;
    
    public GeneratorNormal(long seed, World world, int maxHeightBlocks){
        this(seed, world, maxHeightBlocks, 0);
    }
    
    public GeneratorNormal(long seed, World world, int maxHeightBlocks, int step){
        super(seed, world);
        PROCESS_INFA = "Создание мира";
        heightBlocks = new int[world.widthBlock];
        this.maxHeightBlocks = maxHeightBlocks;
        if(step <= 0){
            this.step = this.rndInt(2, 10);
        }else{
            this.step = step;
        }
    }

    @Override
    public void gen(){
        PROCESS_INFA = "Генерация ладшафта";
        this.generateLandscape();
        
        PROCESS_INFA = "Заполнение блоками";
        int widthBetTree = 0;
        for(int x = 0 ; x < world.widthBlock; x++){
            world.ground.setBlock(Block.get("grass"), x, heightBlocks[x]);
            if(x > 10 && x < world.widthBlock - 10 && rnd.nextInt(100) < 10){
                if(x > widthBetTree + 3){
                    StructureTree.generate(world, x, heightBlocks[x] + 1, Block.get("tree_oak"), Block.get("foliage_oak"), 5);
                }
                widthBetTree = x;
            }
            if(x > 10 && x < world.widthBlock - 10 && rnd.nextInt(100) < 40)
                world.ground.setNullBlock(Block.get("plant_grass"), x, heightBlocks[x] + 1);
            for(int y = heightBlocks[x] - 3; y < heightBlocks[x]; y++){
                world.ground.setBlock(Block.get("dirst"), x, y);
            }
            for(int y = 0; y < heightBlocks[x] - 3; y++){
                world.ground.setBlock(Block.get("stone"), x, y);
            }
        }
        
        this.spawnCreat();
        this.generateCave();
        this.generateBackground();
        this.generateOre();
    }
    
    public void spawnCreat(){
        PROCESS_INFA = "Создание спавна";
        world.spawnsObject.put("playerObject", new Vector2(world.widthBlock / 2, heightBlocks[world.widthBlock / 2] + 2));
    }
    
    private void generateBackground(){
        PROCESS_INFA = "Генерация фона";
        for(int i = 0; i < world.widthBlock; i++){
            for(int y = heightBlocks[i] - 3; y <= heightBlocks[i]; y++){
                world.backLayerBlock.setBlock(Block.get("dirst"), i, y);
            }
            for(int y = 0; y < heightBlocks[i] - 3; y++){
                world.backLayerBlock.setBlock(Block.get("stone"), i, y);
            }
        }
    }
    
    private void generateLandscape(){
        heightBlocks[0] = maxHeightBlocks;
        for(int i = 1; i < world.widthBlock; i++){
            heightBlocks[i] = heightBlocks[i - 1] + (rnd.nextInt(step) - step / 2);
            if(heightBlocks[i] > world.heightBlock - 20) heightBlocks[i] = world.heightBlock - 40;
            if(heightBlocks[i] < 250){
                heightBlocks[i] = 270;
                heightBlocks[i - 1] = 270;
            }
        }
        int inter = rnd.nextInt(6) + 3;
        for(int i = 0 ; i < inter; i++){
            for(int x = 1 ; x < world.widthBlock - 1; x++){
                heightBlocks[x] = (heightBlocks[x - 1] + heightBlocks[x + 1]) / 2;
            }
        }
    }
    private void generateCave(){
        PROCESS_INFA = "Генерация пешер";
        float[][] pix = NoisePerlin.getNoise(world.widthBlock, world.heightBlock, this.rnd.nextFloat() * 5f + 1f);
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < heightBlocks[i] - 20; j++){
                if(pix[i][j] > 0.3f){
                    world.ground.setBlock(null, i, j);
                }
            }
        }
    }
    
    private void generateOre(){
        PROCESS_INFA = "Генерация руд";
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < heightBlocks[i]; j++){
                if(this.rndInt(0, 1000) < 15){
                    this.genOre(i, j, Block.get("dirst"));
                }
                if(this.rndInt(0, 1000) < 5){
                    this.genOre(i, j, Block.get("iron_ore"));
                    if(this.rndInt(0, 100) < 10)
                        this.genOre(i+1, j+1, Block.get("iron_ore"));
                }
            }
        }
    }
}
