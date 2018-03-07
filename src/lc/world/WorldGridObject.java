package lc.world;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import lc.world.objectes.TypeObjectWorld;
import lc.world.objectes.WorldObject;

/**
 * @author Death
 */
public class WorldGridObject {

    public ArrayList<WorldObject>[][] staticObj;
    public ArrayList<WorldObject>[][] dynamicObj;
    public ArrayList<WorldObject> dynamicUpdateObj;
    
    private final ArrayList<WorldObject> foundObj, foundObjGroup;
    private final int sizeCell;
    private final int widthMech, heightMech;

    public WorldGridObject(int sizeCell, int widthWorld, int heightWorld){
        this.sizeCell = sizeCell;
        this.widthMech = widthWorld / this.sizeCell + 1;
        this.heightMech = heightWorld / this.sizeCell + 1;
        
        dynamicObj = new ArrayList[this.widthMech][this.heightMech];
        staticObj = new ArrayList[this.widthMech][this.heightMech];
        
        for(int i = 0; i < this.widthMech; i++){
            for(int j = 0; j < this.heightMech; j++){
                dynamicObj[i][j] = new ArrayList<>();
                staticObj[i][j] = new ArrayList<>();
            }
        }
        foundObj = new ArrayList<>();
        foundObjGroup = new ArrayList<>();
        dynamicUpdateObj = new ArrayList<>();
    }
    public void add(WorldObject ob){
        this.add(ob, true);
        // Сделано специально, передача boolean и от него зависит, добовлять список обновляющихся или нет.
        // Так как проверка на boolen быстрее чем достать из ArrayList объект и проверить его на null
    }
    
    public void add(WorldObject ob, boolean addUp){
        float x1 = ob.x / sizeCell;
        float y1 = ob.y / sizeCell;
        float x2 = (ob.x + ob.width) / sizeCell;
        float y2 = (ob.y + ob.height) / sizeCell;
        if(x1 < 0) x1 = 0;
        if(y1 < 0) y1 = 0;
        if(x2 > widthMech) x2 = widthMech;
        if(y2 > heightMech) y2 = heightMech;
        
        if(ob.type == TypeObjectWorld.DYNAMIC){
            for(int i = (int) x1; i < x2; i++){
                for(int j = (int) y1; j < y2; j++){
                    dynamicObj[i][j].add(ob);
                    if(addUp) dynamicUpdateObj.add(ob);
                }
            }
        }else if(ob.type == TypeObjectWorld.STATIC){
            for(int i = (int) x1; i < x2; i++){
                for(int j = (int) y1; j < y2; j++){
                    staticObj[i][j].add(ob);
                }
            }
        }
    }
    
    public void updateDynamic(){
        this.removeAllDynamic();
        this.dynamicUpdateObj.forEach(ob -> {
            add(ob, false);
        });
    }
    
    public void removeAllDynamic(){
        this.removeAllDynamic(Rectangle.tmp.set(0, 0, widthMech, heightMech));
    }
    
    public void removeAllDynamic(Rectangle rect){
        for(int i = (int)rect.x; i < (int)(rect.x + rect.width); i++){
            for(int j = (int)rect.y; j < (int)(rect.y + rect.height); j++){
                dynamicObj[i][j].clear();
            }
        }
    }
    
    public void removeObject(WorldObject ob){
        float x1 = ob.x / sizeCell;
        float y1 = ob.y / sizeCell;
        float x2 = (ob.x + ob.width) / sizeCell;
        float y2 = (ob.y + ob.height) / sizeCell;
        if(x1 < 0) x1 = 0;
        if(y1 < 0) y1 = 0;
        if(x2 > widthMech) x2 = widthMech;
        if(y2 > heightMech) y2 = heightMech;
        dynamicUpdateObj.remove(ob);
        for(int i = (int) x1; i < x2; i++){
            for(int j = (int) y1; j < y2; j++){
                dynamicObj[i][j].remove(ob);
                staticObj[i][j].remove(ob);
            }
        }
    }
    
    public ArrayList<WorldObject> getObjectes(Rectangle rect){
        foundObj.clear();
        float x1 = rect.x / sizeCell;
        float y1 = rect.y / sizeCell;
        float x2 = (rect.x + rect.width) / sizeCell;
        float y2 = (rect.y + rect.height) / sizeCell;
        if(x1 < 0) x1 = 0;
        if(y1 < 0) y1 = 0;
        if(x2 > widthMech) x2 = widthMech;
        if(y2 > heightMech) y2 = heightMech;
        for(int i = (int) x1; i < x2; i++){
            for(int j = (int) y1; j < y2; j++){
                ArrayList<WorldObject> objs = dynamicObj[i][j];
                foundObj.addAll(objs);
            }
        }
        for(int i = (int) x1; i < x2; i++){
            for(int j = (int) y1; j < y2; j++){
                ArrayList<WorldObject> objs = staticObj[i][j];
                foundObj.addAll(objs);
            }
        }
        return foundObj;
    }
    
    public ArrayList<WorldObject> getObjectes(Rectangle rect, int group){
        this.getObjectes(rect);
        foundObjGroup.clear();
        for(WorldObject ob : foundObj){
            if(ob.group == group)
                foundObjGroup.add(ob);
        }
        return foundObjGroup;
    }
}
