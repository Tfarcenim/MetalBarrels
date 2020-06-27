package com.tfar.metalbarrels.screens;

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
  }

  @Override
  public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
    this.renderBackground();
    super.render(p_render_1_, p_render_2_, p_render_3_);
    this.renderHoveredToolTip(p_render_1_, p_render_2_);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   * @param partialTicks
   * @param mouseX
   * @param mouseY
   */
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    this.minecraft.getTextureManager().bindTexture(texture);
  }
}
