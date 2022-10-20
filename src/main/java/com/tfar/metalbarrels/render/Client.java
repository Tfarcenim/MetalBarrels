package com.tfar.metalbarrels.render;

import com.tfar.metalbarrels.MetalBarrels;
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
    ItemBlockRenderTypes.setRenderLayer(MetalBarrels.ObjectHolders.CRYSTAL_BARREL, RenderType.cutout());
  }
}
