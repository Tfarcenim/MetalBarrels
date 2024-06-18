package tfar.metalbarrels.datagen.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.util.ModTags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_126511_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_, MetalBarrels.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.COPPER_BARREL,ModBlocks.IRON_BARREL,ModBlocks.SILVER_BARREL,ModBlocks.GOLD_BARREL,ModBlocks.DIAMOND_BARREL,
                ModBlocks.CRYSTAL_BARREL,ModBlocks.OBSIDIAN_BARREL,ModBlocks.NETHERITE_BARREL);

        tag(ModTags.Blocks.WOOD_BARRELS).add(Blocks.BARREL);
        tag(ModTags.Blocks.COPPER_BARRELS).add(ModBlocks.COPPER_BARREL);
        tag(ModTags.Blocks.IRON_BARRELS).add(ModBlocks.IRON_BARREL);
        tag(ModTags.Blocks.SILVER_BARRELS).add(ModBlocks.SILVER_BARREL);
        tag(ModTags.Blocks.GOLD_BARRELS).add(ModBlocks.GOLD_BARREL);
        tag(ModTags.Blocks.DIAMOND_BARRELS).add(ModBlocks.DIAMOND_BARREL);
        tag(ModTags.Blocks.OBSIDIAN_BARRELS).add(ModBlocks.OBSIDIAN_BARREL);
    }
}
