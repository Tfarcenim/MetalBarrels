package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NetheriteBarrelScreen extends AbstractBarrelScreen<MetalBarrelContainer> {


  private static final ResourceLocation NETHERITE = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/netherite.png");
  public NetheriteBarrelScreen(MetalBarrelContainer diamondBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(diamondBarrelContainer, playerInventory, component, NETHERITE, 284, 276);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   */
  @Override
  protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(stack,partialTicks,mouseX,mouseY);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    blit(stack,i, j, 0, 0,getBlitOffset(), this.xSize, this.ySize,512,512);
  }
}
