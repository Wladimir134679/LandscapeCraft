package lc.world.threads;

import lc.game.GameScreen;
import lc.item.block.BlockSeedling;
import lc.item.block.BlocksFinder;

/**
 * @author Death
 */
public class GrowSeedling extends ThreadGrow{
    
    public GrowSeedling(GameScreen game){
        super(game);
    }
    
    @Override
    public void run(){
        int xP, yP;
        while(GameScreen.IS_GAME_LOOP){
            this.sleepThread();
            xP = Math.round(game.player.getCenterXId());
            yP = Math.round(game.player.getCenterYId());
            BlocksFinder.getFinder(game.world, xP, yP, radius)
                        .filter((block, x, y) -> {return block instanceof BlockSeedling;})
                        .apply((block, x, y) -> {((BlockSeedling)block).grow(x, y, game.world);})
                        .sleep(() -> {GrowSeedling.this.sleepT(200);})
                        .find();
            
        }
    }
}
