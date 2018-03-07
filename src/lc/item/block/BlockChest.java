package lc.item.block;

import lc.block.managers.ManagerBlocks;
import lc.game.GameScreen;
import lc.inventory.InventoryManager;
import lc.inventory.invBlocks.InventoryBlock;
import lc.inventory.invBlocks.InventoryChest;
import lc.world.LayerBlock;
import lc.world.World;

/**
 * @author Death
 */
public class BlockChest extends Block{

    
    @Override
    public void action(LayerBlock w, int x, int y){
        InventoryChest inv = (InventoryChest)ManagerBlocks.get(this, x, y);
        InventoryManager.getThis().showInvBlock(inv);
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
    public InventoryBlock getNewInventory(int x, int y){
        return new InventoryChest(this, BlockProperty.getData(x, y), getNumRow());
    }
    
    public int getNumRow(){
        return propertyBlock.getInt("numRow");
    }
}
