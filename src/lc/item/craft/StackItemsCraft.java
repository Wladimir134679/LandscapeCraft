package lc.item.craft;

import java.util.ArrayList;
import lc.item.Item;
import lc.item.StackItems;

/**
 * @author Death
 */
public class StackItemsCraft{
    
    public Item item;
    public ArrayList<StackItems> origs;

    public StackItemsCraft(Item it){
        this.item = it;
        origs = new ArrayList<>();
    }
    public void addStack(StackItems si){
        if(item.id == si.item.id)
            origs.add(si);
    }
    public int getNum(){
        int num = 0;
        for(StackItems s : origs){
            num += s.num;
        }
        return num;
    }

    public void minus(int i){
        for(StackItems s : origs){
            if(i < s.num){
                s.num -= i;
                return;
            }else{
                i -= s.num;
                s.num = 0;
            }
        }
    }
}
