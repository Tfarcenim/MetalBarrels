package tfar.metalbarrels.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.MetalBarrelsForge;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntityForge;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.item.BarrelUpgradeItemForge;
import tfar.metalbarrels.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import tfar.metalbarrels.util.BarrelHandler;
import tfar.metalbarrels.util.BarrelHandlerForge;
import tfar.metalbarrels.util.UpgradeInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public <F> void registerAll(Class<?> clazz, Registry<? super F> registry, Class<? super F> filter) {
        List<Pair<ResourceLocation, Supplier<?>>> list = MetalBarrelsForge.registerLater.computeIfAbsent(registry, k -> new ArrayList<>());
        for (Field field : clazz.getFields()) {
            MappedRegistry<?> mappedRegistry = (MappedRegistry<?>) registry;
            mappedRegistry.unfreeze();
            try {
                Object o = field.get(null);
                if (filter.isInstance(o)) {
                    list.add(Pair.of(MetalBarrels.id(field.getName().toLowerCase(Locale.ROOT)), () -> o));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    @Override
    public <F> F register(Registry<F> registry, F f, ResourceLocation name) {
        List<Pair<ResourceLocation, Supplier<?>>> list = MetalBarrelsForge.registerLater.computeIfAbsent(registry, k -> new ArrayList<>());
        MappedRegistry<?> mappedRegistry = (MappedRegistry<?>) registry;
        mappedRegistry.unfreeze();
        list.add(Pair.of(name,() -> f));
        return f;
    }

    @Override
    public MetalBarrelBlockEntity<?> blockEntity(BlockEntityType<MetalBarrelBlockEntity<?>> type, BlockPos pos, BlockState state) {
        return new MetalBarrelBlockEntityForge(type, pos, state);
    }

    @Override
    public <H extends BarrelHandler> H makeDummy(int slots) {
        return (H)new BarrelHandlerForge(slots,null);
    }

    @Override
    public BarrelUpgradeItem createUpgrade(Item.Properties properties, UpgradeInfo info) {
        return new BarrelUpgradeItemForge(properties, info);
    }
}