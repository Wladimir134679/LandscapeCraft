package lc.utils;

/**
 * @author Death
 */
public class LoadDataItems {
//
//    public static void load(FileHandle file){
//        Item.ids = new HashMap<>();
//        Item.items = new HashMap<>();
//        Block.ids = new HashMap<>();
//        Block.blocks = new HashMap<>();
//        CraftManager.load();
//        
//        JSONObject jsonSource = new JSONObject(file.readString());
//        
//        Item.texture = Textures.loadJson(file, jsonSource.getJSONObject("textureItem"));
//        
//        MaterialBlock.load(file, jsonSource.getJSONObject("materialsBlock"));
//        MaterialItem.load(file, jsonSource.getJSONObject("materialsItem"));
//        
//        JSONArray jsonItems = jsonSource.getJSONArray("items");
//        parseItems(jsonItems);
//    }
//    
//    private static void loadFile(JSONObject fileJSONObject){
//        FileHandle file;
//        if(fileJSONObject.getBoolean("local"))
//            file = Gdx.files.local(fileJSONObject.getString("fileItems"));
//        else
//            file = Gdx.files.internal(fileJSONObject.getString("fileItems"));
//        parseItems(new JSONArray(file.readString()));
//    }
//    
//    private static void parseItems(JSONArray source){
//        for(int i = 0; i < source.length(); i++){
//            JSONObject itemInfa = source.getJSONObject(i);
//            
//            if(!itemInfa.isNull("fileItems")){
//                loadFile(itemInfa);
//            }else{
//                ItemsLoad.parseJson(itemInfa);
//            }
//        }
//    }
}
