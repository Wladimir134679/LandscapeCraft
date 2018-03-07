package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.SettingApp;
import lc.block.managers.ManagerBlocks;
import lc.inventory.InventoryParent;
import lc.item.DataAll;
import lc.player.Player;
import lc.world.WorldLoad;

/**
 * @author Death
 */
public class LoadGame {
    
    public static final void load(GameScreen game, GameInfa infa){
        load(game, Gdx.files.local(SettingApp.DIRICTORY_WORLD + infa.dirPath));
    }

    public static final void load(GameScreen game, FileHandle dirPath){
        game.gameInfa.dirPath = dirPath.name();
        if(dirPath.isDirectory()){
            try{
                DataInputStream inMap = new DataInputStream(new FileInputStream(dirPath + "/world.bin"));
                game.world = WorldLoad.load(game, inMap);
                
                DataInputStream gameInfa = new DataInputStream(new FileInputStream(dirPath + "/game.bin"));
                game.gameInfa.load(gameInfa);
                
                DataInputStream inP = new DataInputStream(new FileInputStream(dirPath + "/player.bin"));
                
                DataInputStream inChest = new DataInputStream(new FileInputStream(dirPath + "/chests.bin"));
                
                DataInputStream inPropertyBlock = new DataInputStream(new FileInputStream(dirPath + "/propertyBlock.bin"));
                
                LoadGame.loadPlayer(game, inP);
                LoadGame.loadInventory(game, inP);
                ManagerBlocks.load(game, inChest);
                
                int lenght = inPropertyBlock.readInt();
                byte[] data = new byte[lenght];
                inPropertyBlock.read(data);
                DataAll.get().parseJsonPropertBlocks(data);
                
                inMap.close();
                gameInfa.close();
                inP.close();
                inChest.close();
                inPropertyBlock.close();
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
    
    private static void loadPlayer(GameScreen game, DataInputStream in) throws IOException{
        Player pl = new Player(game);
        pl.healthMax = in.readInt();
        pl.health = in.readInt();
        pl.x = in.readFloat();
        pl.y = in.readFloat();
        game.player = pl;
        game.world.add(pl);
    }
    
    private static void loadInventory(GameScreen game, DataInputStream in) throws IOException{
        InventoryParent.load(game.inventory.bar, in);
        InventoryParent.load(game.inventory.pocket, in);
    }
}
