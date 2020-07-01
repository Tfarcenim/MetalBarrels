package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractBarrelScreen<T extends MetalBarrelContainer> extends ContainerScreen<T> {

  private final ResourceLocation texture;

  public AbstractBarrelScreen(T barrelContainer, PlayerInventory playerInventory, ITextComponent component,
                              ResourceLocation texture, int width, int height) {
    super(barrelContainer, playerInventory, component);
    this.xSize = width;
    this.ySize = height;
    this.texture = texture;
    this.field_238745_s_ = this.ySize - 94;
  }

  @Override
  public void func_230430_a_(MatrixStack stack,int x, int y, float p_render_3_) {
    this.func_230446_a_(stack);
    super.func_230430_a_(stack,x, y, p_render_3_);
    this.func_230459_a_(stack,x,y);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   * @param partialTicks
   * @param mouseX
   * @param mouseY
   */
  @Override
  protected void func_230450_a_(MatrixStack stack,float partialTicks, int mouseX, int mouseY) {
    this.field_230706_i_.getTextureManager().bindTexture(texture);
  }
}
