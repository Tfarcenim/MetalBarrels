package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> lookupProvider,@Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_,lookupProvider, MetalBarrels.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.WOOD_BARRELS).add(Blocks.BARREL);
        tag(ModTags.Blocks.COPPER_BARRELS).add(ModBlocks.COPPER_BARREL);
        tag(ModTags.Blocks.IRON_BARRELS).add(ModBlocks.IRON_BARREL);
        tag(ModTags.Blocks.SILVER_BARRELS).add(ModBlocks.SILVER_BARREL);
        tag(ModTags.Blocks.GOLD_BARRELS).add(ModBlocks.GOLD_BARREL);
        tag(ModTags.Blocks.DIAMOND_BARRELS).add(ModBlocks.DIAMOND_BARREL);
        tag(ModTags.Blocks.OBSIDIAN_BARRELS).add(ModBlocks.OBSIDIAN_BARREL);
    }
}
