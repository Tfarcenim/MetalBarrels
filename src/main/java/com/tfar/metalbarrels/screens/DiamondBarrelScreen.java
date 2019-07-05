package com.tfar.metalbarrels.screens;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.container.DiamondBarrelContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DiamondBarrelScreen extends AbstractBarrelScreen<DiamondBarrelContainer> {


  private static final ResourceLocation DIAMOND = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/diamond.png");
  public DiamondBarrelScreen(DiamondBarrelContainer diamondBarrelContainer, PlayerInventory playerInventory, ITextComponent component) {
    super(diamondBarrelContainer, playerInventory, component, DIAMOND, 230, 276);
  }


  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 149, 0x404040);
    this.font.drawString(this.title.getFormattedText(), -19, -48, 0x404040);
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
    this.blit(i, j, 0, 0, this.xSize, this.ySize, 256, 512);
  }
}
