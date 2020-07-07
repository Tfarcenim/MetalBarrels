package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GoldBarrelScreen extends AbstractBarrelScreen<MetalBarrelContainer> {


  private static final ResourceLocation GOLD = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/gold.png");
  public GoldBarrelScreen(MetalBarrelContainer goldBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(goldBarrelContainer, playerInventory, component, GOLD, 176, 276);
  }

  @Override
  protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(stack,partialTicks,mouseX,mouseY);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    blit(stack,i, j, 0, 0,getBlitOffset(), this.xSize, this.ySize,512,256);
  }
}
