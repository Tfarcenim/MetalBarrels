package tfar.metalbarrels.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.metalbarrels.init.ModMenuTypes;

public class ModClient {

    public static void setup() {
        MenuScreens.register(ModMenuTypes.COPPER, MetalBarrelScreen::copper);
        MenuScreens.register(ModMenuTypes.IRON, MetalBarrelScreen::iron);
        MenuScreens.register(ModMenuTypes.SILVER, MetalBarrelScreen::silver);
        MenuScreens.register(ModMenuTypes.GOLD, MetalBarrelScreen::gold);
        MenuScreens.register(ModMenuTypes.DIAMOND, MetalBarrelScreen::diamond);
        MenuScreens.register(ModMenuTypes.NETHERITE, MetalBarrelScreen::netherite);
    }

}
