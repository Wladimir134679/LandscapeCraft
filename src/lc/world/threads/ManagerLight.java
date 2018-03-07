package lc.world.threads;

import lc.game.GameScreen;
import lc.item.block.Block;
import lc.item.block.TypeBlock;
import lc.utils.ThreadDaemon;
import lc.world.BackgroundLayer;
import lc.world.Ground;
import lc.world.LayerLight;

/**
 * @author Death
 */
public class ManagerLight extends ThreadDaemon{
    
    Ground ground;
    BackgroundLayer back;
    LayerLight light;
    
    public ManagerLight(GameScreen game){
        super(game);
    }

    @Override
    public void run(){
        light = game.world.layerLight;
        ground = game.world.ground;
        back = game.world.backLayerBlock;
        int xP, yP;
        int xE, xB, yE, yB;
        int radius = 50;
        while(GameScreen.IS_GAME_LOOP){
            xP = Math.round(game.player.getCenterXId());
            yP = Math.round(game.player.getCenterYId());
            
            xB = xP - radius;
            yB = yP - radius;
            xE = xP + radius;
            yE = yP + radius;
            if(xB < 0) xB = 0;
            if(yB < 0) yB = 0;
            if(xE >= game.world.widthBlock) xE = game.world.widthBlock - 1;
            if(yE >= game.world.heightBlock) yE = game.world.heightBlock - 1;
            light.clearClon(xB, yB, xE - xB, yE - yB);
            
//            game.world.lightWorld = 1f;
            
            if(game.world.lightWorld > 1){
                game.world.lightWorld = 1f;
            }
            if(game.world.lightWorld < 0){
                game.world.lightWorld = 0f;
            }
            for(int j = yB; j < yE; j++){
                for(int i = xB; i < xE; i++){
                    if(ground.getBlock(i, j) == null && back.getBlock(i, j) == null){
                        light.getC(i, j).light = game.world.lightWorld;
                    }else{
                        light.getC(i, j).light = 0;
                    }
                }
            }
            for(int j = yB; j < yE; j++){
                for(int i = xB; i < xE; i++){
                    Block block = ground.getBlock(i, j);
                    if(block == null){
                        if(this.isNotBlock(i, j)){
                            light.getC(i, j).light = game.world.lightWorld;
                            lightUpdate(i + 1, j);
                            lightUpdate(i - 1, j);
                            lightUpdate(i, j + 1);
                            lightUpdate(i, j - 1);
                        }
                    }
                }
            }
            for(int j = yB; j < yE; j++){
                for(int i = xB; i < xE; i++){
                    Block block = ground.getBlock(i, j);
                    if(block != null){
                        if(block.getMaterial(0) != null && block.getMaterial(0).property != null && block.getMaterial(0).property.get("layerLight") != null){
                            if(block.getMaterial(0).property.getInt("layerLight") > 0){
                                light.getC(i, j).light = 2f;
                                lightUpdate(i + 1, j);
                                lightUpdate(i - 1, j);
                                lightUpdate(i, j + 1);
                                lightUpdate(i, j - 1);
                            }
                        }
                    }
                }
            }
            
            for(int j = yB; j < yE; j++){
                for(int i = xB; i < xE; i++){
                    if(light.getC(i, j).light < 0) light.getC(i, j).light = 0;
                    if(light.getC(i, j).light > 1) light.getC(i, j).light = 1;
                }
            }
            light.cloneLight();
            sleepT(1000 / 30);
        }
    }
    
    public boolean isBlock(int x, int y){
        return ground.getBlock(x, y) != null || back.getBlock(x, y) != null;
    }
    
    public boolean isNotBlock(int x, int y){
        return ground.getBlock(x, y) == null && back.getBlock(x, y) == null;
    }
    
    private float getLP(int x, int y){
        if(light.getC(x, y) == null) return -1;
        return light.getC(x, y).light;
    }
    
    private void setLP(int x, int y, float va){
        if(this.light.getC(x, y) == null) return;
        this.light.getC(x, y).light = va;
    }
    
    public float getABSORT(int x, int y){
        if(x < 0 || x >= game.world.widthBlock || 
           x < 0 || y >= game.world.heightBlock)
            return 100f;
        if(this.ground.getBlock(x, y) != null){
            if(this.ground.getBlock(x, y).type == TypeBlock.SOLID)
                return 0.3f;
            else if(this.ground.getBlock(x, y).type == TypeBlock.PASSABLE)
                return 0.05f;
        }
        if(this.back.getBlock(x, y) != null) return 0.125f;
        return 0.1f;
    }
    
    public float getLight(int x, int y){
        float ABSORT = getABSORT(x, y);
        float f1 = Math.max(Math.max(getLP(x-1, y-1), getLP(x + 1, y - 1)), Math.max(getLP(x + 1, y + 1), getLP(x - 1, y + 1)));
        float f2 = Math.max(Math.max(getLP(x, y - 1), getLP(x + 1, y)), Math.max(getLP(x, y + 1), getLP(x - 1, y)));
        if(f2 >= f1){
            f1 = f2 - ABSORT;
        }else{
            f1 = f1 - ABSORT * 1.5f;
        }
        return f1;
    }
    
    private void lightUpdate(int x, int y){
        float lightL = getLight(x, y);
        if(lightL < 0) return;
        if(lightL <= getLP(x, y)) return;
        setLP(x, y, getLight(x, y));
        lightUpdate(x - 1, y);
        lightUpdate(x + 1, y);
        lightUpdate(x, y - 1);
        lightUpdate(x, y + 1);
    }
    
}
