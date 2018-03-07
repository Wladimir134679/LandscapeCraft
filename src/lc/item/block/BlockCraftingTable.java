package lc.item.block;

import lc.block.managers.ManagerBlocks;
import lc.game.GameScreen;
import lc.inventory.InventoryManager;
import lc.inventory.invBlocks.InventoryBlock;
import lc.inventory.invBlocks.InventoryCraftingTable;
import lc.world.LayerBlock;

/**
 * @author Death
 */
public class BlockCraftingTable extends Block{

    @Override
    public void action(LayerBlock w, int x, int y){
        InventoryCraftingTable inv = (InventoryCraftingTable)ManagerBlocks.get(this, x, y);
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
        return new InventoryCraftingTable(this, BlockProperty.getData(x, y));
    }
}
