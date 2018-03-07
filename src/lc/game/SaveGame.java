package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.block.managers.ManagerBlocks;
import lc.inventory.InventoryParent;
import lc.item.DataAll;
import lc.player.Player;
import lc.world.WorldSave;

/**
 * @author Death
 */
public class SaveGame {

    public static final void save(GameScreen game){
        FileHandle dirPath = game.gameInfa.getDirPath();
        if(dirPath.isDirectory()){
            try{
                DataOutputStream outMap = new DataOutputStream(new FileOutputStream(dirPath + "/world.bin"));
                WorldSave.save(game.world, outMap);
                
                DataOutputStream gameInfa = new DataOutputStream(new FileOutputStream(dirPath + "/game.bin"));
                game.gameInfa.save(gameInfa);
                
                DataOutputStream outP = new DataOutputStream(new FileOutputStream(dirPath + "/player.bin"));
                
                DataOutputStream outChest = new DataOutputStream(new FileOutputStream(dirPath + "/chests.bin"));
                
                DataOutputStream outPropertyBlock = new DataOutputStream(new FileOutputStream(dirPath + "/propertyBlock.bin"));
                
                SaveGame.savePlayer(game, outP);
                SaveGame.saveInventory(game, outP);
                ManagerBlocks.save(game, outChest);
                
                byte[] data = DataAll.get().getJsonPropertyBlocks();
                outPropertyBlock.writeInt(data.length);
                outPropertyBlock.write(data);
                
                outMap.close();
                gameInfa.close();
                outP.close();
                outChest.close();
                outPropertyBlock.close();
            }
            catch(FileNotFoundException ex){
                Logger.getLogger(SaveGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch(IOException ex){
                Logger.getLogger(SaveGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Gdx.app.error("Error / SaveGame", dirPath.name() + " no directory");
        }
    }
    
    private static void savePlayer(GameScreen game, DataOutputStream out) throws IOException{
        Player p = game.player;
        out.writeInt(p.healthMax);
        out.writeInt(p.health);
        out.writeFloat(p.x);
        out.writeFloat(p.y);
    }
    
    private static void saveInventory(GameScreen game, DataOutputStream out) throws IOException{
        InventoryParent.save(game.inventory.bar, out);
        InventoryParent.save(game.inventory.pocket, out);
    }
}
