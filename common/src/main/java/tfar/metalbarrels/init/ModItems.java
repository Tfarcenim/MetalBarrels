package tfar.metalbarrels.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.platform.Services;
import tfar.metalbarrels.util.UpgradeInfo;
import tfar.metalbarrels.util.ModTags;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {   //wood to x
    public static final Map<String,BarrelUpgradeItem> upgrade_items;

    public static Map<String, Pair<TagKey<Block>,Block>> map = new HashMap<>();
    private static final List<Item> ITEMS = new ArrayList<>();


    //crystal
    public static final Item wood_to_crystal = registerBarrelUpgrade(
            "wood_to_crystal",new UpgradeInfo(ModTags.Blocks.WOOD_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item copper_to_crystal =registerBarrelUpgrade(
            "copper_to_crystal", new UpgradeInfo(ModTags.Blocks.COPPER_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item iron_to_crystal = registerBarrelUpgrade(
            "iron_to_crystal", new UpgradeInfo(ModTags.Blocks.IRON_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item silver_to_crystal =registerBarrelUpgrade("silver_to_crystal",
            new UpgradeInfo(ModTags.Blocks.SILVER_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item gold_to_crystal =registerBarrelUpgrade("gold_to_crystal",
            new UpgradeInfo(ModTags.Blocks.GOLD_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item diamond_to_crystal = registerBarrelUpgrade("diamond_to_crystal",
            new UpgradeInfo(ModTags.Blocks.DIAMOND_BARRELS, ModBlocks.CRYSTAL_BARREL));

    public static final Item COPPER_BARREL = registerBlock(ModBlocks.COPPER_BARREL);
    public static final Item IRON_BARREL = registerBlock(ModBlocks.IRON_BARREL);
    public static final Item GOLD_BARREL = registerBlock(ModBlocks.GOLD_BARREL);
    public static final Item DIAMOND_BARREL = registerBlock(ModBlocks.DIAMOND_BARREL);
    public static final Item OBSIDIAN_BARREL = registerBlock(ModBlocks.OBSIDIAN_BARREL);
    public static final Item SILVER_BARREL = registerBlock(ModBlocks.SILVER_BARREL);
    public static final Item CRYSTAL_BARREL = registerBlock(ModBlocks.CRYSTAL_BARREL);
    public static final Item NETHERITE_BARREL = registerBlock(ModBlocks.NETHERITE_BARREL, new Item.Properties().fireResistant());

    static {

        map.put("wood",Pair.of(ModTags.Blocks.WOOD_BARRELS,null));
        map.put("copper",Pair.of(ModTags.Blocks.COPPER_BARRELS,ModBlocks.COPPER_BARREL));
        map.put("iron",Pair.of(ModTags.Blocks.IRON_BARRELS,ModBlocks.IRON_BARREL));
        map.put("silver",Pair.of(ModTags.Blocks.SILVER_BARRELS,ModBlocks.SILVER_BARREL));
        map.put("gold",Pair.of(ModTags.Blocks.GOLD_BARRELS,ModBlocks.GOLD_BARREL));
        map.put("diamond",Pair.of(ModTags.Blocks.DIAMOND_BARRELS,ModBlocks.DIAMOND_BARREL));
        map.put("obsidian",Pair.of(ModTags.Blocks.OBSIDIAN_BARRELS,ModBlocks.OBSIDIAN_BARREL));
        map.put("netherite",Pair.of(null,ModBlocks.NETHERITE_BARREL));

        upgrade_items = new HashMap<>();
        for (int i = 0; i < MetalBarrels.tiers.length; i++) {
            for (int j = i +1; j < MetalBarrels.tiers.length; j++) {

                String s1 = MetalBarrels.tiers[i];
                String s2 = MetalBarrels.tiers[j];

                String s = s1 +"_to_"+ s2;
                BarrelUpgradeItem item =registerBarrelUpgrade(s,new UpgradeInfo(map.get(s1).getFirst(),map.get(s2).getSecond()));
                upgrade_items.put(s,item);
            }
        }
    }

    public static BarrelUpgradeItem registerBarrelUpgrade(String key,UpgradeInfo info) {
        return registerItem(key,p -> new BarrelUpgradeItem(p, info));
    }

    public static List<Item> getItems() {
        if (ITEMS.isEmpty()) {
            for (Field field : ModItems.class.getFields()) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Item item) {
                        ITEMS.add(item);
                    }
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
            ITEMS.addAll(upgrade_items.values());
        }
        return ITEMS;
    }

    private static Item registerBlock(Block block) {
        return registerBlock(block, BlockItem::new);
    }

    private static Item registerBlock(Block block, Item.Properties properties) {
        return registerBlock(block, BlockItem::new, properties);
    }

    private static Item registerBlock(Block block, UnaryOperator<Item.Properties> propertiesFunction) {
        return registerBlock(block, (b, p) -> new BlockItem(b, propertiesFunction.apply(p)));
    }


    private static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> itemFactory) {
        return registerBlock(block, itemFactory, new Item.Properties());
    }

    private static Item registerBlock(Block block, BiFunction<Block, Item.Properties, Item> itemFactory, Item.Properties properties) {
        return registerItem(
                blockIdToItemId(block.builtInRegistryHolder().key()),
                p -> itemFactory.apply(block, p),
                properties.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures())
        );
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> blockName) {
        return ResourceKey.create(Registries.ITEM, blockName.identifier());
    }

    private static <I extends Item> I registerItem(String name, Function<Item.Properties, I> itemFactory) {
        return registerItem(modItemId(name), itemFactory, new Item.Properties());
    }

    private static <I extends Item> I registerItem(String name, Function<Item.Properties, I> itemFactory, Item.Properties properties) {
        return registerItem(modItemId(name), itemFactory, properties);
    }

    private static Item registerItem(String name, Item.Properties properties) {
        return registerItem(modItemId(name), Item::new, properties);
    }

    private static Item registerItem(String name) {
        return registerItem(modItemId(name), Item::new, new Item.Properties());
    }

    @SuppressWarnings("unchecked")
    private static <I extends Item> ResourceKey<I> modItemId(String name) {
        return (ResourceKey<I>) ResourceKey.create(Registries.ITEM, MetalBarrels.id(name));
    }


    private static <I extends Item> I registerItem(ResourceKey<I> key, Function<Item.Properties, I> itemFactory) {
        return registerItem(key, itemFactory, new Item.Properties());
    }

    private static <I extends Item> I registerItem(ResourceKey<I> key, Function<Item.Properties, I> itemFactory, Item.Properties properties) {
        I item = itemFactory.apply(properties.setId((ResourceKey<Item>) key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM,(ResourceKey<Item>) key, item);
    }

    public static void init() {

    }
}
