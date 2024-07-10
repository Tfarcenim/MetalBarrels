package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.init.ModItems;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.util.UpgradeInfo;
import tfar.metalbarrels.util.ModTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output,provider);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        for (Map.Entry<String,BarrelUpgradeItem> entry : ModItems.upgrade_items.entrySet()) {
            BarrelUpgradeItem item = entry.getValue();
            UpgradeInfo info = item.getUpgradeInfo();
            String st = entry.getKey().split("_")[0];
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,info.end_block())
                    .requires(ModTags.Items.tag("barrels/"+st))
                    .requires(item)
                    .unlockedBy("has_"+st, has(ModTags.Items.tag("barrels/"+st)))
                    .save(consumer,MetalBarrels.id("upgrades/combine/"+entry.getKey()));
        }


        cheapNetheriteSmithing(consumer, ModBlocks.OBSIDIAN_BARREL.asItem(),RecipeCategory.DECORATIONS,ModBlocks.NETHERITE_BARREL.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.COPPER_BARREL)
                .define('b',Tags.Items.BARRELS_WOODEN).define('g',Tags.Items.INGOTS_COPPER)
                .pattern("gbg")
                .unlockedBy("has_barrel",has(Tags.Items.BARRELS_WOODEN))
                .save(consumer, MetalBarrels.id("barrels/wood_to_copper_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.IRON_BARREL)
                .define('b', Tags.Items.BARRELS_WOODEN).define('g',Tags.Items.INGOTS_IRON)
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .unlockedBy("has_barrel",has(Tags.Items.BARRELS_WOODEN))
                .save(consumer, MetalBarrels.id("barrels/wood_to_iron_barrel"));



        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.GOLD_BARREL)
                .define('b', Tags.Items.BARRELS_WOODEN).define('i',Tags.Items.INGOTS_IRON).define('g',Tags.Items.INGOTS_GOLD)
                .pattern("gig")
                .pattern("ibi")
                .pattern("gig")
                .unlockedBy("has_barrel",has(Tags.Items.BARRELS_WOODEN))
                .save(consumer, MetalBarrels.id("barrels/wood_to_gold_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.IRON_BARREL)
                .define('b',ModBlocks.COPPER_BARREL).define('g',Tags.Items.INGOTS_IRON)
                .pattern("gbg")
                .unlockedBy(getHasName(ModBlocks.COPPER_BARREL),has(ModBlocks.COPPER_BARREL))
                .save(consumer, MetalBarrels.id("barrels/copper_to_iron_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.SILVER_BARREL)
                .define('b',ModBlocks.IRON_BARREL).define('g',ModTags.Items.INGOTS_SILVER)
                .pattern("gbg")
                .unlockedBy(getHasName(ModBlocks.IRON_BARREL),has(ModBlocks.IRON_BARREL))
                .save(consumer, MetalBarrels.id("barrels/iron_to_silver_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.GOLD_BARREL)
                .define('b',ModBlocks.SILVER_BARREL).define('g',Tags.Items.INGOTS_GOLD)
                .pattern("gbg")
                .unlockedBy(getHasName(ModBlocks.SILVER_BARREL),has(ModBlocks.SILVER_BARREL))
                .save(consumer, MetalBarrels.id("barrels/silver_to_gold_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.GOLD_BARREL)
                .define('b',ModBlocks.IRON_BARREL).define('g',Tags.Items.INGOTS_GOLD)
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .unlockedBy(getHasName(ModBlocks.IRON_BARREL),has(ModBlocks.IRON_BARREL))
                .save(consumer, MetalBarrels.id("barrels/iron_to_gold_barrel"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DIAMOND_BARREL)
                .requires(ModBlocks.GOLD_BARREL).requires(Tags.Items.GEMS_DIAMOND)
                .unlockedBy(getHasName(ModBlocks.GOLD_BARREL),has(ModBlocks.GOLD_BARREL))
                .save(consumer, MetalBarrels.id("barrels/gold_to_diamond_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DIAMOND_BARREL)
                .define('b',ModBlocks.SILVER_BARREL).define('g',Tags.Items.GEMS_DIAMOND)
                .pattern("gbg")
                .unlockedBy(getHasName(ModBlocks.SILVER_BARREL),has(ModBlocks.SILVER_BARREL))
                .save(consumer, MetalBarrels.id("barrels/silver_to_diamond_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_BARREL)
                .define('b',ModBlocks.DIAMOND_BARREL).define('g',Tags.Items.GLASS_BLOCKS_COLORLESS)
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .unlockedBy(getHasName(ModBlocks.DIAMOND_BARREL),has(ModBlocks.DIAMOND_BARREL))
                .save(consumer, MetalBarrels.id("barrels/diamond_to_crystal_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,ModBlocks.OBSIDIAN_BARREL)
                .define('b',ModBlocks.DIAMOND_BARREL).define('g',Tags.Items.OBSIDIANS)
                .pattern(" g ")
                .pattern("gbg")
                .pattern(" g ")
                .unlockedBy(getHasName(ModBlocks.DIAMOND_BARREL),has(ModBlocks.DIAMOND_BARREL))
                .save(consumer, MetalBarrels.id("barrels/diamond_to_obsidian_barrel"));

    }

    protected static void cheapNetheriteSmithing(RecipeOutput pFinishedRecipeConsumer, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Tags.Items.GEMS_DIAMOND), Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pCategory, pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, MetalBarrels.id(getItemName(pResultItem) + "_smithing"));
    }

}
