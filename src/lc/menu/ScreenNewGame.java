package lc.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lc.Asset;
import lc.MyGame;
import lc.game.GameInfa;

/**
 * @author Death
 */
public class ScreenNewGame extends ScreenMenuParent{
    

    public TextField nameWorld;
    public TextField idSEED;
    public SelectBox<String> selSize;
    
    private TextButton create, back;
    private ScreenSelectWorld selectWorld;

    public ScreenNewGame(ScreenSelectWorld selectWorld){
        this.selectWorld = selectWorld;
    }
    
    
    @Override
    public void show(Stage st){
        nameWorld = new TextField("Новый мир", Asset.skinUI);
        idSEED = new TextField(String.valueOf(MathUtils.random(Long.MAX_VALUE)), Asset.skinUI);
        selSize = new SelectBox<>(Asset.skinUI);
        create = new TextButton("Создать", Asset.skinUI);
        create.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(nameWorld.getText().equals("") ||
                   selSize.getSelected() == null ||
                   idSEED.getText().equals(""))
                    return;
                int width = 0;
                int heigth = 0;
                long seed = Long.parseLong(idSEED.getText());
                if(selSize.getSelected().equals(GameInfa.WORLD_SIZE[0])){
                    width = 700;
                    heigth = 500;
                }else if(selSize.getSelected().equals(GameInfa.WORLD_SIZE[1])){
                    width = 1000;
                    heigth = 700;
                }else if(selSize.getSelected().equals(GameInfa.WORLD_SIZE[2])){
                    width = 1500;
                    heigth = 900;
                }
                GameInfa infa = new GameInfa();
                infa.nameWorld = nameWorld.getText();
                infa.dirPath = String.valueOf(nameWorld.getText() + System.currentTimeMillis());
                infa.width = width;
                infa.height = heigth;
                infa.seed = seed;
                
                MyGame.game.setScreen(new GenerateWorldScreen(infa));
            }
        });
        back = new TextButton("Назад", Asset.skinUI);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.game.setScreen(selectWorld);
            }
        });
        Label infaName, infaSize, infaSEED;
        infaName = new Label("Название карты: ", Asset.skinUI);
        infaSize = new Label("Размер карты: ", Asset.skinUI);
        infaSEED = new Label("SEED: ", Asset.skinUI);
        
        int widthField = 300;
        int heightField = 30;
        nameWorld.setSize(widthField, heightField);
        selSize.setSize(widthField, heightField);
        idSEED.setSize(widthField, heightField);
        
        infaName.setSize(widthField, heightField);
        infaSize.setSize(widthField, heightField);
        infaSEED.setSize(widthField, heightField);
        
        TextField.TextFieldFilter filterTextNum = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField tf, char c){
                return Character.isDigit(c);
            }
        };
        
        selSize.setItems(GameInfa.WORLD_SIZE);
        selSize.setSelected(GameInfa.WORLD_SIZE[1]);
        idSEED.setTextFieldFilter(filterTextNum);
        
        int xField = Gdx.graphics.getWidth() / 2;
        int yFieldBegin = Gdx.graphics.getHeight() - heightField - 10;
        int indent = heightField + 10;
        
        nameWorld.setPosition(xField + 5, yFieldBegin - indent * 0);
        idSEED.setPosition(xField + 5, yFieldBegin - indent * 1);
        selSize.setPosition(xField + 5, yFieldBegin - indent * 2);
        
        infaName.setPosition(xField - widthField - 5, yFieldBegin - indent * 0);
        infaSEED.setPosition(xField - widthField - 5, yFieldBegin - indent * 1);
        infaSize.setPosition(xField - widthField - 5, yFieldBegin - indent * 2);
        
        create.setPosition(Gdx.graphics.getWidth() / 2 - 100, yFieldBegin - indent * 3 - (30 + 10) * 1);
        create.setSize(250, 30);
        
        back.setPosition(Gdx.graphics.getWidth() / 2 - 100, yFieldBegin - indent * 3 - (30 + 10) * 2);
        back.setSize(250, 30);
        
        st.addActor(infaName);    st.addActor(nameWorld);
        st.addActor(infaSize);    st.addActor(selSize);
        st.addActor(infaSEED);    st.addActor(idSEED);
        
        st.getActors().addAll(create, back);
    }

    @Override
    public void draw(SpriteBatch b){
        Asset.backgroundImageScreenPrint.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Asset.backgroundImageScreenPrint.setPosition(0, 0);
        Asset.backgroundImageScreenPrint.draw(b);
        Asset.background.draw(batch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
