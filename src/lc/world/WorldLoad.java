package lc.world;

import java.io.DataInputStream;
import java.io.IOException;
import lc.game.GameScreen;
import lc.item.DataAll;
import lc.item.StackItems;
import lc.item.block.Block;

/**
 * @author Death
 */
public class WorldLoad {

    public static final World load(GameScreen game, DataInputStream in) throws IOException{
        World world = new World(game, in.readInt(), in.readInt());
        
        int id;
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < world.heightBlock; j++){
                int num = in.readInt();
                id = in.readInt();
                
                if(id != -1){
                    Block block = DataAll.get().blocks.get(id);
                    if(block == null) continue;
                    for(int ni = j; ni < j + num + 1; ni++){
                        world.ground.setBlock(block, i, ni);
                    }
                }
                j += num;
            }
        }
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < world.heightBlock; j++){
                int num = in.readInt();
                id = in.readInt();
                
                if(id != -1){
                    Block block = DataAll.get().blocks.get(id);
                    if(block == null) continue;
                    for(int ni = j; ni < j + num + 1; ni++){
                        world.backLayerBlock.setBlock(block, i, ni);
                    }
                }
                j += num;
            }
        }
        WorldLoad.loadWorldStack(world, in);
        return world;
    }
    
    private static void loadWorldStack(World w, DataInputStream in) throws IOException{
        int num = in.readInt();
        for(int i = 0; i < num; i++){
            StackItems si = new StackItems(DataAll.get().items.get(in.readInt()));
            si.num = in.readInt();
            
            WorldStackItems wsi = new WorldStackItems(w, si);
            byte[] data = new byte[in.readInt()];
            in.read(data);
            wsi.setData(data);
            
            w.add(wsi);
        }
    }
}
