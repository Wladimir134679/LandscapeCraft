package lc.block.processing;

import lc.block.managers.ManagerBlocks;

/**
 * @author Death
 */
public class ProcessingBlockFurnace extends ProcessingBlock{

    public ProcessingBlockFurnace(){
        super();
    }
    
    @Override
    public void run(ManagerProcessingBlockTool manager){
        ManagerBlocks.get(block, prop.getInt("x"), prop.getInt("y")).processingEnd(block, prop);
    }
}
