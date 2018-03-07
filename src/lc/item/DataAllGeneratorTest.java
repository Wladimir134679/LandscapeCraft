package lc.item;

import lc.item.block.AnimTextureBlock;
import lc.item.block.Block;
import lc.item.block.BlockChest;
import lc.item.block.BlockCraftingTable;
import lc.item.block.BlockFoliage;
import lc.item.block.BlockFurnace;
import lc.item.block.BlockGrass;
import lc.item.block.BlockPlantGrass;
import lc.item.block.BlockSeedling;
import lc.item.block.MaterialBlock;
import lc.item.block.TypeBlock;
import lc.item.craft.Recipe;
import lc.utils.Textures;

/**
 * @author Death
 */
public class DataAllGeneratorTest {

    public static void gen(){
        genWoodChest();
        genTreeOak();
        genTorch();
        genToolWoodShovel();
        genToolWoodPixkaxe();
        genToolWoodAxe();
        genToolStoneShovel();
        genToolStonePixkaxe();
        genToolStoneAxe();
        genToolIronShovel();
        genToolIronPixkaxe();
        genToolIronAxe();
        genTexture();
        genStoneFurnace();
        genStone();
        genSeedlingOak();
        genSeedPlantGrass();
        genPlanksOak();
        genPlantGrass();
        genIronOre();
        genIronIngot();
        genGrass();
        genFoliageOak();
        genDirst();
        genCraftTable();
        genCharcoal();
        genMat();
        genTexture();
    }
    
    private static void genTexture(){
        DataAll.get().textures = new Textures();
        Textures tex = DataAll.get().textures;
        
        tex.nameFile = "Items.png";
        tex.names.add("dirst");
        tex.names.add("grass");
        tex.names.add("stone");
        tex.names.add("tree_oak");
        tex.names.add("foliage_oak");
        tex.names.add("seedling_oak");
        tex.names.add("crafting_table");
        tex.names.add("planks_oak");
        tex.names.add("plant_grass");
        tex.names.add("iron_ore");
        tex.names.add("stone_furnace_off");
        tex.names.add("stone_furnace_on");
        tex.names.add("wood_chest");
        tex.names.add("wood_pixkaxe");
        tex.names.add("wood_axe");
        tex.names.add("wood_shovel");
        tex.names.add("stone_pixkaxe");
        tex.names.add("stone_axe");
        tex.names.add("stone_shovel");
        tex.names.add("seed_plant_grass");
        tex.names.add("iron_ingot");
        tex.names.add("iron_pixkaxe");
        tex.names.add("iron_axe");
        tex.names.add("iron_shovel");
        tex.names.add("charcoal");
        tex.names.add("torch1");
        tex.names.add("torch2");
        tex.names.add("torch3");
        tex.names.add("torch4");
        tex.names.add("torch5");
        tex.strSize = "32:32";
    }
    
    private static void genGrass(){
        BlockGrass bl1 = new BlockGrass();
        bl1.density = 0.5f;
        bl1.animTexBlock = null;
        bl1.id = 0;
        bl1.item = "dirst";
        bl1.nameKod = "grass";
        bl1.namesMaterial.add("grass");
        bl1.propertyBlock.set("plantGrass", "plant_grass");
        bl1.propertyBlock.set("dirstBlock", "dirst");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Recipe re1 = new Recipe();
        re1.tool = "this";
        re1.numResult = 1;
        re1.component = new String[2];
        re1.numCom = new int[2];
        re1.component[0] = "dirst";
        re1.numCom[0] = 2;
        re1.component[1] = "seed_plant_grass";
        re1.numCom[1] = 2;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 0;
        it1.nameKod = "grass";
        it1.nameEN = "Трава";
        it1.nameRU = "Grass";
        it1.infaRU = "Тут такая вот информация на русском языке, которую не всем понять =)";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "grass";
        it1.recipe = re1;
        
        re1.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genDirst(){
        Block bl1 = new Block();
        bl1.density = 0.45f;
        bl1.animTexBlock = null;
        bl1.id = 1;
        bl1.item = "dirst";
        bl1.nameKod = "dirst";
        bl1.namesMaterial.add("dirst");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 1;
        it1.nameKod = "dirst";
        it1.nameEN = "Земля";
        it1.nameRU = "Dirst";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "dirst";
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genStone(){
        Block bl1 = new Block();
        bl1.density = 1f;
        bl1.animTexBlock = null;
        bl1.id = 2;
        bl1.item = "stone";
        bl1.nameKod = "stone";
        bl1.namesMaterial.add("stone");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 2;
        it1.nameKod = "stone";
        it1.nameEN = "Stone";
        it1.nameRU = "Камень";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 32;
        it1.nameTexture = "stone";
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genTreeOak(){
        Block bl1 = new Block();
        bl1.density = 1f;
        bl1.animTexBlock = null;
        bl1.id = 3;
        bl1.item = "tree_oak";
        bl1.nameKod = "tree_oak";
        bl1.namesMaterial.add("tree");
        bl1.numItem = 1;
        bl1.type = TypeBlock.PASSABLE;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 3;
        it1.nameKod = "tree_oak";
        it1.nameEN = "Oak";
        it1.nameRU = "Дуб";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 32;
        it1.nameTexture = "tree_oak";
        it1.namesMaterial.add("fuel");
        it1.namesMaterial.add("tree");
        it1.property.set("numMelt", 3);
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genFoliageOak(){
        BlockFoliage bl1 = new BlockFoliage();
        bl1.density = 0.1f;
        bl1.animTexBlock = null;
        bl1.id = 4;
        bl1.item = "seedling_oak";
        bl1.nameKod = "foliage_oak";
        bl1.namesMaterial.add("foliage");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 4;
        it1.nameKod = "foliage_oak";
        it1.nameEN = "Foliage Oak";
        it1.nameRU = "Листва Дуба";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "foliage_oak";
        it1.namesMaterial.add("foliage");
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genSeedlingOak(){
        BlockSeedling bl1 = new BlockSeedling();
        bl1.density = 0.2f;
        bl1.animTexBlock = null;
        bl1.id = 5;
        bl1.item = "seedling_oak";
        bl1.nameKod = "seedling_oak";
        bl1.namesMaterial.add("plant");
        bl1.propertyBlock.set("nameTree", "tree_oak");
        bl1.propertyBlock.set("nameFoliage", "foliage_oak");
        bl1.numItem = 1;
        bl1.type = TypeBlock.PASSABLE;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 5;
        it1.nameKod = "seedling_oak";
        it1.nameEN = "Seedling Oak";
        it1.nameRU = "Саженец Дуба";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "seedling_oak";
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genCraftTable(){
        BlockCraftingTable bl1 = new BlockCraftingTable();
        bl1.density = 0.2f;
        bl1.animTexBlock = null;
        bl1.id = 6;
        bl1.item = "crafting_table";
        bl1.nameKod = "crafting_table";
        bl1.namesMaterial.add("tree");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Recipe rec = new Recipe();
        rec.numResult = 1;
        rec.tool = "this";
        rec.component = new String[1];
        rec.numCom = new int[1];
        rec.component[0] = "planks_oak";
        rec.numCom[0] = 5;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 6;
        it1.nameKod = "crafting_table";
        it1.nameEN = "CraftingTable";
        it1.nameRU = "Верстак";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "crafting_table";
        it1.recipe = rec;
        rec.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genPlanksOak(){
        Block bl1 = new Block();
        bl1.density = 0.5f;
        bl1.animTexBlock = null;
        bl1.id = 7;
        bl1.item = "planks_oak";
        bl1.nameKod = "planks_oak";
        bl1.namesMaterial.add("tree");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Recipe rec = new Recipe();
        rec.numResult = 2;
        rec.tool = "this";
        rec.component = new String[1];
        rec.numCom = new int[1];
        rec.component[0] = "tree_oak";
        rec.numCom[0] = 1;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 7;
        it1.nameKod = "planks_oak";
        it1.nameEN = "Planks Oak";
        it1.nameRU = "Дубовые Доски";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "planks_oak";
        it1.recipe = rec;
        rec.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genPlantGrass(){
        BlockPlantGrass bl1 = new BlockPlantGrass();
        bl1.density = 0.1f;
        bl1.animTexBlock = null;
        bl1.id = 8;
        bl1.item = "seed_plant_grass";
        bl1.nameKod = "plant_grass";
        bl1.numItem = 1;
        bl1.namesMaterial.add("plant");
        bl1.type = TypeBlock.PASSABLE;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 8;
        it1.nameKod = "plant_grass";
        it1.nameEN = "Big Grass";
        it1.nameRU = "Высокая Трава";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "plant_grass";
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genSeedPlantGrass(){
        Item it1 = new Item();
        it1.block = null;
        it1.blockBet = "plant_grass";
        it1.id = 9;
        it1.nameKod = "seed_plant_grass";
        it1.nameEN = "Seed Big Grass";
        it1.nameRU = "Семя Высокой Травы";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "seed_plant_grass";
        it1.recipe = null;
        
        DataAll.get().addItem(it1);
    }
    
    private static void genTorch(){
        Block bl1 = new Block();
        bl1.density = 0.5f;
        bl1.animTexBlock = new AnimTextureBlock();
        bl1.animTexBlock.nameTexs = new String[]{"torch1", "torch2", "torch3", "torch4", "torch5"};
        bl1.animTexBlock.pause = 100;
        bl1.id = 10;
        bl1.item = "torch";
        bl1.nameKod = "torch";
        bl1.numItem = 1;
        bl1.namesMaterial.add("torch");
        bl1.type = TypeBlock.PASSABLE;
        
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.component[0] = "planks_oak";
        re.component[1] = "charcoal";
        re.numCom[0] = 2;
        re.numCom[1] = 1;
        re.numResult = 1;
        re.tool = "this";
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 10;
        it1.nameKod = "torch";
        it1.nameEN = "Torch";
        it1.nameRU = "Факел";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "torch1";
        it1.recipe = re;
        re.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genIronOre(){
        Block bl1 = new Block();
        bl1.density = 0.5f;
        bl1.animTexBlock = null;
        bl1.id = 11;
        bl1.item = "iron_ore";
        bl1.nameKod = "iron_ore";
        bl1.namesMaterial.add("ore_1");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 11;
        it1.nameKod = "iron_ore";
        it1.nameEN = "Iron Ore";
        it1.nameRU = "Железная руда";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "iron_ore";
        it1.recipe = null;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genIronIngot(){
        Recipe re = new Recipe();
        re.component = new String[1];
        re.numCom = new int[1];
        re.component[0] = "iron_ore";
        re.numCom[0] = 1;
        re.numResult = 1;
        re.tool = "stone_furnace";
        
        Item it1 = new Item();
        it1.block = null;
        it1.id = 12;
        it1.nameKod = "iron_ingot";
        it1.nameEN = "Iron Ingot";
        it1.nameRU = "Железный слиток";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "iron_ingot";
        it1.recipe = re;
        re.itemRezult = it1;
        
        DataAll.get().addItem(it1);
    }
    
    private static void genStoneFurnace(){
        BlockFurnace bl1 = new BlockFurnace();
        bl1.density = 0.5f;
        bl1.animTexBlock = null;
        bl1.id = 13;
        bl1.item = "stone_furnace";
        bl1.nameKod = "stone_furnace";
        bl1.namesMaterial.add("stone");
        bl1.propertyBlock.set("nameTexture", "stone_furnace");
        bl1.propertyBlock.set("timeMelt", "2000");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Recipe re = new Recipe();
        re.component = new String[1];
        re.component[0] = "stone";
        re.numCom = new int[1];
        re.numCom[0] = 10;
        re.numResult = 1;
        re.tool = "crafting_table";
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 13;
        it1.nameKod = "stone_furnace";
        it1.nameEN = "Stone Furnace";
        it1.nameRU = "Каменная печь";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "stone_furnace_off";
        it1.recipe = re;
        re.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genWoodChest(){
        BlockChest bl1 = new BlockChest();
        bl1.density = 0.1f;
        bl1.animTexBlock = null;
        bl1.id = 14;
        bl1.item = "crafting_table";
        bl1.nameKod = "crafting_table";
        bl1.namesMaterial.add("tree");
        bl1.propertyBlock.set("numRow", "3");
        bl1.numItem = 1;
        bl1.type = TypeBlock.SOLID;
        
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "tree_oak";
        re.component[1] = "planks_oak";
        re.numCom[0] = 5;
        re.numCom[1] = 1;
        
        Item it1 = new Item();
        it1.block = bl1;
        it1.id = 14;
        it1.nameKod = "wood_chest";
        it1.nameEN = "Wood chest";
        it1.nameRU = "Деревянный сундук";
        it1.infaRU = "Клади вещи!";
        it1.infaEN = "";
        it1.maxNum = 16;
        it1.nameTexture = "wood_chest";
        it1.recipe = re;
        re.itemRezult = it1;
        
        DataAll.get().addBlock(bl1);
        DataAll.get().addItem(it1);
    }
    
    private static void genCharcoal(){
        Recipe re = new Recipe();
        re.component = new String[1];
        re.numCom = new int[1];
        re.tool = "stone_furnace";
        re.numResult = 1;
        re.component[0] = "tree_oak";
        re.numCom[0] = 1;
        
        Item it1 = new Item();
        it1.block = null;
        it1.id = 15;
        it1.nameKod = "charcoal";
        it1.nameEN = "Charcoal";
        it1.nameRU = "Древестный уголь";
        it1.infaRU = "";
        it1.infaEN = "";
        it1.maxNum = 99;
        it1.nameTexture = "charcoal";
        it1.namesMaterial.add("fuel");
        it1.property.set("numMelt", "1");
        it1.recipe = re;
        re.itemRezult = it1;
        
        DataAll.get().addItem(it1);
    }
    
    private static void genToolWoodPixkaxe(){
        Recipe re = new Recipe();
        re.component = new String[1];
        re.numCom = new int[1];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 10;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 16;
        it.maxNum = 1;
        it.nameTexture = "wood_pixkaxe";
        it.nameKod = "wood_pixkaxe";
        it.nameRU = "Деревянная кирка";
        it.nameEN = "Wood Pixkaxe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.5f;
        it.crashType.add("stone");
        it.id = 100;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolWoodAxe(){
        Recipe re = new Recipe();
        re.component = new String[1];
        re.numCom = new int[1];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 15;
        it.maxNum = 1;
        it.nameTexture = "wood_axe";
        it.nameKod = "wood_axe";
        it.nameRU = "Деревянный Топор";
        it.nameEN = "Wood Axe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.5f;
        it.crashType.add("tree");
        it.id = 101;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolWoodShovel(){
        Recipe re = new Recipe();
        re.component = new String[1];
        re.numCom = new int[1];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 16;
        it.maxNum = 1;
        it.nameTexture = "wood_shovel";
        it.nameKod = "wood_shovel";
        it.nameRU = "Деревянная Лопата";
        it.nameEN = "Wood Shovel";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.5f;
        it.crashType.add("dirst");
        it.crashType.add("grass");
        it.id = 102;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolStonePixkaxe(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "stone";
        re.numCom[1] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 30;
        it.maxNum = 1;
        it.nameTexture = "stone_pixkaxe";
        it.nameKod = "stone_pixkaxe";
        it.nameRU = "Каменная кирка";
        it.nameEN = "Stone Pixkaxe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.75f;
        it.crashType.add("stone");
        it.crashType.add("ore_1");
        it.id = 103;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolStoneAxe(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "stone";
        re.numCom[1] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 30;
        it.maxNum = 1;
        it.nameTexture = "stone_axe";
        it.nameKod = "stone_axe";
        it.nameRU = "Каменный Топор";
        it.nameEN = "Stone Axe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.75f;
        it.crashType.add("tree");
        it.id = 104;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolStoneShovel(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "stone";
        re.numCom[1] = 2;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 30;
        it.maxNum = 1;
        it.nameTexture = "stone_shovel";
        it.nameKod = "stone_shovel";
        it.nameRU = "Каменная Лопата";
        it.nameEN = "Stone Shovel";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.75f;
        it.crashType.add("dirst");
        it.crashType.add("grass");
        it.id = 105;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolIronPixkaxe(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "iron_ingot";
        re.numCom[1] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 50;
        it.maxNum = 1;
        it.nameTexture = "iron_pixkaxe";
        it.nameKod = "iron_pixkaxe";
        it.nameRU = "Железная кирка";
        it.nameEN = "Iron Pixkaxe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.9f;
        it.crashType.add("stone");
        it.crashType.add("ore_1");
        it.id = 106;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolIronAxe(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "iron_ingot";
        re.numCom[1] = 5;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 50;
        it.maxNum = 1;
        it.nameTexture = "iron_axe";
        it.nameKod = "iron_axe";
        it.nameRU = "Железная Топор";
        it.nameEN = "Iron Axe";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.9f;
        it.crashType.add("tree");
        it.id = 107;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static void genToolIronShovel(){
        Recipe re = new Recipe();
        re.component = new String[2];
        re.numCom = new int[2];
        re.tool = "crafting_table";
        re.numResult = 1;
        re.component[0] = "planks_oak";
        re.numCom[0] = 5;
        re.component[1] = "iron_ingot";
        re.numCom[1] = 2;
        
        ItemTool it = new ItemTool();
        it.strengthMax = 50;
        it.maxNum = 1;
        it.nameTexture = "iron_shovel";
        it.nameKod = "iron_shovel";
        it.nameRU = "Железная Лопата";
        it.nameEN = "Iron Shovel";
        it.infaRU = "";
        it.infaEN = "";
        it.force = 0.9f;
        it.crashType.add("dirst");
        it.crashType.add("grass");
        it.id = 108;
        it.recipe = re;
        re.itemRezult = it;
        DataAll.get().addItem(it);
    }
    
    private static MaterialItem genMatItem(String name){
        MaterialItem matItem = new MaterialItem();
        matItem.name = name;
        DataAll.get().addMaterialItem(matItem);
        return matItem;
    }
    
    private static MaterialBlock getMatBlock(String name, boolean pc){
        MaterialBlock mat = new MaterialBlock();
        mat.name = name;
        mat.isPlayerCrach = pc;
        DataAll.get().addMaterialBlock(mat);
        return mat;
    }
    
    private static void genMat(){
        MaterialItem mat = null;
        MaterialBlock mb = null;
        mat = genMatItem("fuel");
        mat = genMatItem("tree");
        
        mb = getMatBlock("stone", false);
        mb = getMatBlock("dirst", true);
        mb = getMatBlock("torch", true);
        mb.property.set("layerLight", "10");
        mb = getMatBlock("tree", true);
        mb = getMatBlock("plant", true);
        mb = getMatBlock("grass", true);
        mb = getMatBlock("ore_1", false);
        mb = getMatBlock("other", true);
        mb = getMatBlock("foliage", true);
    }
}
