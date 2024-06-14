package tfar.metalbarrels.init;

import tfar.metalbarrels.block.MetalBarrelBlock;
import tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class ModBlocks {

    static Block.Properties metal = Block.Properties.of().strength(1,6).sound(SoundType.METAL);
    static Block.Properties obsidian = Block.Properties.of().strength(2.5f,6000);
    
    public static final Block COPPER_BARREL = new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.copper());
    public static final Block IRON_BARREL = new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.iron());
    public static final Block GOLD_BARREL = new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.gold());
    public static final Block DIAMOND_BARREL = new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.diamond());
    public static final Block NETHERITE_BARREL = new MetalBarrelBlock(obsidian, MetalBarrelBlockEntityType.netherite());
    public static final Block OBSIDIAN_BARREL = new MetalBarrelBlock(obsidian, MetalBarrelBlockEntityType.diamond());
    public static final Block SILVER_BARREL = new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.silver());
    public static final Block CRYSTAL_BARREL = new MetalBarrelBlock(metal.noOcclusion(), MetalBarrelBlockEntityType.diamond());
}
