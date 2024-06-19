package tfar.metalbarrels.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntityFabric;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.item.BarrelUpgradeItemFabric;
import tfar.metalbarrels.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import tfar.metalbarrels.util.BarrelHandler;
import tfar.metalbarrels.util.BarrelHandlerFabric;
import tfar.metalbarrels.util.UpgradeInfo;

import java.lang.reflect.Field;
import java.util.Locale;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <F> void registerAll(Class<?> clazz, Registry<? super F> registry, Class<? super F> filter) {
        for (Field field : clazz.getFields()) {
            try {
                Object o = field.get(null);
                if (filter.isInstance(o)) {
                    Registry.register((Registry<? super F>) registry, MetalBarrels.id(field.getName().toLowerCase(Locale.ROOT)),(F)o);
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    @Override
    public <F> F register(Registry<F> registry, F f, ResourceLocation name) {
        Registry.register(registry, name,f);
        return f;
    }

    @Override
    public MetalBarrelBlockEntity<?> blockEntity(BlockEntityType<MetalBarrelBlockEntity<?>> type, BlockPos pos, BlockState state) {
        return new MetalBarrelBlockEntityFabric(type,pos,state);
    }

    @Override
    public <H extends BarrelHandler> H makeDummy(int slots) {
        return (H) new BarrelHandlerFabric(slots,null);
    }

    @Override
    public BarrelUpgradeItem createUpgrade(Item.Properties properties, UpgradeInfo info) {
        return new BarrelUpgradeItemFabric(properties, info);
    }
}
