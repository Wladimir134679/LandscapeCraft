package lc.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lc.Asset;
import lc.game.GameScreen;
import lc.item.block.Block;
import lc.utils.WCamera;

/**
 * @author Death
 */
public class LayerLight {

    public GameScreen game;
    public Sprite sp;
    public CellLight[][] cells;
    public CellLight[][] cellsClon;
    public World world;

    public LayerLight(GameScreen game, World world){
        this.game = game;
        sp = new Sprite(Asset.whitePixel);
        this.world = world;
        cells = new CellLight[world.widthBlock][world.heightBlock];
        cellsClon = new CellLight[world.widthBlock][world.heightBlock];
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                cells[i][j] = new CellLight();
                cellsClon[i][j] = new CellLight();
            }
        }
        this.clear();
    }
    
    public void draw(WCamera cam, SpriteBatch b){
        int xi = (int) (cam.getX() / Block.SIZE) - 2;
        int yi = (int) (cam.getY() / Block.SIZE) - 2;
        int wi = (int) (cam.getWidth() / Block.SIZE) + 5;
        int hi = (int) (cam.getHeight() / Block.SIZE) + 5;
        if(xi < 0) xi = 0;
        if(yi < 0) yi = 0;
        if(xi + wi > world.widthBlock) wi = world.widthBlock - xi;
        if(yi + hi > world.heightBlock) hi = world.heightBlock - yi;
        
        for(int i = xi; i < xi + wi; i++){
            for(int j = yi; j < yi + hi; j++){
                cells[i][j].draw(sp, b, i, j);
            }
        }
    }
    
    public void clear(){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                cells[i][j].light = 0.0f;
                cellsClon[i][j].rC = 0f;
                cellsClon[i][j].gC = 0f;
                cellsClon[i][j].bC = 0f;
            }
        }
    }
    
    public void clearClon(int x, int y, int w, int h){
        for(int i = x; i < x + w; i++){
            for(int j = y; j < y + h; j++){
                cellsClon[i][j].light = 0.0f;
                cellsClon[i][j].rC = 0f;
                cellsClon[i][j].gC = 0f;
                cellsClon[i][j].bC = 0f;
            }
        }
    }
    
    public void setAll(float f){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                cells[i][j].light = f;
            }
        }
    }
    
    public void setAllClon(float f){
        for(int i = 0; i < cellsClon.length; i++){
            for(int j = 0; j < cellsClon[i].length; j++){
                cellsClon[i][j].light = f;
            }
        }
    }
    
    public void cloneLight(){
        for(int i = 0; i < cellsClon.length; i++){
            for(int j = 0; j < cellsClon[i].length; j++){
                cells[i][j].light = cellsClon[i][j].light;
                cells[i][j].rC = cellsClon[i][j].rC;
                cells[i][j].gC = cellsClon[i][j].gC;
                cells[i][j].bC = cellsClon[i][j].bC;
            }
        }
    }
    
    public CellLight get(int x, int y){
        if(x < 0 || y < 0 || x >= world.widthBlock || y >= world.heightBlock)
            return null;
        return cells[x][y];
    }
    
    public CellLight getC(int x, int y){
        if(x < 0 || y < 0 || x >= world.widthBlock || y >= world.heightBlock)
            return null;
        return cellsClon[x][y];
    }
    
    public static class CellLight{
        public float light = 0;
        public float rC, gC, bC;
        
        public void draw(Sprite sp, SpriteBatch b, int x, int y){
            sp.setColor(rC, gC, bC, 1 - light);
            sp.setAlpha(1 - light);
            sp.setPosition(x * Block.SIZE, y * Block.SIZE);
            sp.setSize(Block.SIZE, Block.SIZE);
            sp.draw(b);
        }
    }
}
