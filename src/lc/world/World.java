package lc.world;

import lc.world.threads.GrowSeedling;
import lc.world.threads.GrowGrass;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import lc.game.Cursor;
import lc.game.GameScreen;
import lc.item.block.Block;
import lc.item.block.TypeBlock;
import lc.utils.WCamera;
import lc.world.generator.Generator;
import lc.world.objectes.WorldObject;
import lc.world.threads.FallFoliage;
import lc.world.threads.ManagerLight;

/**
 * @author Death
 */
public class World {
    
    public final Rectangle size;
    
    public int widthBlock, heightBlock;
    public WorldGridObject gridObject;
    public ArrayList<WorldObject> objectes = new ArrayList<>();
    public Ground ground;
    public BackgroundLayer backLayerBlock;
    public GrowGrass grassGrow;
    public GrowSeedling seedlingGrow;
    public FallFoliage fallFoliage;
    public Generator generator;
    public LayerLight layerLight;
    public ManagerLight managerLight;
    public float lightWorld;
    public HashMap<String, Vector2> spawnsObject;
    
    public World(GameScreen game, int widthBlock, int heightBlock){
        size = new Rectangle(0, 0, widthBlock * Block.SIZE, Block.SIZE);
        this.widthBlock = widthBlock;
        this.heightBlock = heightBlock;
        gridObject = new WorldGridObject(Block.SIZE * 10, this.getWidth(), this.getHeight());
        ground = new Ground(this);
        backLayerBlock = new BackgroundLayer(this);
        layerLight = new LayerLight(game, this);
        grassGrow = new GrowGrass(game);
        seedlingGrow = new GrowSeedling(game);
        fallFoliage = new FallFoliage(game);
        managerLight = new ManagerLight(game);
        lightWorld = 1;
        spawnsObject = new HashMap<>();
    }
    
    public void spawnObject(String nameSp, WorldObject obj){
        if(spawnsObject.get(nameSp) == null) return;
        Vector2 point = spawnsObject.get(nameSp);
        obj.x = point.x * Block.SIZE;
        obj.y = point.y * Block.SIZE;
    }
    
    public void start(){
        grassGrow.start(2, 100);
        seedlingGrow.start(15, 200);
        fallFoliage.start();
        managerLight.start();
    }
    
    public void draw(WCamera cam, SpriteBatch b){
        backLayerBlock.draw(cam, b);
        ground.draw(cam, b);
        this.drawObject(cam, b);
        layerLight.draw(cam, b);
    }
    
    public void drawObject(WCamera cam, SpriteBatch b){
        Rectangle.tmp2.set(cam.getX(), cam.getY(), cam.getWidth(), cam.getHeight());
        ArrayList<WorldObject> objs = gridObject.getObjectes(Rectangle.tmp2);
        objs.forEach(ob ->{
            ob.draw(b);
        });
    }
    
    public void update(WCamera cam){
        objectes.forEach(ob ->{
            ob.move();
        });
        gridObject.updateDynamic();
    }
    
    public void add(WorldObject ob){
        this.objectes.add(ob);
        this.gridObject.add(ob);
    }
    
    public void remove(WorldObject ob){
        this.objectes.remove(ob);
        this.gridObject.removeObject(ob);
    }
    
    public int getWidth(){
        return widthBlock * Block.SIZE;
    }
    
    public int getHeight(){
        return heightBlock * Block.SIZE;
    }
    
    public Block getBlock(int x, int y){
        if(ground.getBlock(x, y) != null)
            return ground.getBlock(x, y);
        else
            return backLayerBlock.getBlock(x, y);
    }
    
    public boolean isBetBlock(Block b, Cursor cur, int x, int y){
        if(b.type == TypeBlock.PASSABLE) return true;
        ArrayList<WorldObject> objs = gridObject.getObjectes(cur.getRectangle());
        for(WorldObject obj : objs){
            for(int i = (int)(obj.x / obj.getSizeBlock()); i < Math.floor(obj.x + obj.width) / obj.getSizeBlock(); i++){
                for(int j = (int)(obj.y / obj.getSizeBlock()); j < Math.floor(obj.y + obj.height) / obj.getSizeBlock(); j++){
                    if(i == x && y == j) return false;
                }
            }
        }
        return true;
    }
    
    public ArrayList<WorldObject> getObjectes(int group){
        size.set(0, 0, widthBlock * Block.SIZE, heightBlock * Block.SIZE);
        ArrayList<WorldObject> objs = gridObject.getObjectes(size, group);
        return objs;
    }
}
