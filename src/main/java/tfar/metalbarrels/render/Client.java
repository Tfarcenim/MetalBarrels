package tfar.metalbarrels.render;

import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.init.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MetalBarrels.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class Client {
  @SubscribeEvent
  public static void doClientStuff(final FMLClientSetupEvent event) {
    //ClientRegistry.bindTileEntityRenderer(MetalBarrels.ObjectHolders.CRYSTAL_TILE, CrystalBarrelTileSpecialRenderer::new);
    //ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_BARREL, RenderType.cutout());
  }
}
