package tfar.metalbarrels.datagen.data;

import net.minecraft.data.tags.BlockItemTagsProvider;
import net.neoforged.neoforge.common.Tags;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.util.ModTags;

public abstract class ModBlockItemTagsProvider extends BlockItemTagsProvider {
    @Override
    protected void run() {
        tag(ModTags.Blocks.COPPER_BARRELS,ModTags.Items.COPPER_BARRELS)
                .add(ModBlocks.COPPER_BARREL);
        tag(ModTags.Blocks.IRON_BARRELS,ModTags.Items.IRON_BARRELS)
                .add(ModBlocks.IRON_BARREL);
        tag(ModTags.Blocks.SILVER_BARRELS,ModTags.Items.SILVER_BARRELS).add(ModBlocks.SILVER_BARREL);
        tag(ModTags.Blocks.GOLD_BARRELS,ModTags.Items.GOLD_BARRELS).add(ModBlocks.GOLD_BARREL);
        tag(ModTags.Blocks.DIAMOND_BARRELS,ModTags.Items.DIAMOND_BARRELS).add(ModBlocks.DIAMOND_BARREL);
        tag(ModTags.Blocks.CRYSTAL_BARRELS,ModTags.Items.CRYSTAL_BARRELS).add(ModBlocks.CRYSTAL_BARREL);
        tag(ModTags.Blocks.OBSIDIAN_BARRELS,ModTags.Items.OBSIDIAN_BARRELS).add(ModBlocks.OBSIDIAN_BARREL);
        tag(ModTags.Blocks.NETHERITE_BARRELS,ModTags.Items.NETHERITE_BARRELS).add(ModBlocks.NETHERITE_BARREL);
        tag(Tags.Blocks.BARRELS,Tags.Items.BARRELS).addTags(ModTags.Blocks.COPPER_BARRELS,ModTags.Blocks.IRON_BARRELS,
                ModTags.Blocks.SILVER_BARRELS,ModTags.Blocks.GOLD_BARRELS,ModTags.Blocks.DIAMOND_BARRELS,ModTags.Blocks.CRYSTAL_BARRELS,ModTags.Blocks.OBSIDIAN_BARRELS
                ,ModTags.Blocks.NETHERITE_BARRELS);
    }
}
