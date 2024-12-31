package tfar.metalbarrels.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import tfar.metalbarrels.init.ModBlocks;

public class ModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModClient.setup();
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(), ModBlocks.CRYSTAL_BARREL);
    }
}
