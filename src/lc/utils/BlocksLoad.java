package lc.utils;

/**
 * @author Death
 */
public class BlocksLoad {
//    
//    public static Block block;
//    
//    public static void parserJson(JSONObject blockInfa, Item item){
//        block = null;
//        if(!blockInfa.isNull("classPath")){
//            loadClass(blockInfa.getString("classPath"));
//            if(!blockInfa.isNull("data") && blockInfa.getBoolean("data")){
//                 loadBlock(blockInfa);
//            }
//        }else{
//            load(blockInfa);
//        }
//        if(block == null) return;
//        block.setNameKod(item.nameKod)
//                .setId(item.id);
//        if(!blockInfa.getString("item").equals("this"))
//            block.setItem(blockInfa.getString("item"));
//        else
//            block.setItem(item.nameKod);
//        if(!blockInfa.isNull("animTexBlock")){
//            JSONObject animTexBlock = blockInfa.getJSONObject("animTexBlock");
//            block.setAnimTexBlock(AnimTextureBlock.load(animTexBlock));
//        }
//        Block.ids.put(block.nameKod, block.id);
//        Block.blocks.put(block.id, block);
//        block.init();
//        block.itemParent = item;
//    }
//    
//    public static void load(JSONObject blockInfo){
//        if(!blockInfo.isNull("extendsBlock")){
//            String extendsBlock = blockInfo.getString("extendsBlock");
//            String[] parametrs = null;
//            if(!blockInfo.isNull("parametrs")){
//                parametrs = blockInfo.getString("parametrs").split(":");
//            }
//            switch(extendsBlock){
//                case "GRASS": block = new BlockGrass(parametrs[0], parametrs[1]); break;
//                case "FOLIAGE": block = new BlockFoliage(); break;
//                case "PLANT_GRASS": block = new BlockPlantGrass(); break;
//                case "CHEST": block = new BlockChest(Integer.parseInt(parametrs[0])); break;
//                case "SEEDLING": block = new BlockSeedling(parametrs[0], parametrs[1]); break;
//                case "FURNACE": block = new BlockFurnace(parametrs[0], Integer.parseInt(parametrs[1])); break;
//                case "CRAFTING_TABLE": block = new BlockCraftingTable(); break;
//                default: block = new Block(); break;
//            }
//        }
//        if(block == null)
//            block = new Block();
//        loadBlock(blockInfo);
//    }
//    
//    private static void loadBlock(JSONObject blockInfo){
//        int numItem = blockInfo.getInt("numItem");
//        float density = blockInfo.getInt("density") / 100f;
//        String nameMaterial = blockInfo.getString("material");
//        String type = blockInfo.getString("type");
//
//        block
//            .setMaterial(nameMaterial)
//            .setType(type)
//            .setNumItem(numItem)
//            .setDensity(density);
//    }
//    
//    private static void loadClass(String classPath){
//        try{
//            block = (Block)Class.forName(classPath).newInstance();
//        }
//        catch(InstantiationException | IllegalAccessException | ClassNotFoundException ex){
//            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
