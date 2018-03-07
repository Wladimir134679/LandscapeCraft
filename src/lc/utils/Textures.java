package lc.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Death
 */
public class Textures {
    
    public static void init(GsonBuilder gson){
        gson.registerTypeAdapter(Textures.class, new Parser());
    }
    
    public HashMap<String, Sprite> tex;
    
    public ArrayList<String> names;
    public String strSize;
    public String nameFile;

    public Textures(){
        tex = new HashMap<>();
        names = new ArrayList<>();
    }
    
    public void addTextures(Textures tex1){
        this.tex.putAll(tex1.tex);
    }
    public void load(FileHandle path){
        int[] size = new int[2];
        TextureRegion[][] regs;
        int indexName;
        String nameTexture;
        Texture texture = new Texture(path.child(nameFile));
        String[] strSizeAr = strSize.split(":");
        size[0] = Integer.parseInt(strSizeAr[0]);
        size[1] = Integer.parseInt(strSizeAr[1]);
        regs = TextureRegion.split(texture, size[0], size[1]);
        indexName = 0;
        for(TextureRegion[] regsTex : regs){
            for(TextureRegion textureRegion : regsTex){
                nameTexture = "nullName";
                if(indexName < names.size())
                    nameTexture = names.get(indexName++);
                Sprite sp = new Sprite(textureRegion);
                tex.put(nameTexture, sp);
            }
        }
    }
    
    public Sprite get(String name){
        return tex.get(name);
    }
    
    public void removeData(){
        names = null;
        strSize = null;
    }
    
    public static class Parser implements JsonSerializer<Textures>, JsonDeserializer<Textures>{

        @Override
        public JsonElement serialize(Textures t, Type type, JsonSerializationContext jsc){
            JsonObject jo = new JsonObject();
            jo.addProperty("size", t.strSize);
            jo.addProperty("nameFile", t.nameFile);
            JsonArray arrNames = new JsonArray();
            for(String name : t.names)
                arrNames.add(name);
            jo.add("names", arrNames);
            return jo;
        }

        @Override
        public Textures deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException{
            Textures tex = new Textures();
            JsonObject jo = (JsonObject) je;
            tex.strSize = jo.get("size").getAsString();
            tex.nameFile = jo.get("nameFile").getAsString();
            JsonArray arrNames = jo.getAsJsonArray("names");
            for(int i = 0; i < arrNames.size(); i++){
                tex.names.add(arrNames.get(i).getAsString());
            }
            return tex;
        }
        
    }
}
