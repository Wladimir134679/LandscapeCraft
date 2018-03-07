package lc.utils;

import static java.lang.Thread.MIN_PRIORITY;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.game.GameScreen;
import lc.world.threads.GrowGrass;

/**
 * @author Death
 */
public abstract class ThreadDaemon extends Thread{


    protected GameScreen game;
    protected float sec;

    public ThreadDaemon(GameScreen game){
        this.game = game;
        this.setPriority(MIN_PRIORITY);
        this.setDaemon(true);
        
        sec = 1;
    }

    @Override
    public abstract void run();
    
    public void sleepT(int sleep){
        try{
            this.sleep(sleep);
        }
        catch(InterruptedException ex){
            Logger.getLogger(GrowGrass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sleepThread(int d){
        try{
            this.sleep((int)(sec * 1000f) / d);
        }
        catch(InterruptedException ex){
            Logger.getLogger(GrowGrass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sleepThread(){
        this.sleepThread(1);
    }
}
