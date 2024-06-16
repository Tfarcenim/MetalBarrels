package tfar.metalbarrels.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record UpgradeInfo(TagKey<Block> start_blocks, Block end_block) {

    public boolean canUpgrade(BlockState barrel) {
        return barrel.is(start_blocks);
    }
}