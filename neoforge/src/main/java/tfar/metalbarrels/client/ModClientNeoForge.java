package tfar.metalbarrels.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ModClientNeoForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientNeoForge::setup);
    }

    static void setup(FMLClientSetupEvent event) {
        ModClient.setup();
    }
}
