package lc.world.threads;

import com.badlogic.gdx.math.MathUtils;
import lc.game.GameScreen;
import lc.item.block.Block;
import lc.item.block.BlockFoliage;
import lc.item.block.BlocksFinder;
import lc.utils.ThreadDaemon;

/**
 * @author Death
 */
public class FallFoliage extends ThreadDaemon{

    public FallFoliage(GameScreen game){
        super(game);
    }

    @Override
    public void run(){
        this.sec = 2f;
        int xP, yP;
        while(GameScreen.IS_GAME_LOOP){
            xP = Math.round(game.player.getCenterXId());
            yP = Math.round(game.player.getCenterYId());
            BlocksFinder.getFinder(game.world, xP, yP, 500)
                        .filter((block, x, y) -> {return block instanceof BlockFoliage;})
                        .apply((block, x, y) -> {
                            if(MathUtils.random(0, 100) < 10){
                                if(!((BlockFoliage)block).isFindTree(game.world, x, y)){
                                    game.world.ground.setBlock(null, x, y);
                                    Block.crashed(block, game.world, x, y);
                                }
                            }
                        })
                        .sleep(() -> {FallFoliage.this.sleepT(50);})
                        .find();
            sleepThread();
        }
    }
}
