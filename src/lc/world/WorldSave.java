package lc.world;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.world.objectes.WorldObject;

/**
 * @author Death
 */
public class WorldSave {

    public static final void save(World world, DataOutputStream out) throws IOException{
        out.writeInt(world.widthBlock);
        out.writeInt(world.heightBlock);
        
        int num;
        
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < world.heightBlock; j++){
                if(world.ground.getBlock(i, j) != null){
                    num = world.ground.numBlocks(i, j);
                    j += num;
                    out.writeInt(num);
                    out.writeInt(world.ground.getBlock(i, j).id);
                }else{
                    num = world.ground.numNull(i, j);
                    j += num;
                    out.writeInt(num);
                    out.writeInt(-1);
                }
            }
        }
        for(int i = 0; i < world.widthBlock; i++){
            for(int j = 0; j < world.heightBlock; j++){
                if(world.backLayerBlock.getBlock(i, j) != null){
                    num = world.backLayerBlock.numBlocks(i, j);
                    j += num;
                    out.writeInt(num);
                    out.writeInt(world.backLayerBlock.getBlock(i, j).id);
                }else{
                    num = world.backLayerBlock.numNull(i, j);
                    j += num;
                    out.writeInt(num);
                    out.writeInt(-1);
                }
            }
        }
        WorldSave.saveWorldStack(world, out);
    }
    
    private static void saveWorldStack(World w, DataOutputStream out) throws IOException{
        ArrayList<WorldObject> objs = w.getObjectes(WorldStackItems.GROUP);
        out.writeInt(objs.size());
        objs.stream().forEach(a -> {
            try{
                WorldStackItems wsi = (WorldStackItems) a;
                out.writeInt(wsi.si.item.id);
                out.writeInt(wsi.si.num);
                
                byte[] data = a.getData();
                out.writeInt(data.length);
                out.write(data);
            }
            catch(IOException ex){
                Logger.getLogger(WorldSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
