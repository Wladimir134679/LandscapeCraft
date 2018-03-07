package lc.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lc.Asset;
import lc.MyGame;
import lc.SettingApp;
import lc.game.GameInfa;
import lc.game.GameScreen;
import lc.game.LoadGame;

/**
 * @author Death
 */
public class ScreenSelectWorld extends ScreenMenuParent{

    ScreenMenu mainScreen;
    private ThreadFindWorld findWorld;
    private ArrayList<GameInfa> arrGameInfa;
    private List<GameInfa> listGame;
    private List<String> listInfaWorld;
    private ScrollPane scrollSelectGame, infaWorld;
    private TextButton newGame, playGame, deliteWorld, renameWorld, back;
    private ScreenNewGame scNewGame;
    private ArrayList<String> arrInfaStrings;
    private long timeBegin;

    public ScreenSelectWorld(ScreenMenu mainScreen){
        super();
        this.mainScreen = mainScreen;
        arrInfaStrings = new ArrayList<>();
        scNewGame = null;
    }
    
    @Override
    public void show(Stage st){
        int width = 500;
        int widthSelectPane = 900;
        int heightSelectPane = 400;
        int ySelect = Gdx.graphics.getHeight() - heightSelectPane - 10;
        
        findWorld = new ThreadFindWorld();
        arrGameInfa = new ArrayList<>();
        listGame = new List<>(Asset.skinUI);
        listInfaWorld = new List<>(Asset.skinUI);
        scrollSelectGame = new ScrollPane(listGame, Asset.skinUI);
        infaWorld = new ScrollPane(listInfaWorld, Asset.skinUI);
        newGame = new TextButton("Новый мир", Asset.skinUI);
        newGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(scNewGame == null){
                    scNewGame = new ScreenNewGame(ScreenSelectWorld.this);
                }
                MyGame.game.setScreen(scNewGame);
            }
        });
        playGame = new TextButton("Запустить", Asset.skinUI);
        playGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(listGame.getSelected() == null)
                    return;
                GameInfa gi = listGame.getSelected();
                GameScreen game = new GameScreen();
                LoadGame.load(game, gi);
                MyGame.game.setScreen(game);
            }
        });
        deliteWorld = new TextButton("Удалить", Asset.skinUI);
        deliteWorld.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(listGame.getSelected() == null)
                    return;
                GameInfa gi = listGame.getSelected();
                Gdx.files.local(SettingApp.DIRICTORY_WORLD + gi.dirPath).deleteDirectory();
                show();
            }
        });
        renameWorld = new TextButton("Переименовать", Asset.skinUI);
        renameWorld.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(listGame.getSelected() == null)
                    return;
                GameInfa gi = listGame.getSelected();
                Input.TextInputListener input = new Input.TextInputListener() {
                    @Override
                    public void input(String string){
                        if(string.equals(""))
                            return;
                        gi.nameWorld = string;
                        gi.save(Gdx.files.local(SettingApp.DIRICTORY_WORLD + gi.dirPath));
                        show();
                    }

                    @Override
                    public void canceled(){
                    }
                };
                Gdx.input.getTextInput(input, "Название мира", gi.nameWorld, "");
            }
        });
        back = new TextButton("Назад", Asset.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.game.setScreen(mainScreen);
            }
        });
        
        infaWorld.setSize(widthSelectPane / 2, heightSelectPane);
        infaWorld.setPosition(Gdx.graphics.getWidth() / 2 + 5, 
                              ySelect);
        stage.addActor(infaWorld);
        
        scrollSelectGame.setSize(widthSelectPane / 2, heightSelectPane);
        scrollSelectGame.setPosition(Gdx.graphics.getWidth() / 2 - scrollSelectGame.getWidth() - 5, 
                                     ySelect);
        scrollSelectGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameInfa infa = listGame.getSelected();
                if(infa == null)
                    return;
                arrInfaStrings.clear();
                arrInfaStrings.add(String.valueOf("Название мира: " + infa.nameWorld));
                arrInfaStrings.add(String.valueOf("Время создания: " + infa.dateCreateWorld));
                arrInfaStrings.add(String.valueOf("Размер: " + infa.getSize()));
                arrInfaStrings.add(String.valueOf("- Высота: " + infa.width));
                arrInfaStrings.add(String.valueOf("- Длина: " + infa.height));
                arrInfaStrings.add(String.valueOf("SEED: " + infa.seed));
                arrInfaStrings.add(String.valueOf("Название папки: " + infa.dirPath));
                listInfaWorld.clearItems();
            }
        });
        stage.addActor(scrollSelectGame);
        
        newGame.setSize(width / 2 - 10, 30);
        newGame.setPosition(Gdx.graphics.getWidth() / 2 - width / 2, 
                            ySelect - 40);
        stage.addActor(newGame);
        
        playGame.setSize(width / 2 - 10, 30);
        playGame.setPosition(Gdx.graphics.getWidth() / 2 + 10, 
                            ySelect - 40);
        stage.addActor(playGame);
        
        renameWorld.setSize(width / 2 - 10, 30);
        renameWorld.setPosition(Gdx.graphics.getWidth() / 2 - width / 2, 
                               ySelect - 40 - 30 - 10);
        stage.addActor(renameWorld);
        
        deliteWorld.setSize(width / 2 - 10, 30);
        deliteWorld.setPosition(Gdx.graphics.getWidth() / 2 + 10, 
                               ySelect - 40 - 30 - 10);
        stage.addActor(deliteWorld);
        
        back.setSize(width, 30);
        back.setPosition(Gdx.graphics.getWidth() / 2 - width / 2, 
                         ySelect - 40 - 30 - 10 - 30 - 10);
        stage.addActor(back);
        
        findWorld.start();
    }

    @Override
    public void draw(SpriteBatch b){
        Asset.backgroundImageScreenPrint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Asset.backgroundImageScreenPrint.setPosition(0, 0);
        Asset.backgroundImageScreenPrint.draw(b);
        Asset.background.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if(arrGameInfa.size() > 0){
            for(int i = 0; i < arrGameInfa.size(); i++){
                GameInfa infa = arrGameInfa.get(i);
                arrGameInfa.remove(infa);
                i--;
                
                listGame.getItems().add(infa);
            }
        }
        if(!arrInfaStrings.isEmpty()){
            if(System.currentTimeMillis() - timeBegin > 50){
                timeBegin = System.currentTimeMillis();
                listInfaWorld.getItems().add(arrInfaStrings.get(0));
                arrInfaStrings.remove(0);
            }
        }
    }

    public class ThreadFindWorld implements Runnable{

        private void start(){
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
        }
        
        @Override
        public void run(){
            this.sleep(50);
            FileHandle dirWorld = Gdx.files.local(SettingApp.DIRICTORY_WORLD);
            FileHandle[] dirs = dirWorld.list();
            for(FileHandle dir : dirs){
                if(dir.isDirectory()){
                    FileHandle gameInfa = Gdx.files.local(dir.path() + "/" + "game.bin");
                    if(gameInfa == null)
                        continue;
                    GameInfa infa = new GameInfa();
                    infa.dirPath = dir.name();
                    infa.load(dir);
                    arrGameInfa.add(infa);
                    sleep(25);
                }
            }
        }
        
        private void sleep(int i){
            try{
                Thread.currentThread().sleep(i);
            }
            catch(InterruptedException ex){
                Logger.getLogger(ScreenSelectWorld.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
