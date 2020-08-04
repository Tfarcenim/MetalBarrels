package com.tfar.metalbarrels.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MetalBarrelScreen extends ContainerScreen<MetalBarrelContainer> {

  private final ResourceLocation texture;

  private final boolean isTall;

  private final boolean isWide;

  public MetalBarrelScreen(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component,
                           ResourceLocation texture, int xSize, int ySize) {
    super(barrelContainer, playerInventory, component);
    this.xSize = xSize;
    this.ySize = ySize;
    this.texture = texture;
    this.playerInventoryTitleY = this.ySize - 94;
    isTall = barrelContainer.height > 6;
    isWide = barrelContainer.width > 12;
  }

  @Override
  public void render(MatrixStack stack,int x, int y, float p_render_3_) {
    this.renderBackground(stack);
    super.render(stack,x, y, p_render_3_);
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
  protected void drawGuiContainerBackgroundLayer(MatrixStack stack,float partialTicks, int mouseX, int mouseY) {
    this.minecraft.getTextureManager().bindTexture(texture);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    if (!isTall) {
      this.blit(stack,i, j, 0, 0, this.xSize, this.ySize);
    } else if (!isWide) {
      blit(stack,i, j, 0, 0,getBlitOffset(), this.xSize, this.ySize,512,256);
    } else {
      blit(stack,i, j, 0, 0,getBlitOffset(), this.xSize, this.ySize,512,512);
    }
  }

  private static final ResourceLocation COPPER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/copper.png");
  private static final ResourceLocation IRON = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/iron.png");
  private static final ResourceLocation SILVER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/silver.png");
  private static final ResourceLocation GOLD = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/gold.png");
  private static final ResourceLocation DIAMOND = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/diamond.png");
  private static final ResourceLocation NETHERITE = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/netherite.png");


  public static MetalBarrelScreen copper(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,COPPER,176,204);
  }

  public static MetalBarrelScreen iron(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,IRON,176,222);
  }

  public static MetalBarrelScreen silver(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,SILVER,176,258);
  }

  public static MetalBarrelScreen gold(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,GOLD,176,276);
  }

  public static MetalBarrelScreen diamond(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,DIAMOND,230,276);
  }

  public static MetalBarrelScreen netherite(MetalBarrelContainer barrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,NETHERITE,284,276);
  }

}
