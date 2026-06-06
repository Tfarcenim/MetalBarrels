package tfar.metalbarrels.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.menu.MetalBarrelMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;

public class MetalBarrelScreen extends AbstractContainerScreen<MetalBarrelMenu> {

  private final Identifier texture;

  private final boolean isTall;

  private final boolean isWide;

  public MetalBarrelScreen(MetalBarrelMenu barrelMenu, Inventory playerInventory, Component component,
                           Identifier texture, int xSize, int ySize) {
    super(barrelMenu, playerInventory, component,xSize,ySize);
    this.texture = texture;
    this.inventoryLabelY = this.imageHeight - 94;
    isTall = barrelMenu.height > 6;
    isWide = barrelMenu.width > 12;
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractBackground(graphics, mouseX, mouseY, a);

    int i = (this.width - this.imageWidth) / 2;
    int j = (this.height - this.imageHeight) / 2;
    if (!isTall) {
      graphics.blit(RenderPipelines.GUI_TEXTURED,texture,i, j, 0, 0, this.imageWidth, this.imageHeight,256,256);
    } else if (!isWide) {
      graphics.blit(RenderPipelines.GUI_TEXTURED,texture,i, j, 0,0, 0, this.imageWidth, this.imageHeight,256,512);
    } else {
      graphics.blit(RenderPipelines.GUI_TEXTURED,texture,i, j,0, 0, 0, this.imageWidth, this.imageHeight,512,512);
    }
  }

  private static final Identifier COPPER = MetalBarrels.id("textures/gui/container/copper.png");
  private static final Identifier IRON = MetalBarrels.id("textures/gui/container/iron.png");
  private static final Identifier SILVER = MetalBarrels.id("textures/gui/container/silver.png");
  private static final Identifier GOLD = MetalBarrels.id("textures/gui/container/gold.png");
  private static final Identifier DIAMOND = MetalBarrels.id("textures/gui/container/diamond.png");
  private static final Identifier NETHERITE = MetalBarrels.id("textures/gui/container/netherite.png");


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
