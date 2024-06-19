package tfar.metalbarrels.init;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import tfar.metalbarrels.menu.MetalBarrelMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<MetalBarrelMenu> COPPER = new MenuType<>(MetalBarrelMenu::copper, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelMenu> IRON = new MenuType<>(MetalBarrelMenu::iron, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelMenu> GOLD = new MenuType<>(MetalBarrelMenu::gold, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelMenu> DIAMOND = new MenuType<>(MetalBarrelMenu::diamond, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelMenu> NETHERITE = new MenuType<>(MetalBarrelMenu::netherite, FeatureFlagSet.of(FeatureFlags.VANILLA));
    public static final MenuType<MetalBarrelMenu> SILVER = new MenuType<>(MetalBarrelMenu::silver, FeatureFlagSet.of(FeatureFlags.VANILLA));

}
