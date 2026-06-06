package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_126511_,lookupProvider, MetalBarrels.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.COPPER_BARREL,ModBlocks.IRON_BARREL,ModBlocks.SILVER_BARREL,ModBlocks.GOLD_BARREL,ModBlocks.DIAMOND_BARREL,
                ModBlocks.CRYSTAL_BARREL,ModBlocks.OBSIDIAN_BARREL,ModBlocks.NETHERITE_BARREL);

        /*tag(ModTags.Blocks.COPPER_BARRELS).add(ModBlocks.COPPER_BARREL);
        tag(ModTags.Blocks.IRON_BARRELS).add(ModBlocks.IRON_BARREL);
        tag(ModTags.Blocks.SILVER_BARRELS).add(ModBlocks.SILVER_BARREL);
        tag(ModTags.Blocks.GOLD_BARRELS).add(ModBlocks.GOLD_BARREL);
        tag(ModTags.Blocks.DIAMOND_BARRELS).add(ModBlocks.DIAMOND_BARREL);
        tag(ModTags.Blocks.CRYSTAL_BARRELS).add(ModBlocks.CRYSTAL_BARREL);
        tag(ModTags.Blocks.OBSIDIAN_BARRELS).add(ModBlocks.OBSIDIAN_BARREL);
        tag(ModTags.Blocks.NETHERITE_BARRELS).add(ModBlocks.NETHERITE_BARREL);*/

        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.COPPER_BARREL,ModBlocks.IRON_BARREL,ModBlocks.SILVER_BARREL,ModBlocks.GOLD_BARREL);
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.DIAMOND_BARREL,ModBlocks.CRYSTAL_BARREL,ModBlocks.OBSIDIAN_BARREL,ModBlocks.NETHERITE_BARREL);

        /*tag(Tags.Blocks.BARRELS).addTags(ModTags.Blocks.COPPER_BARRELS,ModTags.Blocks.IRON_BARRELS,
                ModTags.Blocks.SILVER_BARRELS,ModTags.Blocks.GOLD_BARRELS,ModTags.Blocks.DIAMOND_BARRELS,ModTags.Blocks.CRYSTAL_BARRELS,ModTags.Blocks.OBSIDIAN_BARRELS
                ,ModTags.Blocks.NETHERITE_BARRELS);*/

        tag(BlockTags.GUARDED_BY_PIGLINS).addTags(ModTags.Blocks.COPPER_BARRELS,ModTags.Blocks.IRON_BARRELS,ModTags.Blocks.SILVER_BARRELS,
                ModTags.Blocks.GOLD_BARRELS,ModTags.Blocks.DIAMOND_BARRELS,ModTags.Blocks.CRYSTAL_BARRELS,ModTags.Blocks.OBSIDIAN_BARRELS,ModTags.Blocks.NETHERITE_BARRELS);
    }
}
