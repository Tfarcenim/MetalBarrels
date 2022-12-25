package tfar.metalbarrels.datagen.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import tfar.metalbarrels.MetalBarrels;
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
                    .save(consumer,new ResourceLocation(MetalBarrels.MODID,"upgrades/combine/"+entry.getKey()));
        }
    }
}
