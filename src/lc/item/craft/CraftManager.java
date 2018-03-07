package lc.item.craft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import lc.item.StackItems;

/**
 * @author Death
 */
public class CraftManager {
    
    public static HashMap<String, ArrayList<Recipe>> recipe;
    
    public static void init(){
        recipe = new HashMap<>();
    }
    
    public static void addRecipe(Recipe re){
        if(recipe.get(re.tool) == null){
            ArrayList<Recipe> res = new ArrayList<>();
            recipe.put(re.tool, res);
        }
        recipe.get(re.tool).add(re);
    }
    
//    public void addRecipte(Item it, JSONObject recipte){
//        String com = recipte.getString("components");
//        String nums = recipte.getString("nums");
//        String tool = "this";
//        if(!recipte.isNull("tool")) tool = recipte.getString("tool");
//        
//        Recipe rec = new Recipe(it);
//        rec.load(com, nums);
//        rec.tool = tool;
//        if(!recipte.isNull("numResult")) rec.numResult = recipte.getInt("numResult");
//        it.recipe = rec;
//        
//        if(recipe.get(tool) == null){
//            recipe.put(tool, new ArrayList<Recipe>());
//        }
//        ((ArrayList<Recipe>)recipe.get(tool)).add(rec);
//    }
    
    public static Recipe getRecipe(StackItems si, String tool){
        ArrayList<Recipe> recs = getAllRecipe();
        for(Recipe re : recs){
            if(re.component.length == 1){
                if(re.component[0].equals(si.item.nameKod) && re.tool.equals(tool)){
                    return re;
                }
            }
        }
        return null;
    }
    
    public static ArrayList<Recipe> getAllRecipe(){
        ArrayList<Recipe> recs = new ArrayList<>();
        Iterator<String> str = recipe.keySet().iterator();
        for(int i = 0; i < recipe.keySet().size(); i++){
            recs.addAll(recipe.get(str.next()));
        }
        return recs;
    }
    
    public static String[] getTools(){
        Iterator<String> str = recipe.keySet().iterator();
        String[] ret = new String[recipe.keySet().size()];
        for(int i = 0; i < recipe.keySet().size(); i++){
            ret[i] = str.next();
        }
        return ret;
    }
    
    public static ArrayList<Recipe> getRecipes(String tool){
        return recipe.get(tool);
    }
    
    
    public static boolean isCraftMax(Recipe re, StackItems[] items){
        StackItemsCraft[] crafts = getStacksCraft(items);
        return isCraft(re, crafts, maxNumCraft(re, crafts));
    }
    public static boolean isCraft(Recipe re, StackItems[] items, int num){
        return isCraft(re, getStacksCraft(items), num);
    }
    public static boolean isCraft(Recipe re, StackItemsCraft[] crafts, int num){
        boolean isResultCraft = true;
        boolean[] isCraft = new boolean[re.component.length];
        for(int i = 0; i < re.component.length; i++)
            isCraft[i] = false;
        
        for(StackItemsCraft sic : crafts){
            for(int i = 0; i < re.component.length; i++){
                if(!isCraft[i] && sic.item.nameKod.equals(re.component[i])){
                    if(sic.getNum() - re.numCom[i] * num >= 0){
                        isCraft[i] = true;
                    }
                }
            }
        }
        
        for(int i = 0; i < isCraft.length; i++){
            isResultCraft = isResultCraft && isCraft[i];
        }
        
        return isResultCraft;
    }
    
    public static int maxNumCraft(Recipe re, StackItemsCraft[] items){
        int num = Integer.MAX_VALUE;
        
        int numComMin = 0;
        
        for(StackItemsCraft sic : items){
            for(int i = 0; i < re.component.length; i++){
                if(sic.item.nameKod.equals(re.component[i])){
                    numComMin = sic.getNum() / re.numCom[i];
                    if(numComMin < num)
                        num = numComMin;
                }
            }
        }
        
        return num;
    }
    public static StackItems craftMax(Recipe re, StackItems resultStack, StackItems[] si){
        return craft(re, resultStack, si, maxNumCraft(re, getStacksCraft(si)));
    }
    
    public static StackItems craft(Recipe re, StackItems resultStack, StackItems[] si, int num){
        boolean[] result = new boolean[re.component.length];
        if(resultStack == null){
            resultStack = new StackItems(re.itemRezult);
            resultStack.setNum(0);
        }else{
            if(resultStack.item.id != re.itemRezult.id)
                return resultStack;
        }
        for(int i = 0; i < result.length; i++)
            result[i] = false;
        StackItemsCraft[] crafts = getStacksCraft(si);
        for(StackItemsCraft sic : crafts){
            for(int i = 0; i < re.component.length; i++){
                if(!result[i] && sic.item.nameKod.equals(re.component[i])){
                    if(sic.getNum() - re.numCom[i] * num >= 0){
                        sic.minus(re.numCom[i] * num);
                        result[i] = true;
                    }
                }
            }
        }

        boolean isResultCraft = true;
        for(int i = 0; i < result.length; i++){
            isResultCraft = isResultCraft && result[i];
        }
        if(isResultCraft){
            resultStack.num += num * re.numResult;
        }else{
            return null;
        }
        return resultStack;
    }
    
    public static StackItemsCraft[] getStacksCraft(StackItems[] items){
        HashMap<Integer, StackItemsCraft> crafts = new HashMap<>();
        for(StackItems si : items){
            if(si == null) continue;
            if(crafts.get(si.item.id) == null){
                StackItemsCraft sic = new StackItemsCraft(si.item);
                sic.addStack(si);
                crafts.put(si.item.id, sic);
            }else{
                crafts.get(si.item.id).addStack(si);
            }
        }
        StackItemsCraft[] result = new StackItemsCraft[crafts.size()];
        ArrayList<StackItemsCraft> it = new ArrayList<>(crafts.values());
        for(int i = 0; i < it.size(); i++){
            result[i] = it.get(i);
        }
        return result;
    }
}
