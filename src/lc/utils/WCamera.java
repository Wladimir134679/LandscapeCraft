package lc.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Death
 */
public class WCamera extends OrthographicCamera{

    private Vector3 posMouse;

    public WCamera(){
        posMouse = new Vector3();
    }
    
    public int getWidth(){
        return (int)(this.viewportWidth * this.zoom);
    }
    
    public int getHeight(){
        return (int)(this.viewportHeight * this.zoom);
    }
    
    public int getX(){
        return (int)(this.position.x - this.getWidth() / 2);
    }
    
    public int getY(){
        return (int)(this.position.y - this.getHeight()/ 2);
    }
    
    public void setX(int x){
        this.position.x = x + getWidth() / 2;
    }
    
    public void setY(int y){
        this.position.y = y + getHeight() / 2;
    }
    
    public int getMouseX(){
        return this.getMouseX(Gdx.input.getX());
    }
    
    public int getMouseY(){
        return this.getMouseY(Gdx.input.getY());
    }
    
    public int getMouseX(int xM){
        posMouse.set(xM, Gdx.input.getY(), 0);
        posMouse = this.unproject(posMouse);
        return (int)posMouse.x;
    }
    
    public int getMouseY(int yM){
        posMouse.set(Gdx.input.getX(), yM, 0);
        posMouse = this.unproject(posMouse);
        return (int)posMouse.y;
    }
}
