package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.util.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output,lookupProvider, pBlockTagsProvider.contentsGetter(), MetalBarrels.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(ModTags.Blocks.WOOD_BARRELS,ModTags.Items.WOOD_BARRELS);
        copy(ModTags.Blocks.COPPER_BARRELS,ModTags.Items.COPPER_BARRELS);
        copy(ModTags.Blocks.IRON_BARRELS,ModTags.Items.IRON_BARRELS);
        copy(ModTags.Blocks.SILVER_BARRELS,ModTags.Items.SILVER_BARRELS);
        copy(ModTags.Blocks.GOLD_BARRELS,ModTags.Items.GOLD_BARRELS);
        copy(ModTags.Blocks.DIAMOND_BARRELS,ModTags.Items.DIAMOND_BARRELS);
        copy(ModTags.Blocks.CRYSTAL_BARRELS,ModTags.Items.CRYSTAL_BARRELS);
        copy(ModTags.Blocks.OBSIDIAN_BARRELS,ModTags.Items.OBSIDIAN_BARRELS);
        copy(ModTags.Blocks.NETHERITE_BARRELS,ModTags.Items.NETHERITE_BARRELS);

        Item[] gold = ModDatagen.getKnownItems().filter(item -> BuiltInRegistries.ITEM.getKey(item).getPath().contains("gold")).toArray(Item[]::new);
        tag(ItemTags.PIGLIN_LOVED).add(gold);
    }
}
