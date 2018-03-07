package lc.utils;

/**
 * @author Death
 */
public class ItemsLoad {
//
//    private static Item item;
//    
//    public static void parseJson(JSONObject itemInfa){
//        item = null;
//        if(!itemInfa.isNull("classPath")){
//            loadClass(itemInfa.getString("classPath"));
//            if(!itemInfa.isNull("data") && itemInfa.getBoolean("data"))
//                loadItem(itemInfa);
//        }else{
//            load(itemInfa);
//        }
//        
//        
//        if(item == null) return;
//        if(!itemInfa.isNull("infaRU"))
//            item.infaRU = itemInfa.getString("infaRU");
//        if(!itemInfa.isNull("infaEN"))
//            item.infaEN = itemInfa.getString("infaEN");
//        if(!itemInfa.isNull("block")){
//            if(itemInfa.getJSONObject("block").isNull("blockName"))
//                BlocksLoad.parserJson(itemInfa.getJSONObject("block"), item);
//            else
//                item.block = itemInfa.getJSONObject("block").getString("blockName");
//        }
//        if(!itemInfa.isNull("recipe")){
//            JSONObject dataRecipte = itemInfa.getJSONObject("recipe");
//            CraftManager.addRecipte(item, dataRecipte);
//        }
//        if(!itemInfa.isNull("materials")){
//            item.setMaterials(itemInfa.getString("materials"));
//        }
//        if(!itemInfa.isNull("property")){
//            item.property = PropertyUtil.parse(itemInfa.getJSONArray("property"));
//        }
//        Item.ids.put(item.nameKod, item.id);
//        Item.items.put(item.id, item);
//        item.init();
//    }
//    
//    private static void load(JSONObject itemInfo){
//        String type = "ITEM";
//        if(!itemInfo.isNull("type")) type = itemInfo.getString("type");
//        switch(type){
//            case "TOOL": loadTool(itemInfo); break;
//            case "ITEM": loadItem(itemInfo); break;
//            default: System.err.println("ERROR! ItemLoad / Type = " + type);
//        }
//    }
//    
//    private static void loadItem(JSONObject itemInfo){
//        loadMainData(itemInfo);
//        int maxNum = itemInfo.getInt("maxNum");
//        String block = item.nameKod;
//        item
//                .setMaxNum(maxNum)
//                .setBlock(block);
//    }
//    
//    private static void loadTool(JSONObject itemInfo){
//        float force = Float.parseFloat(itemInfo.getString("force"));
//        int strength = itemInfo.getInt("strength");
//        String crashType = itemInfo.getString("crashType");
//        
//        
//        if(item == null || !(item instanceof ItemTool)){
//            item = new ItemTool();
//        }
//        ((ItemTool)item)
//                .setCrashType(crashType)
//                .setForce(force)
//                .setStrength(strength)
//                .setBlock("")
//                .setMaxNum(1);
//        loadMainData(itemInfo);
//    }
//    
//    private static void loadMainData(JSONObject itemInfo){
//        String nameKod = itemInfo.getString("nameKod");
//        int id = itemInfo.getInt("id");
//        String nameTex = itemInfo.getString("texture");
//        
//        String nameRu = nameKod;
//        if(!itemInfo.isNull("nameRU")) nameRu = itemInfo.getString("nameRU");
//        String nameEn = nameKod;
//        if(!itemInfo.isNull("nameEN")) nameEn = itemInfo.getString("nameEN");
//        
//        if(item == null) item = new Item();
//        item
//            .setId(id)
//            .setNameKod(nameKod)
//            .setTexture(nameTex)
//            .setName(nameRu, nameEn);
//    }
//    
//    private static void loadClass(String classPath){
//        try{
//            item = (Item)Class.forName(classPath).newInstance();
//        }
//        catch(InstantiationException | IllegalAccessException | ClassNotFoundException ex){
//            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
