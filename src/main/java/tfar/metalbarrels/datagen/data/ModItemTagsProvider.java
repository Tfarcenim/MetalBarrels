package tfar.metalbarrels.datagen.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.util.ModTags;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, MetalBarrels.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(ModTags.Blocks.WOOD_BARRELS,ModTags.Items.WOOD_BARRELS);
        copy(ModTags.Blocks.COPPER_BARRELS,ModTags.Items.COPPER_BARRELS);
        copy(ModTags.Blocks.IRON_BARRELS,ModTags.Items.IRON_BARRELS);
        copy(ModTags.Blocks.SILVER_BARRELS,ModTags.Items.SILVER_BARRELS);
        copy(ModTags.Blocks.GOLD_BARRELS,ModTags.Items.GOLD_BARRELS);
        copy(ModTags.Blocks.DIAMOND_BARRELS,ModTags.Items.DIAMOND_BARRELS);
        copy(ModTags.Blocks.OBSIDIAN_BARRELS,ModTags.Items.OBSIDIAN_BARRELS);
    }
}
