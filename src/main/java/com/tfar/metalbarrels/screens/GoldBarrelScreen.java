package com.tfar.metalbarrels.screens;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.GoldBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GoldBarrelScreen extends AbstractBarrelScreen<GoldBarrelContainer> {


  private static final ResourceLocation GOLD = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/gold.png");
  public GoldBarrelScreen(GoldBarrelContainer goldBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(goldBarrelContainer, playerInventory, component, GOLD, 176, 276);
  }


  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 94, 0x404040);
    this.font.drawString(this.title.getFormattedText(), 8, 6, 0x404040);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   */
  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
    int i = (this.width - this.xSize) / 2;
    int j = (this.height - this.ySize) / 2;
    blit(i, j, 0, 0, this.xSize, this.ySize, 256, 512);
  }
}
