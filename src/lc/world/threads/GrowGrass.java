package lc.world.threads;

import lc.game.GameScreen;
import lc.item.block.Block;
import lc.item.block.BlockGrass;
import lc.item.block.BlocksFinder;

/**
 * @author Death
 */
public class GrowGrass extends ThreadGrow{

    public GrowGrass(GameScreen game){
        super(game);
    }
    
    @Override
    public void run(){
        int xP, yP;
        while(GameScreen.IS_GAME_LOOP){
            this.sleepThread(3);
            xP = Math.round(game.player.getCenterXId());
            yP = Math.round(game.player.getCenterYId());
            BlocksFinder.getFinder(game.world, xP, yP, radius)
                        .filter((block, x, y) -> {return block instanceof BlockGrass;})
                        .apply((block, x, y) -> {((BlockGrass)block).grow(x, y, game.world);})
                        .sleep(() -> {GrowGrass.this.sleepT(50);})
                        .find();
            this.sleepThread(3);
            xP = Math.round((game.player.x + game.player.width / 2) / Block.SIZE);
            yP = Math.round((game.player.y + game.player.height / 2) / Block.SIZE);
            BlocksFinder.getFinder(game.world, xP, yP, radius)
                        .filter((block, x, y) -> {return block instanceof BlockGrass;})
                        .apply((block, x, y) -> {((BlockGrass)block).update(x, y, game.world);})
                        .sleep(() -> {GrowGrass.this.sleepT(50);})
                        .find();
            this.sleepThread(3);
            xP = Math.round((game.player.x + game.player.width / 2) / Block.SIZE);
            yP = Math.round((game.player.y + game.player.height / 2) / Block.SIZE);
            BlocksFinder.getFinder(game.world, xP, yP, radius)
                        .filter((block, x, y) -> {return block instanceof BlockGrass;})
                        .apply((block, x, y) -> {((BlockGrass)block).growPlantGrass(x, y, game.world);})
                        .sleep(() -> {GrowGrass.this.sleepT(50);})
                        .find();
        }
    }
}
