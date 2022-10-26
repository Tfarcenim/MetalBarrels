package tfar.metalbarrels.init;

import tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<MetalBarrelContainer> COPPER = new MenuType<>(MetalBarrelContainer::copper);
    public static final MenuType<MetalBarrelContainer> IRON = new MenuType<>(MetalBarrelContainer::iron);
    public static final MenuType<MetalBarrelContainer> GOLD = new MenuType<>(MetalBarrelContainer::gold);
    public static final MenuType<MetalBarrelContainer> DIAMOND = new MenuType<>(MetalBarrelContainer::diamond);
    public static final MenuType<MetalBarrelContainer> NETHERITE = new MenuType<>(MetalBarrelContainer::netherite);
    public static final MenuType<MetalBarrelContainer> SILVER = new MenuType<>(MetalBarrelContainer::silver);

}
