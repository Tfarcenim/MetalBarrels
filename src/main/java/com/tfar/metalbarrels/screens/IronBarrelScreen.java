package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;

import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class IronBarrelScreen extends AbstractBarrelScreen<MetalBarrelContainer> {


  private static final ResourceLocation IRON = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/iron.png");
  public IronBarrelScreen(MetalBarrelContainer ironBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(ironBarrelContainer, playerInventory, component, IRON, 176, 222);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   */
  @Override
  protected void func_230450_a_(MatrixStack stack,float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(stack,partialTicks,mouseX,mouseY);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    this.blit(stack,i, j, 0, 0, this.xSize, this.ySize);
  }
}
