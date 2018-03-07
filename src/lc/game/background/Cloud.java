package lc.game.background;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import lc.SettingApp;

/**
 * @author Death
 */
public class Cloud {

    public BackgroundGame back;
    public Sprite sp;
    
    public float x, y;
    public float width, height;
    public float zum;
    public int dirMove;

    public Cloud(BackgroundGame back){
        this.back = back;
        spawn();
        x = MathUtils.random(0, SettingApp.WIDTH_WIN);
    }
    
    public void spawn(){
        spawn(back.dirWind);
    }
    
    public void spawn(int dirWind){
        sp = back.sprites.get(MathUtils.random(0, back.sprites.size() - 1));
        y = MathUtils.random(300, 580);
        zum = MathUtils.random(1f, 10f);
        width = back.widthTex * zum;
        height = back.heightTex * zum;
        if(back.dirWind == 0){
            dirMove = 0;
            while(dirMove == 0)
                dirMove = MathUtils.random(-1, 1);
        }else{
            dirMove = back.dirWind;
        }
        if(dirWind == 1){
            x = -width - 10;
        }else if(dirWind == -1){
            x = SettingApp.WIDTH_WIN;
        }
    }

    public void draw(SpriteBatch b){
        sp.setSize(width, height);
        sp.setPosition(x, y);
        sp.draw(b);
    }
    
    public void update(int napP, float delta){
        if(napP == 0)
            x += back.speedWind * dirMove * (zum / 2f);
        else{
            x += (back.speedWind + 0.1f + Math.abs(delta) / 10f) * -napP * (zum / 2f) + back.speedWind * dirMove * zum;
        }
        if(napP == 0){
            if(x > SettingApp.WIDTH_WIN + width || x < -(width * 2)){
                spawn();
            }
        }else{
            if(x > SettingApp.WIDTH_WIN + width || x < -(width * 2)){
                spawn(-napP);
            }
        }
    }
}
