package lc.world.threads;

import static java.lang.Thread.MIN_PRIORITY;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.game.GameScreen;
import lc.utils.ThreadDaemon;

/**
 * @author Death
 */
public abstract class ThreadGrow extends ThreadDaemon{

    protected int radius;

    public ThreadGrow(GameScreen game){
        super(game);
    }
    
    public void start(int sec, int radius){
        this.sec = sec;
        this.radius = radius;
        this.start();
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
}
