package lc.processing;

import java.util.ArrayList;
import lc.item.block.Block;
import lc.item.block.TypeBlock;
import lc.item.Item;
import lc.item.ItemTool;

/**
 * @author Death
 */
public class ManagerItemAndBlock {
    
    private static ManagerItemAndBlock manager = null;
    
    public static final ManagerItemAndBlock get(){
        if(manager == null)
            manager = new ManagerItemAndBlock();
        return manager;
    }

    public ArrayList<Item> item;

    public ManagerItemAndBlock(){
        item = new ArrayList<>();
        
    }
    
    public ManagerItemAndBlock createNewStandart(){
        Block bl = new Block();
        bl.id = 0;
        bl.density = 100f;
        bl.nameKod = "TestBlock";
        bl.numItem = 1;
        bl.type = TypeBlock.SOLID;
        bl.namesMaterial.add("material1");
        bl.item = "this";
        
        Item it = new Item();
        it.id = 0;
        it.block = bl;
        it.maxNum = 15;
        it.nameKod = "dirst";
        it.nameRU = "Земля";
        it.nameEN = "Dirst";
        it.infaRU = "Земля =)";
        it.infaEN = "Dirst =)";
        it.nameTexture = "tex-dirst";
        it.namesMaterial = new ArrayList<>();
        it.namesMaterial.add("mat1");
        it.namesMaterial.add("mat2");
        it.property.set("keyOther1", "value1");
        item.add(it);
        
        ItemTool tool = new ItemTool();
        tool.id = 1;
        tool.maxNum = 15;
        tool.nameKod = "pixcake";
        tool.nameRU = "Кирка";
        tool.nameEN = "Pixcake";
        tool.infaRU = "Кирка =)";
        tool.infaEN = "Pixcake =)";
        tool.nameTexture = "tex-pixcake";
        tool.namesMaterial = new ArrayList<>();
        tool.force = 13f;
        tool.crashType = new ArrayList<>();
        tool.crashType.add("cr1");
        tool.crashType.add("cr2");
        tool.crashType.add("cr3");
        tool.strengthMax = 10;
        item.add(tool);
        
        return this;
    }
}
