package tfar.metalbarrels.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.item.UpgradeInfo;
import tfar.metalbarrels.util.ModTags;

import java.lang.reflect.Field;
import java.util.*;

public class ModItems {   //wood to x
    private static final Item.Properties properties = new Item.Properties();
    public static final Map<String,BarrelUpgradeItem> upgrade_items;

    public static Map<String, Pair<TagKey<Block>,Block>> map = new HashMap<>();

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
        for (int i = 0; i < MetalBarrels.tiers.length;i++) {
            for (int j = i +1; j < MetalBarrels.tiers.length;j++) {
                
                String s1 = MetalBarrels.tiers[i];
                String s2 = MetalBarrels.tiers[j];

                String s = s1 +"_to_"+ s2;
                BarrelUpgradeItem item = new BarrelUpgradeItem(properties,new UpgradeInfo(map.get(s1).getFirst(),map.get(s2).getSecond()));
                upgrade_items.put(s,item);
            }
        }
    }
    //crystal
    public static final Item wood_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.WOOD_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item copper_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.COPPER_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item iron_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.IRON_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item silver_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.SILVER_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item gold_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.GOLD_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item diamond_to_crystal = new BarrelUpgradeItem(properties, new UpgradeInfo(ModTags.Blocks.DIAMOND_BARRELS,
            ModBlocks.CRYSTAL_BARREL));

    public static final Item COPPER_BARREL = new BlockItem(ModBlocks.COPPER_BARREL, properties);
    public static final Item IRON_BARREL = new BlockItem(ModBlocks.IRON_BARREL, properties);
    public static final Item GOLD_BARREL = new BlockItem(ModBlocks.GOLD_BARREL, properties);
    public static final Item DIAMOND_BARREL = new BlockItem(ModBlocks.DIAMOND_BARREL, properties);
    public static final Item OBSIDIAN_BARREL = new BlockItem(ModBlocks.OBSIDIAN_BARREL, properties);
    public static final Item SILVER_BARREL = new BlockItem(ModBlocks.SILVER_BARREL, properties);
    public static final Item CRYSTAL_BARREL = new BlockItem(ModBlocks.CRYSTAL_BARREL, properties);
    public static final Item NETHERITE_BARREL = new BlockItem(ModBlocks.NETHERITE_BARREL, properties.fireResistant());

    private static final List<Item> ITEMS = new ArrayList<>();
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
}
