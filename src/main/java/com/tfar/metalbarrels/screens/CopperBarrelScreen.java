package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CopperBarrelScreen extends AbstractBarrelScreen<MetalBarrelContainer> {


  private static final ResourceLocation COPPER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/copper.png");
  public CopperBarrelScreen(MetalBarrelContainer copperBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(copperBarrelContainer, playerInventory, component, COPPER, 176, 204);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   */
  @Override
  protected void func_230450_a_(MatrixStack stack,float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(stack,partialTicks,mouseX,mouseY);
    int i = (this.field_230708_k_ - this.xSize) / 2;
    int j = (this.field_230709_l_ - this.ySize) / 2;
    this.func_238474_b_(stack,i, j, 0, 0, this.xSize, this.ySize);
  }
}
