package lc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.menu.ScreenSelectWorld;

/**
 * @author Death
 */
public class GameInfa {
    public static final String[] WORLD_SIZE = {"Маленький", "Нормальный", "Большой"};

    public String nameWorld;
    public String dirPath;
    public int width, height;
    public long seed;
    public String dateCreateWorld;
    
    public FileHandle getDirPath(){
        FileHandle dir = Gdx.files.local("./worlds/" + dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }
    
    public void load(FileHandle dirW){
        FileHandle file = Gdx.files.local(dirW.path() + "/" + "game.bin");
        try{
            DataInputStream in = new DataInputStream(new FileInputStream(file.file()));
            load(in);
            in.close();
        }
        catch(IOException ex){
            Logger.getLogger(GameInfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void load(DataInputStream in) throws IOException{
        nameWorld = in.readUTF();
        width = in.readInt();
        height = in.readInt();
        seed = in.readLong();
        dateCreateWorld = in.readUTF();
    }
    
    public void save(FileHandle dirW){
        FileHandle file = Gdx.files.local(dirW.path() + "/" + "game.bin");
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file.file()));
            save(out);
            out.close();
        }
        catch(IOException ex){
            Logger.getLogger(GameInfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(DataOutputStream out) throws IOException{
        out.writeUTF(nameWorld);
        out.writeInt(width);
        out.writeInt(height);
        out.writeLong(seed);
        out.writeUTF(dateCreateWorld);
    }
    
    public String getSize(){
        return getSize(WORLD_SIZE);
    }
    
    public String getSize(String[] sizes){
        if(width * height >= 1500 * 900){
            return sizes[2];
        }else if(width * height >= 900 * 700){
            return sizes[1];
        }else{
            return sizes[0];
        }
    }

    @Override
    public String toString(){
        return nameWorld;
    }
}
