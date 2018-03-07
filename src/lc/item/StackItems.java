package lc.item;

import java.nio.ByteBuffer;

/**
 * @author Death
 */
public class StackItems {

    public static enum TypeStack{
        TOOL(0),
        ITEMS(1);
        
        public int id;

        private TypeStack(int id){
            this.id = id;
        }
    }
    
    public static byte[] getData(StackItems si){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        
        buf.putInt(si.item.id);
        if(si.type == TypeStack.TOOL)
            buf.putInt(si.strngthTool);
        buf.putInt(si.max);
        buf.putInt(si.num);
        buf.putInt(si.name.getBytes().length);
        buf.put(si.name.getBytes());
        
        return buf.array();
    }
    
    public static StackItems setData(byte[] data){
        ByteBuffer buf = ByteBuffer.wrap(data);
        
        StackItems si = new StackItems(DataAll.get().items.get(buf.getInt()));
        if(si.type == TypeStack.TOOL){
            si.strngthTool = buf.getInt();
        }
        si.max = buf.getInt();
        si.num = buf.getInt();
        byte[] strName = new byte[buf.getInt()];
        buf.get(strName);
        si.name = new String(strName);
        
        return si;
    }
    
    public Item item;
    public int num;
    public int max;
    public String name;
    public TypeStack type;
    public int strngthTool;

    public StackItems(Item item){
        if(item == null){
            System.err.println("ERROR! StackItems.Constructor Item == null");
            return;
        }
        this.strngthTool = 1;
        this.num = 1;
        if(item instanceof ItemTool){
            type = TypeStack.TOOL;
            this.max = 1;
            this.strngthTool = ((ItemTool)item).strengthMax;
        }else{
            type = TypeStack.ITEMS;
            this.max = item.maxNum;
        }
        this.name = item.nameKod;
        this.item = item;
    }
    
    public boolean addItem(Item item){
        if(item.id != this.item.id) return false;
        if(num + 1 >= max) return false;
        num++;
        return true;
    }

    public boolean equals(StackItems obj){
        return obj.item.id == this.item.id;
    }
    
    public boolean addStackAll(StackItems si){
        return addStack(si, si.num);
    }
    
    public boolean addStack(StackItems si, int numSi){
        if(si.num < numSi) return false;
        if(!this.equals(si)) return false;
        if(num >= max) return false;
        if(num + numSi > max) return false;
        
        if(num + numSi <= max){
            num += numSi;
            si.num -= numSi;
        }else{
            int os = (num + numSi) - max;
            num = max;
            si.num -= os;
        }
        return true;
    }
    
    public boolean isEmpty(){
        if(num < 0) num = 0;
        if(strngthTool < 0) strngthTool = 0;
        return num == 0 || strngthTool == 0;
    }
    
    public boolean isFull(){
        if(num > max) num = max;
        return num == max;
    }
    
    public int getNum(){
        return num;
    }
    
    public boolean setNum(int i){
        this.num = i;
        if(num > max){
            num = max;
            return false;
        }
        return true;
    }
    
    public boolean sum(int s){
        if(num + s > max) return false;
        num += s;
        return true;
    }
    
    public boolean crash(){
        this.strngthTool -= 1;
        return this.isEmpty();
    }
    
    public boolean isTool(){
        return type == TypeStack.TOOL;
    }
}
