package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SilverBarrelScreen extends AbstractBarrelScreen<MetalBarrelContainer> {


  private static final ResourceLocation SILVER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/silver.png");
  public SilverBarrelScreen(MetalBarrelContainer silverBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(silverBarrelContainer, playerInventory, component, SILVER, 176, 264);
  }

  @Override
  protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(stack,partialTicks,mouseX,mouseY);
    int i = (this.field_230708_k_ - this.xSize) / 2;
    int j = (this.field_230709_l_ - this.ySize) / 2;
    func_238464_a_(stack,i, j, 0, 0, this.xSize, this.ySize,0,256,512);
  }
}
