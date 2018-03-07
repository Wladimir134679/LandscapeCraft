package lc.utils;

/**
 * @author Death
 */
public class Timer {

    public long timeBegin, time;
    public boolean isRun;

    public Timer(){
        timeBegin = 0;
        isRun = false;
    }
    
    public void startMillis(int mil){
        this.time = mil;
        isRun = true;
        timeBegin = System.currentTimeMillis();
    }
    
    public void startSecund(int sec){
        this.startMillis(sec * 1000);
    }
    
    public boolean isEnd(){
        if(!isRun) return false;
        return System.currentTimeMillis() - timeBegin > time;
    }
    
    public long getDelta(){
        if(!isRun) return -1;
        return System.currentTimeMillis() - timeBegin;
    }
    
    public void stop(){
        isRun = false;
    }
    
    public float getRemainingPercent(){
        if(!isRun) return 0;
        return (getDelta() * 100) / time;
    }
}
