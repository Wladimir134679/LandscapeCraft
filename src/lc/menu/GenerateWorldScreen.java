package lc.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;
import lc.Asset;
import lc.MyGame;
import lc.game.GameInfa;
import lc.game.GameScreen;
import lc.game.SaveGame;
import lc.world.World;
import lc.world.generator.Generator;
import lc.world.generator.GeneratorNormal;
import org.lwjgl.opengl.GL11;

/**
 * @author Death
 */
public class GenerateWorldScreen extends ScreenMenuParent{
    
    private ThreadGenerate gen;
    private BitmapFont font;
    private String infa;
    public GameScreen game;
    public GameInfa gameInfa;
    private World world;
    
    public boolean endGen;
    public long timeEnd;
    
    private SimpleDateFormat formatDate;

    public GenerateWorldScreen(GameInfa infa){
        Date dateCurrent = new Date();
        formatDate = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        infa.dateCreateWorld = formatDate.format(dateCurrent);
        this.gameInfa = infa;
    }
    
    @Override
    public void show(Stage st){
        game = new GameScreen();
        gen = new ThreadGenerate();
        font = Asset.skinUI.get("font-small", BitmapFont.class);
        
        batch = new SpriteBatch();
        
        gen = new ThreadGenerate();
        gen.start();
        endGen = false;
    }

    @Override
    public void draw(SpriteBatch b){
        infa = Generator.PROCESS_INFA;
        if(endGen){
            infa = "Мир создан";
            if(System.currentTimeMillis() - timeEnd > 250){
                game.init(world, gameInfa);
                MyGame.game.setScreen(game);
                return;
            }
        }
        Asset.backgroundImageScreenPrint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Asset.backgroundImageScreenPrint.setPosition(0, 0);
        Asset.backgroundImageScreenPrint.draw(b);
        Asset.background.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Asset.background.draw(batch, camera.getWidth() / 2 - camera.getWidth() / 4, 
                                     camera.getHeight() / 2 - font.getCapHeight() / 2 - 6,
                                     camera.getWidth() / 2, 
                                     font.getCapHeight() + 12);
        font.draw(batch, infa, camera.getWidth() / 2 - (infa.length() * font.getSpaceWidth() / 2),
                               camera.getHeight() / 2 + font.getCapHeight() / 2);
        
    }

    private class ThreadGenerate extends Thread{

        @Override
        public void run(){
            world = new World(game, gameInfa.width, gameInfa.height);
            Generator gen = new GeneratorNormal(gameInfa.seed, world, gameInfa.height / 3);
            gen.gen();
            endGen = true;
            timeEnd = System.currentTimeMillis();
        }
    }

}
