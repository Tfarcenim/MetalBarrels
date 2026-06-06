package tfar.metalbarrels.platform;

import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.item.BarrelUpgradeItemForge;
import tfar.metalbarrels.platform.services.IPlatformHelper;
import tfar.metalbarrels.util.UpgradeInfo;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public BarrelUpgradeItem createUpgrade(Item.Properties properties, UpgradeInfo info) {
        return new BarrelUpgradeItemForge(properties, info);
    }
}