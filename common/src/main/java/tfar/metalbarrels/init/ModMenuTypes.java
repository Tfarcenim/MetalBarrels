package tfar.metalbarrels.init;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<MetalBarrelContainer> COPPER = new MenuType<>(MetalBarrelContainer::copper, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelContainer> IRON = new MenuType<>(MetalBarrelContainer::iron, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelContainer> GOLD = new MenuType<>(MetalBarrelContainer::gold, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelContainer> DIAMOND = new MenuType<>(MetalBarrelContainer::diamond, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelContainer> NETHERITE = new MenuType<>(MetalBarrelContainer::netherite, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelContainer> SILVER = new MenuType<>(MetalBarrelContainer::silver, FeatureFlagSet.of(FeatureFlags.VANILLA));

}
