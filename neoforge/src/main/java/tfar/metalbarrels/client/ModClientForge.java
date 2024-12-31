package tfar.metalbarrels.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.metalbarrels.init.ModBlocks;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::setup);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_BARREL, RenderType.cutoutMipped());
    }

    static void setup(FMLClientSetupEvent event) {
        ModClient.setup();
    }

}
