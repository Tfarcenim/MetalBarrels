package tfar.metalbarrels.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.container.MetalBarrelContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class MetalBarrelScreen extends AbstractContainerScreen<MetalBarrelContainer> {

  private final ResourceLocation texture;

  private final boolean isTall;

  private final boolean isWide;

  public MetalBarrelScreen(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component,
                           ResourceLocation texture, int xSize, int ySize) {
    super(barrelContainer, playerInventory, component);
    this.imageWidth = xSize;
    this.imageHeight = ySize;
    this.texture = texture;
    this.inventoryLabelY = this.imageHeight - 94;
    isTall = barrelContainer.height > 6;
    isWide = barrelContainer.width > 12;
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

  private static final ResourceLocation COPPER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/copper.png");
  private static final ResourceLocation IRON = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/iron.png");
  private static final ResourceLocation SILVER = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/silver.png");
  private static final ResourceLocation GOLD = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/gold.png");
  private static final ResourceLocation DIAMOND = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/diamond.png");
  private static final ResourceLocation NETHERITE = new ResourceLocation(MetalBarrels.MODID,"textures/gui/container/netherite.png");


  public static MetalBarrelScreen copper(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,COPPER,176,204);
  }

  public static MetalBarrelScreen iron(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,IRON,176,222);
  }

  public static MetalBarrelScreen silver(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,SILVER,176,258);
  }

  public static MetalBarrelScreen gold(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,GOLD,176,276);
  }

  public static MetalBarrelScreen diamond(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,DIAMOND,230,276);
  }

  public static MetalBarrelScreen netherite(MetalBarrelContainer barrelContainer, Inventory playerInventory, Component component) {
    return new MetalBarrelScreen(barrelContainer,playerInventory,component,NETHERITE,284,276);
  }

}
