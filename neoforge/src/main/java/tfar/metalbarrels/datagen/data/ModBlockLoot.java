package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.init.ModBlocks;

public class ModBlockLoot extends VanillaBlockLoot {
    public ModBlockLoot(HolderLookup.Provider pRegistries) {
        super(pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.COPPER_BARREL);
        dropSelf(ModBlocks.IRON_BARREL);
        dropSelf(ModBlocks.SILVER_BARREL);
        dropSelf(ModBlocks.GOLD_BARREL);
        dropSelf(ModBlocks.DIAMOND_BARREL);
        dropSelf(ModBlocks.CRYSTAL_BARREL);
        dropSelf(ModBlocks.OBSIDIAN_BARREL);
        dropSelf(ModBlocks.NETHERITE_BARREL);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModDatagen.getKnownBlocks().toList();
    }
}
