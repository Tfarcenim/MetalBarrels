package tfar.metalbarrels.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.menu.MetalBarrelMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class MetalBarrelScreen extends AbstractContainerScreen<MetalBarrelMenu> {

  private final ResourceLocation texture;

  private final boolean isTall;

  private final boolean isWide;

  public MetalBarrelScreen(MetalBarrelMenu barrelMenu, Inventory playerInventory, Component component,
                           ResourceLocation texture, int xSize, int ySize) {
    super(barrelMenu, playerInventory, component);
    this.imageWidth = xSize;
    this.imageHeight = ySize;
    this.texture = texture;
    this.inventoryLabelY = this.imageHeight - 94;
    isTall = barrelMenu.height > 6;
    isWide = barrelMenu.width > 12;
  }

  @Override
  public void render(GuiGraphics stack,int x, int y, float p_render_3_) {
    this.renderBackground(stack);
    super.render(stack,x, y, p_render_3_);
    this.renderTooltip(stack,x,y);
  }

  /**
   * Draws the background layer of this container (behind the items).
   *
   * @param partialTicks
   * @param mouseX
   * @param mouseY
   */
  @Override
  protected void renderBg(GuiGraphics stack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShaderTexture(0,texture);
    int i = (this.width - this.imageWidth) / 2;
    int j = (this.height - this.imageHeight) / 2;
    if (!isTall) {
      stack.blit(texture,i, j, 0, 0, this.imageWidth, this.imageHeight);
    } else if (!isWide) {
      stack.blit(texture,i, j, 0,0, 0, this.imageWidth, this.imageHeight,256,512);
    } else {
      stack.blit(texture,i, j,0, 0, 0, this.imageWidth, this.imageHeight,512,512);
    }
  }

  private static final ResourceLocation COPPER = MetalBarrels.id("textures/gui/container/copper.png");
  private static final ResourceLocation IRON = MetalBarrels.id("textures/gui/container/iron.png");
  private static final ResourceLocation SILVER = MetalBarrels.id("textures/gui/container/silver.png");
  private static final ResourceLocation GOLD = MetalBarrels.id("textures/gui/container/gold.png");
  private static final ResourceLocation DIAMOND = MetalBarrels.id("textures/gui/container/diamond.png");
  private static final ResourceLocation NETHERITE = MetalBarrels.id("textures/gui/container/netherite.png");


  public static MetalBarrelScreen copper(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,COPPER,176,204);
  }

  public static MetalBarrelScreen iron(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,IRON,176,222);
  }

  public static MetalBarrelScreen silver(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,SILVER,176,258);
  }

  public static MetalBarrelScreen gold(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,GOLD,176,276);
  }

  public static MetalBarrelScreen diamond(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,DIAMOND,230,276);
  }

  public static MetalBarrelScreen netherite(MetalBarrelMenu barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,NETHERITE,284,276);
  }

}
