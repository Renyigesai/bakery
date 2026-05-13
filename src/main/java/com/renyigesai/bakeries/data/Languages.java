package com.renyigesai.bakeries.data;

import com.renyigesai.bakeries.BakeriesMod;
import com.renyigesai.bakeries.api.annotation.ItemData;
import com.renyigesai.bakeries.common.init.BakeriesCreativeModeTabs;
import com.renyigesai.bakeries.common.init.BakeriesItems;
import com.renyigesai.bakeries.common.init.BakeriesMobEffects;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.weibai.rcglib.utils.UtilTranslatable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class Languages extends AbstractLanguageProvider {
    private final PackOutput output;
    private final String locale;

    private static final String ADVANCEMENT = "advancements.bakeries.";
    private static final String CATEGORIES = "guide.bakeries.categories.";
    private static final String PATCHOULI_ENTRIES = "guide.bakeries.entries.";
    private static final String PATCHOULI_DESCR = "guide.bakeries.descr.";

    public Languages(PackOutput output, String locale) {
        super(output, locale);
        this.output = output;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        addCreativeModeTabs();
        try {
            addItems();
        } catch (IllegalAccessException e) {
            LOGGER.error("Failed to access item fields", e);
            throw new RuntimeException("Failed to access item fields during language generation", e);
        }
        addBlocks();
        addFluidTypes();
        addElements();
        add();
        addEffects();
        addEntity();
        addAdvancements();
        addPatchoulis();

        // PonderPlugin
        addPonders();
    }


    private void add() {
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "row_item_temperature"), "Min %s °c", "Min %s °c");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven"), "Oven", "烤箱");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.temperature"), "Current temperature", "当前温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "oven.rolling"), "Scroll the middle mouse to adjust the temperature.", "滚动鼠标中键调节温度");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "blender"), "Blender", "搅拌机");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "dough_crafting_table"), "Dough Crafting Table", "面胚制作台");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "cupboard"), "Cupboard", "厨台");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "bread_knife"), "Bread Knife", "面包刀");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "flour_sieve"), "Flour Sieve", "面粉筛");
        add(UtilTranslatable.setContainer(BakeriesMod.MODID, "drink"), "Drink", "饮料");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "bread_knife"), "When using  cut the object pointed by the target.", "使用时切开准星所指的物品");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "wood_counter"), "Use Bowl to Change State", "使用碗右键方块以改变状态");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_0"), "Sift the item in the main hand while holding it off hand", "拿在副手时过筛主手的物品");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_1"), "What are you doing?", "你在干什么?");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_2"), "You can't sift it!", "筛不了的啦!");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "flour_sieve_3"), "I don't have the power, you know?", "我没这个能力知道吧?");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "pile_item_perfect"), "Perfect Temperature", "完美温度");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "repeat_eat_item_eat"), "Eat", "吃");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "repeat_eat_item_drink"), "Drink", "喝");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "eternal_baguette"), "Impose a forced knock back and slow effect on Entity", "对实体施加强制击退和缓慢效果");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "toaster_0"), "Right-click with your left hand to start baking", "空手右键以开始烤制");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "toaster_1"), "%s and right-click to retrieve the item", "%s右键以取出物品");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "mould"), "When the secondary weapon has a Knife long-pressing the right button can disengage it", "副手存在刀时长按右键可脱模");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "shake"), "Right shake", "右键以摇晃");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "drink"), "The Enjoy effect can be enhanced to a %s", "可提升享受效果到 %s 级");
        add(UtilTranslatable.setTooltips(BakeriesMod.MODID, "player_logged_in"), "§6[Bakeries]§fInstall Patchouli to obtain the mod tutorial book", "§6【烘焙坊】§f安装帕秋莉手册以获得模组教程书");
        add("bakeries.book.name","Baking Guide","烘焙教科书");
        add("key.bakeries.bakeries","Bakeries","烘焙坊");
        add("key.bakeries.auxiliary","Auxiliary","辅助按键");
    }

    private void addCreativeModeTabs() {
        add("item_group.bakeries.bakeries_tab","Bakeries","烘焙坊");
        add("item_group.bakeries.bakeries_sfp_tab","Bakeries Sfp","烘焙坊 半成品");
        add("item_group.bakeries.bakeries_compat_tab","Bakeries Compat","烘焙坊 联动物品");
    }

    private void addElements() {
    }

    private void addEffects() {
        addEffect(BakeriesMobEffects.CHEESE_POWER::value, "Cheese Power", "芝士力");
        addEffect(BakeriesMobEffects.COCOA_MANIA::value, "Cocoa Mania", "可可狂热");
        addEffect(BakeriesMobEffects.SOFT::value, "Soft", "柔软");
        addEffect(BakeriesMobEffects.ENJOY::value, "Enjoy", "享受");
        add("effect.bakeries.enjoy.description","When you have this effect, you will be immune to all negative effects and increase the speed of blood recovery.","拥有此效果时，免疫所有负面效果，提升回血速度。");
        add("effect.bakeries.cocoa_mania.description","Ignore the damage caused by the entity's invincible time.","无视生物无敌帧造成伤害。");
        add("effect.bakeries.cheese_power.description","Enhance the basic attack power.","提升基础攻击力。");
        add("effect.bakeries.soft.description","Increase armor value and repel resistance, and bombard the attacker entity.","提升护甲值和击退抗性，弹飞攻击者实体。");
    }

    private void addItems() throws IllegalAccessException {
        Class<BakeriesItems> _class = BakeriesItems.class;
        for (Field field : _class.getDeclaredFields()) {
            if (field.isAnnotationPresent(ItemData.class)) {
                Object object = field.get(null);
                DeferredItem<?> deferredItem = null;
                if (object instanceof DeferredItem<?>) {
                    deferredItem = (DeferredItem<?>) object;
                }
                if (deferredItem != null) {
                    ItemData itemData = field.getAnnotation(ItemData.class);
                    if (itemData != null) {
                        String zh = itemData.zhCn();
                        String en = itemData.enUs();
                        if (itemData.itemType() == ItemData.ItemType.ITEM) {
                            if (en.isEmpty()) {
                                addItem(deferredItem, zh);
                            } else {
                                addItem(deferredItem, en, zh);
                            }
                        }
                        if (itemData.itemType() == ItemData.ItemType.BLOCK) {
                            Item item = deferredItem.get();
                            if (item instanceof BlockItem blockItem) {
                                if (en.isEmpty()){
                                    addBlock(blockItem::getBlock, zh);
                                }else {
                                    addBlock(blockItem::getBlock, en, zh);
                                }
                            } else {
                                throw new IllegalStateException("Field <<<" + field.getName() + ">>> is annotated as BLOCK but is not a BlockItem!");
                            }
                        }
                    }
                }
            }
        }
    }

    private void addBlocks(){
        add("block.bakeries.salt_water_block","Salt Water","盐水");

    }

    private void addFluidTypes(){
        add("fluid_type.bakeries.salt_water","Salt Water","盐水");
    }

    private void addEntity(){
        add("entity.minecraft.villager.bakeries.pistrinamaster","Pistrina Master","面包师");
    }

    private void addAdvancements(){
        // 根进度
        addAdvancement("root", translateText("Bakery", "烘焙坊"), translateText("Fine baking.", "精致烘焙。"));
        addAdvancement("sieving", translateText("Sieving", "唰唰唰!!"), translateText("Get a flour sieve.", "获得一个面粉筛。"));
        addAdvancement("start_the_experiment", translateText("Start The Experiment", "开始实验吧"), translateText("Get a Fermentation Tank.", "获得一个发酵罐"));
        addAdvancement("natural_activity", translateText("Natural Activity", "天然活性"), translateText("Use the bottle to scoop out the natural yeast from the fermenter.", "用瓶子舀出发酵罐里的天然酵母。"));
        addAdvancement("precise_temperature_control", translateText("Precise Temperature Control", "精确控温"), translateText("In the oven", "使用烤箱。"));
        addAdvancement("perfect_temperature", translateText("Perfect Temperature", "完美温度"), translateText("With a more precise temperature, the food will be more delicious.", "用更加精准的温度烤制，食物会更加可口。"));
        addAdvancement("rough_flavor", translateText("Rough Flavor", "粗犷风味"), translateText("Wheat is used to make whole wheat flour.", "用小麦制作全麦面粉。"));
        addAdvancement("my_stomach_is_hungry_too", translateText("My Stomach Is Hungry Too", "我的肚子也饿了"), translateText("Make a baked good.", "制作一个烘焙食品。"));
        addAdvancement("it.s.bread", translateText("It s Bread", "是个面包"), translateText("I've eaten ten different kinds of bread!", "食用过十种不同的面包！"));
        addAdvancement("mix_well", translateText("Mix Well", "搅拌均匀"), translateText("It's not Mixin...", "这不是Mixin..."));
        addAdvancement("cheese_power", translateText("Cheese Power!", "芝士力!"), translateText("Only power. Cheese is power!", "只是力，芝士就是力量!"));
        addAdvancement("glossy_green", translateText("Glossy Green", "绿油油"), translateText("Get a bottle of olive oil.", "获得一瓶橄榄油。"));
        addAdvancement("immortalers_the_bakeries", translateText("Immortalers? The Bakeries!", "千古？烘焙坊！"), translateText("Take Sniffer and dig up coffee beans in the jungle biome.", "带着嗅探兽在丛林生物群系挖掘出咖啡豆。"));
        addAdvancement("very_hard", translateText("It can't be eaten. It can't be eaten at all", "不能吃根本不能吃"), translateText("Get an Eternal Baguette (Hide progress)", "获得一个永恒法棍（隐藏进度）"));
        addAdvancement("get_cream_cake", translateText("Cake! Eat Cake!", "蛋糕！吃下蛋糕！"), translateText("Get Cream Cake.", "获得一个奶油蛋糕。"));
        addAdvancement("get_sofa", translateText("Come on, sit down", "来啊,你坐啊"), translateText("Get Sofa.", "获得一个沙发。"));
        addAdvancement("get_pineapple_oil", translateText("“Ice and Fire”", "“冰火传说”"), translateText("Get Pineapple Oil.", "获得一个冰火菠萝油."));
        addAdvancement("get_flat_croissant",translateText("Don't！","压没得！"),translateText("Make flat croissants using a falling anvil.","使用下落的铁砧制作扁可颂。"));
        addAdvancement("get_taro",translateText("Meetion","芋见你"),translateText("Get Taro.","获得芋头。"));
    }

    private void addPatchoulis(){
        addCategories("baking_utensil","§6Baking Utensil","§6烘焙用具");
        addCategories("bread_making_process","§6Bread Making Process","§6面包制作流程");
        addCategories("do_you_know","§6Do You Know?","§6你知道吗？");
        addCategories("food_ingredient","§6Food Ingredient","§6食材");
        addCategories("perfect_temperature","§6Perfect Temperature","§6完美温度");

        addPatchouliDescr("blender","The blender is a very important machine for you to go on the baking road. It can mix and make many things, such as dough and batter.","搅拌机是你走在烘焙道路上非常重要的机器，他可以搅拌制作很多东西，比如面团和面糊。");
        addPatchouliDescr("bread_knife","You can use it to cut bread, like country bread.","你可以用它来切面包，比如乡村面包。");
        addPatchouliDescr("flour_sieve","Flour Sieve for sifting items, such as $(l:food_ingredient/flour)flour/$.","面粉筛可过筛物品，如$(l:food_ingredient/flour)面粉/$");
        addPatchouliDescr("mould","Make some foods with special shapes, you may need to use them.","制作一些拥有特殊形状的食物，你可能会需要用到他们。");

        addPatchouliEntries("await","Await","等待");
        addPatchouliDescr("await","In the real world, making bread often requires long periods of fermentation or hydrolysis.","在现实世界中，制作面包通常需要长时间的发酵或水解。");

        addPatchouliEntries("be_unable_to_eat_any_more","Be Unable to Eat Any More","吃不完 根本吃不完");
        addPatchouliDescr("be_unable_to_eat_any_more","In the development version, the baguette can be Enchanting Mending.Perhaps you could try attaching durability(?","在开发版本中，法棍可以被附上经验修补魔咒。也许你可以试试附上耐久(？");

        addPatchouliEntries("feed_sequence","Feed Sequence","投料顺序");
        addPatchouliDescr("feed_sequence","The order of ingredients for the dough synthesis recipe is very close to the real order of ingredients, dry powder > yeast > liquid > butter.","面团合成配方的原料顺序非常接近现实中的放料顺序，干粉类 > 酵母 > 液体类 > 黄油。");

        addPatchouliDescr("raw_coffee_bean","Sniffer beasts can dig up raw coffee beans in the jungle biomes.","嗅探兽可在丛林生物群系挖掘到生咖啡豆。");

        addPatchouliDescr("raw_coffee_bean","Sniffer beasts can dig up raw coffee beans in the jungle biomes.","嗅探兽可在丛林生物群系挖掘到生咖啡豆。");

        addPatchouliDescr("flour","Use a $(l:baking_utensil/flour_sieve)flour sieve/$ to sift the whole wheat flour into flour.","使用$(l:baking_utensil/flour_sieve)面粉筛/$将全麦面粉过筛为面粉。");
        addPatchouliDescr("tomato","It can be obtained through transactions with villagers in the farming profession.","可通过与农民村民交易获得。");
        addPatchouliDescr("olive","It can be obtained through transactions with villagers in the farming profession.","可通过与农民村民交易获得。");

        addPatchouliEntries("milk","Milk","牛奶");
        addPatchouliDescr("milk","Hold the bottle milk in your hand, right-click and shake it, and its texture will change after a while.","将瓶装牛奶拿在手中，$(gold)右键摇晃/$，一段时间后它的质地会发生改变。");
        addPatchouliDescr("milk_2","Shake once.","摇晃一次。");
        addPatchouliDescr("milk_3","Shake twice.","摇晃两次。");

        addCategories("cake_making_process", "§6Cake Making Process", "§6蛋糕制作流程");
        addPatchouliEntries("yeast_making", "Yeast Making", "酵母制造");
        addPatchouliEntries("baked_bread", "Baked Bread", "烤制面包");
        addPatchouliEntries("prepare_the_dough", "Prepare The Dough", "准备面团");
        addPatchouliEntries("create_bread_dough", "Create Bread Dough", "制作面包胚");
        addPatchouliEntries("flour", "Flour", "面粉");
        addPatchouliEntries("salt", "Campfire and Salt Water", "营火烧盐水");
        addPatchouliEntries("salt_2", "Raw Salt Block", "粗盐块");
        addPatchouliEntries("salt_3", "Salt Ore", "盐矿石");
        addPatchouliEntries("moka_pot", "Brew coffee in a mocha pot", "摩卡壶煮咖啡");
        addPatchouliEntries("prepare_the_paste", "Prepare The Paste", "准备面糊");
        addPatchouliEntries("star_baking", "Star Baking", "入模烘烤");
        addPatchouliEntries("sequence_assembly", "Sequence Assembly", "序列组装");
        addPatchouliEntries("sequence_assembly_2", "Cream Cake", "奶油蛋糕");
        addPatchouliEntries("sequence_assembly_3", "Tiramisu", "提拉米苏");
        addPatchouliEntries("fresh_cheese_cube", "Fresh Cheese Cub", "鲜奶酪块");
        addPatchouliDescr("yeast_making", "To make bread, you first need to make dough, and to make dough you of course need $(gold)yeast/$, you first need a fermenter,Put in $(gold)3 parts $(l:food_ingredient/flour)whole wheat flour/$$(gold) and 1 part water/$, and let it rise, then you can remove the fresh yeast in a mason jar.", "要制作面包，首先需要制作面团，要制作面团你当然需要$(gold)酵母/$，你首先需要准备一个发酵罐，往里面投入$(gold)三份$(l:food_ingredient/flour)全麦面粉/$$(gold)和一瓶水/$，然后静待发酵完成后，你就可以用玻璃瓶取出鲜酵母啦。");
        addPatchouliDescr("baked_bread", "Do you have an oven?  With the oven you can bake the bread, the bottom of the bread object shows the oven temperature required for the bread, adjust the oven temperature to the right temperature, put the bread in the bread, wait for the delicious bread.", "你有烤炉了吗？有了烤炉你就可以烤面包了，面包胚物品的下方显示了此面包需要的烘烤温度，调整烤炉温度到合适的温度后放入面包胚，等待美味的面包出炉吧。");
        addPatchouliDescr("prepare_the_dough", "With $(l:yeast_making) yeast/$, you are ready to start making the dough, of course, you need a $(l:baking_utensil/blander) mixer/$to make the dough.", "有了$(l:yeast_making)酵母/$，你就可以开始制作面团了，当然，你需要一台$(l:baking_utensil/blander)搅拌机/$来制作面团。");
        addPatchouliDescr("create_bread_dough", "Put the kneaded dough into the $(gold)dough synthesis table/$ again, you can view the dough can be made bread embryo, left click to select to synthesize.", "将揉好的面团再次放入在$(gold)面胚合成台/$，可以查看此面团可制作的面包胚，左键选择以合成。");
        addPatchouliDescr("perfect_temperature", "Food with a Perfect Temperature entry can be eaten even when you are not hungry", "拥有完美火候词条的烘焙食物，在玩家饥饿值满时也能食用");
        addPatchouliDescr("salt", "You can use salt water to get kosher salt blocks", "你可以使用营火烧盐水来获得粗盐块");
        addPatchouliDescr("cheese", "You first need to prepare a fermenter, put a bucket of milk and a serving of $(l:food_ingredient/salt)salt/$ in it, and let the fermentation finish, you can pick out the cheese cubes with your hands.", "你首先需要准备一个发酵罐，往里面投入一桶牛奶和一份$(l:food_ingredient/salt)盐/$，然后静待发酵完成，你就可以用手取出奶酪块啦。");
        addPatchouliDescr("cocoa_powder", "Use$(l:baking_utensil/flour_sieve)Flour Sieve/$The cocoa beans are sifted into cocoa powder.", "使用$(l:baking_utensil/flour_sieve)面粉筛/$将可可豆过筛处理为可可粉。");
        addPatchouliDescr("blender_2", "You can use the Redstone torch to adjust the machine to compatibility mode, where you can set item filtering.", "可以使用红石火把调整机器为兼容模式，在兼容模式下可以设置物品过滤。");
        addPatchouliDescr("meat_floss", "Process Cooked Porkchop into Meat Floss using a $(l:baking_utensil/blander) blender/$.", "使用$(l:baking_utensil/blander)搅拌机/$将熟猪排加工为肉松。");
        addPatchouliDescr("whole_egg", "It can be obtained by right-clicking the main hand with an egg and the secondary hand with a bread knife.", "可通过主手拿蛋副手拿面包刀右键获得，右键全蛋可获得生蛋白和生蛋黄。");
        addPatchouliDescr("moka_pot", "You can use a mocha pot to brew coffee.", "你可以用摩卡壶来煮咖啡。");
        addPatchouliDescr("moka_pot_2", "Place the mocha pot above the stove, right-click to add the coffee powder, and the coffee will be cooked in a short while.", "将摩卡壶放置在炉的上方，右键放入咖啡粉，一会儿咖啡就会煮好。");
        addPatchouliDescr("prepare_the_paste", "You need to use$(l:baking_utensil/blander)Blander/$To make the cake batter, first make the whipped egg whites and egg yolk batter separately, and then mix them together.", "你需要用$(l:baking_utensil/blander)搅拌机/$制作蛋糕面糊，先分别制作打发蛋白和蛋黄糊，然后把他们混合在一起。");
        addPatchouliDescr("star_baking", "Pour the cake batter into the mold. Baking a cake always requires a mold, although the molds they need may be different. Anyway, once it's ready, put it in the oven to bake.", "将蛋糕糊倒入到模具中，烤蛋糕总是需要一个模具，尽管它们需要的模具可能不一样，总之，准备好后就将它放进烤炉烘烤吧。");
        addPatchouliDescr("sequence_assembly", "You can manually place the ingredients on the cake base to make a more upscale cake!", "你可以手动将食材放在蛋糕胚上来制作更高级的蛋糕！");
        addPatchouliDescr("sequence_assembly_2", "Cut Cake Base→Foamed Cream→Cut Cake Base→Foamed Cream→Sweet Berries", "蛋糕胚切片→奶油→蛋糕胚切片→奶油→甜浆果");
        addPatchouliDescr("sequence_assembly_3", "Soak Coffee Cut Cake Base→Cheese Cream→Soak Coffee Cut Cake Base→Cheese Cream→Cocoa Powder", "浸润咖啡的蛋糕胚切片→奶酪奶油→浸润咖啡的蛋糕胚切片→奶酪奶油→可可粉");
        addPatchouliDescr("sequence_assembly_4", "Red Velvet Cake Base→Cheese Cream", "红丝绒蛋糕胚→奶酪奶油");
        addPatchouliDescr("bread_making_process", "A nanny-level tutorial on bread making", "面包制作保姆级教程");
        addPatchouliDescr("cake_making_process", "A nanny-level tutorial on cake making", "蛋糕制作保姆级教程");
        addPatchouliDescr("fresh_cheese_cube", "You can get four servings of fresh cheese cubes by putting one portion of milk and one portion of salt in the fermentation tank.", "你可以通过在发酵桶中放入一份牛奶和一份盐来得到4份鲜奶酪块。");
        addPatchouliDescr("cake_roll", "$(l:https://www.bilibili.com/video/BV1WcuAzQEMr)Nanny level tutorial on making cake rolls", "$(l:https://www.bilibili.com/video/BV1WcuAzQEMr)蛋糕卷制作保姆级教程");
        addPatchouliDescr("matcha_powder", "Use$(l:baking_utensil/flour_sieve)Flour Sieve/$Sift the leaves into matcha powder.", "使用$(l:baking_utensil/flour_sieve)面粉筛/$将树叶过筛为抹茶粉。");
        addPatchouliDescr("stone_kiln", "A very primitive oven. You can use it to bake pizza. You also need a Stone Kiln Shovel for putting in and taking out items.", "非常原始的烤炉，你可以用来烤披萨，你还需要一个炉铲，用来放入和取出物品。");
        addPatchouliDescr("taro", "It can be obtained through transactions with villagers in the farming profession.", "可通过与农民村民交易获得。");
    }

    private void addCategories(String key,String en_us,String zh_cn){
        add(CATEGORIES + key,en_us,zh_cn);
    }

    private void addPatchouliDescr(String key,String en_us,String zh_cn){
        add(PATCHOULI_DESCR + key,en_us,zh_cn);
    }

    private void addPatchouliEntries(String key,String en_us,String zh_cn){
        add(PATCHOULI_ENTRIES + key,en_us,zh_cn);
    }


    private void addDesc(String key,String en_us,String zh_cn){
        add(ADVANCEMENT + key + ".descr",en_us,zh_cn);
    }

    private void addTitle(String key,String en_us,String zh_cn){
        add(ADVANCEMENT + key + ".title",en_us,zh_cn);
    }

    private void addAdvancement(String name,List<String> title,List<String> desc){
        addTitle(name,title.get(0),title.get(1));
        addDesc(name,desc.get(0),desc.get(1));
    }

    public List<String> translateText(String en_us,String zh_cn){
        List<String> text = new ArrayList<>();
        text.add(en_us);
        text.add(zh_cn);
        return text;
    }

    private void addPonders() {
        // Tag
        add("bakeries.ponder.tag.bakeries_equipment",
                "Bakeries Equipment",
                "烘焙坊设备");
        add("bakeries.ponder.tag.bakeries_equipment.description",
                "Professional equipment used for processing flour, fermenting, and making cheese.",
                "用于加工面粉、发酵以及制作奶酪的专业设备。");

        // Scenes
        // fermentation_tank_interaction
        add("bakeries.ponder.fermentation_tank_interaction.header",
                "Using the Fermentation Tank",
                "使用发酵罐");
        add("bakeries.ponder.fermentation_tank_interaction.text_1",
                "Add 3 scoops of Whole Wheat Flour",
                "加入全麦面粉");
        add("bakeries.ponder.fermentation_tank_interaction.text_2",
                "Add water to begin fermentation",
                "用水瓶加入水");
        add("bakeries.ponder.fermentation_tank_interaction.text_3",
                "When full of flour and water, it will eventually ferment into yeast",
                "等待一段时间...");
        add("bakeries.ponder.fermentation_tank_interaction.text_4",
                "Yeast can be extracted multiple times using glass bottles",
                "发酵完成，使用玻璃瓶可以多次获取酵母");

        // cheese_tank_interaction
        add("bakeries.ponder.cheese_tank_interaction.header",
                "Making Cheese in the Fermentation Tank",
                "发酵奶酪");
        add("bakeries.ponder.cheese_tank_interaction.text_1",
                "If you add milk and salt instead...",
                "如果加入奶和盐");
        add("bakeries.ponder.cheese_tank_interaction.text_2",
                "The mixture will solidify into a batch of cheese",
                "就会发酵得到奶酪");
        add("bakeries.ponder.cheese_tank_interaction.text_3",
                "Simply use an empty hand to collect the finished cheese",
                "空手即可获取");

        // auto_baking_line
        add("bakeries.ponder.auto_baking_line.header",
                "Automated Baking Production Line",
                "自动化烘焙生产线");
        add("bakeries.ponder.auto_baking_line.text_1",
                "Drop ingredients directly into the Basin",
                "将原料直接投入工作盆");
        add("bakeries.ponder.auto_baking_line.text_2",
                "The Mechanical Mixer processes the mixture using blender recipes",
                "动力搅拌器会运行搅拌机的配方");
        add("bakeries.ponder.auto_baking_line.text_3",
                "The Brass Funnel filters out byproducts like empty bottles",
                "黄铜漏斗会筛选并取出生产过程中的副产物（如空瓶）");
        add("bakeries.ponder.auto_baking_line.text_4",
                "The Mechanical Saw cuts the dough into embryos",
                "用动力锯将传送带上的面团切割为面包胚");
        add("bakeries.ponder.auto_baking_line.text_5",
                "Once the oven temperature is set, baking begins automatically",
                "设定好烤箱温度，面包就会自动开烤");
    }
}