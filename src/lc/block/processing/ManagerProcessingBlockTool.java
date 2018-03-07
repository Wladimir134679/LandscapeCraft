package lc.block.processing;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.game.GameScreen;

/**
 * @author Death
 */
public class ManagerProcessingBlockTool implements Runnable{

    public Thread thread;
    private ArrayList<ProcessingBlock> process;
    
    public ManagerProcessingBlockTool(){
        thread = new Thread(this);
        thread.setDaemon(true);
        process = new ArrayList<>();
    }
    
    public void start(){
        if(GameScreen.IS_GAME_LOOP){
            thread.start();
        }
    }

    @Override
    public void run(){
        while(GameScreen.IS_GAME_LOOP){
            for(int i = 0; i < process.size(); i++){
                ProcessingBlock proc = process.get(i);
                if(proc.timer.isEnd()){
                    proc.end();
                    proc.run(this);
                    i--;
                }
            }
            sleep(1000 / 60);
        }
    }
    
    public void add(ProcessingBlock proc){
        this.process.add(proc);
    }
    
    public void remove(ProcessingBlock proc){
        this.process.remove(proc);
    }
    
    public void sleep(int i){
        try{
            thread.sleep(i);
        }
        catch(InterruptedException ex){
            Logger.getLogger(ManagerProcessingBlockTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
