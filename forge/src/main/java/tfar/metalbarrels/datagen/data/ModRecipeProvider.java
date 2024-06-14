package tfar.metalbarrels.datagen.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.MetalBarrelsForge;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.init.ModItems;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.item.UpgradeInfo;
import tfar.metalbarrels.util.ModTags;

import java.util.Map;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (Map.Entry<String,BarrelUpgradeItem> entry : ModItems.upgrade_items.entrySet()) {
            BarrelUpgradeItem item = entry.getValue();
            UpgradeInfo info = item.getUpgradeInfo();
            String st = entry.getKey().split("_")[0];
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,info.end_block)
                    .requires(ModTags.Items.tag("barrels/"+st))
                    .requires(item)
                    .unlockedBy("has_"+st, has(ModTags.Items.tag("barrels/"+st)))
                    .save(consumer,MetalBarrels.id("upgrades/combine/"+entry.getKey()));
        }


        cheapNetheriteSmithing(consumer, ModBlocks.OBSIDIAN_BARREL.asItem(),RecipeCategory.DECORATIONS,ModBlocks.NETHERITE_BARREL.asItem());
    }

    protected static void cheapNetheriteSmithing(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Tags.Items.GEMS_DIAMOND), Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pCategory, pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, MetalBarrels.id(getItemName(pResultItem) + "_smithing"));
    }

}
