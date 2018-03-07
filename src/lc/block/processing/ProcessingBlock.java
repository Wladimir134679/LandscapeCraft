package lc.block.processing;

import lc.game.GameScreen;
import lc.item.block.Block;
import lc.utils.PropertyUtil;
import lc.utils.Timer;

/**
 * @author Death
 */
public abstract class ProcessingBlock {

    public Timer timer;
    public PropertyUtil prop;
    public Block block;

    public ProcessingBlock(){
        timer = new Timer();
    }
    
    public final void start(int time, Block b, PropertyUtil pro){
        timer.startMillis(time);
        this.start(timer, b, pro);
    }
    
    public final void start(Timer time, Block b, PropertyUtil pro){
        if(!time.isRun) return;
        this.timer = time;
        this.prop = pro;
        this.block = b;
        GameScreen.GAME_SCREEN.processingBlockTool.add(this);
    }

    public abstract void run(ManagerProcessingBlockTool manager);
    
    public final void end(){
        timer.stop();
        GameScreen.GAME_SCREEN.processingBlockTool.remove(this);
    }
    
    public boolean isRun(){
        return timer.isRun;
    }

}
