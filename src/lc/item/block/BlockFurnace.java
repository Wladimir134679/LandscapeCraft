package lc.item.block;

import com.badlogic.gdx.graphics.g2d.Sprite;
import lc.block.managers.ManagerBlocks;
import lc.game.GameScreen;
import lc.inventory.InventoryManager;
import lc.inventory.invBlocks.InventoryBlock;
import lc.inventory.invBlocks.InventoryFurnace;
import lc.item.DataAll;
import lc.utils.PropertyUtil;
import lc.world.LayerBlock;
import lc.world.World;

/**
 * @author Death
 */
public class BlockFurnace extends Block{
    
    private static final String TEXTURE_STATE = "tex_state";

    @Override
    public void action(LayerBlock w, int x, int y){
        InventoryFurnace fur = (InventoryFurnace)ManagerBlocks.get(this, x, y);
        InventoryManager.getThis().showInvBlock(fur);
    }

    @Override
    public void crashed(LayerBlock w, int x, int y){
        ManagerBlocks.get(this, x, y).crashDrop(GameScreen.GAME_SCREEN.world, x, y);
        BlockProperty.remove(x, y);
        ManagerBlocks.remove(x, y);
    }

    @Override
    public boolean isAction(LayerBlock w, int x, int y){
        return !ManagerBlocks.get(this, x, y).isClose();
    }
    
    @Override
    public Sprite getSprite(int x, int y){
        PropertyUtil pr = BlockProperty.getData(x, y);
        if(pr.get(TEXTURE_STATE) == null){
            pr.set(TEXTURE_STATE, "off");
        }
        return DataAll.get().textures.get(nameKod + "_" + pr.get(TEXTURE_STATE));
    }
    
    public void on(PropertyUtil pro){
        pro.set(TEXTURE_STATE, "on");
    }
    
    public void off(PropertyUtil pro){
        pro.set(TEXTURE_STATE, "off");
    }

    @Override
    public InventoryBlock getNewInventory(int x, int y){
        PropertyUtil pro = BlockProperty.getData(x, y);
        InventoryFurnace fur = new InventoryFurnace(this, pro);
        return fur;
    }
    
    public int getTimeMelt(){
        return propertyBlock.getInt("timeMelt");
    }
    
    public String getNameTexture(){
        return propertyBlock.get("nameTexture");
    }
}
