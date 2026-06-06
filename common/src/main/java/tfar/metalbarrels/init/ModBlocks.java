package tfar.metalbarrels.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.block.CrystalBarrelBlock;
import tfar.metalbarrels.block.MetalBarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import tfar.metalbarrels.util.BarrelProperties;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.function.Function;

public class ModBlocks {

    static Block.Properties metal() {
        return Block.Properties.of().strength(1,6).sound(SoundType.METAL).requiresCorrectToolForDrops();
    }
    static Block.Properties obsidian() {
        return Block.Properties.of().strength(2.5f, 6000).requiresCorrectToolForDrops();
    }

    public static final MetalBarrelBlock COPPER_BARREL = register("copper_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.copper(), BarrelProperties.copper),metal());
    public static final MetalBarrelBlock IRON_BARREL =register("iron_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.iron(),BarrelProperties.iron),metal());
    public static final MetalBarrelBlock GOLD_BARREL = register("gold_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.gold(),BarrelProperties.gold),metal());
    public static final MetalBarrelBlock DIAMOND_BARREL = register("diamond_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.diamond(),BarrelProperties.diamond),metal());
    public static final MetalBarrelBlock NETHERITE_BARREL = register("netherite_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.netherite(),BarrelProperties.netherite),obsidian());
    public static final MetalBarrelBlock OBSIDIAN_BARREL = register("obsidian_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.diamond(),BarrelProperties.diamond),obsidian());
    public static final MetalBarrelBlock SILVER_BARREL =register("silver_barrel",p ->
            new MetalBarrelBlock(p, BarrelProperties.silver(),BarrelProperties.silver),metal());
    public static final MetalBarrelBlock CRYSTAL_BARREL = register("crystal_barrel",p ->
            new CrystalBarrelBlock(p, BarrelProperties.crystal(),BarrelProperties.crystal),metal().noOcclusion());

    public static void init(){

    }



        @SuppressWarnings("unchecked")
    private static <B extends Block> B register(ResourceKey<B> id, Function<BlockBehaviour.Properties, B> factory, BlockBehaviour.Properties properties) {
        Block block = factory.apply(properties.setId((ResourceKey<Block>) id));
        return (B) Registry.register(BuiltInRegistries.BLOCK, (ResourceKey<Block>)id, block);
    }

    private static <B extends Block> B register(String id, Function<BlockBehaviour.Properties, B> factory,
                                                BlockBehaviour.Properties properties) {
        return register(modBlockId(id), factory, properties);
    }

    @SuppressWarnings("unchecked")
    private static <B extends Block> ResourceKey<B> modBlockId(String name) {
        return (ResourceKey<B>) ResourceKey.create(Registries.BLOCK, MetalBarrels.id(name));
    }


}
