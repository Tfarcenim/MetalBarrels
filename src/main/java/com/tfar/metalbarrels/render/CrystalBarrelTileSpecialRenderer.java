package com.tfar.metalbarrels.render;

import com.google.common.primitives.SignedBytes;
import com.mojang.blaze3d.platform.GlStateManager;
import com.tfar.metalbarrels.tile.CrystalBarrelTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class CrystalBarrelTileSpecialRenderer extends TileEntityRenderer<CrystalBarrelTile> {
  private static ItemEntity customItem;

  private Random random = new Random();

  private ItemRenderer itemRenderer;

  private static float[][] shifts = {{0.3F, 0.45F, 0.3F}, {0.7F, 0.45F, 0.3F}, {0.3F, 0.45F, 0.7F}, {0.7F, 0.45F, 0.7F}, {0.3F, 0.1F, 0.3F}, {0.7F, 0.1F, 0.3F}, {0.3F, 0.1F, 0.7F}, {0.7F, 0.1F, 0.7F}, {0.5F, 0.32F, 0.5F}};

  @Override
  public void render(CrystalBarrelTile crystalTile, double x, double y, double z, float partialTicks, int destroyStage) {
    GlStateManager.enableDepthTest();
    GlStateManager.depthFunc(515);
    GlStateManager.depthMask(true);

    GlStateManager.pushMatrix();

    {
      GlStateManager.disableCull();
    }

    GlStateManager.enableRescaleNormal();
    GlStateManager.translatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
    GlStateManager.scalef(1.0F, -1.0F, -1.0F);

    {
      GlStateManager.scalef(1F, 0.99F, 1F);
    }

    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();

    {
      GlStateManager.enableCull();
    }

    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    if (destroyStage >= 0) {
      GlStateManager.matrixMode(5890);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
    }

    if (this.rendererDispatcher.renderInfo != null) {
      if (crystalTile.getDistanceSq(this.rendererDispatcher.renderInfo.getProjectedView().x, this.rendererDispatcher.renderInfo.getProjectedView().y, this.rendererDispatcher.renderInfo.getProjectedView().z) < 128d) {
        this.random.setSeed(254L);
        float shiftX;
        float shiftY;
        float shiftZ;
        int shift = 0;
        float blockScale = 0.70F;
        float timeD = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTicks;

        if (crystalTile.topStacks.get(1).isEmpty()) {
          shift = 8;
          blockScale = 0.85F;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x, (float) y, (float) z);

        if (customItem == null) {
          customItem = new ItemEntity(EntityType.ITEM, this.getWorld());
        }

        for (ItemStack item : crystalTile.topStacks) {
          if (shift > shifts.length || shift > 8) {
            break;
          }

          if (item.isEmpty()) {
            shift++;
            continue;
          }

          shiftX = shifts[shift][0];
          shiftY = shifts[shift][1];
          shiftZ = shifts[shift][2];
          shift++;

          GlStateManager.pushMatrix();
          GlStateManager.translatef(shiftX, shiftY, shiftZ);
          GlStateManager.rotatef(timeD, 0F, 1F, 0F);
          GlStateManager.scalef(blockScale, blockScale, blockScale);

          customItem.setItem(item);

          if (this.itemRenderer == null) {
            this.itemRenderer = new ItemRenderer(Minecraft.getInstance().getRenderManager(), Minecraft.getInstance().getItemRenderer()) {
              @Override
              public int getModelCount(ItemStack stack) {
                return SignedBytes.saturatedCast(Math.min(stack.getCount() / 32, 15) + 1);
              }

              @Override
              public boolean shouldBob() {
                return false;
              }

              @Override
              public boolean shouldSpreadItems() {
                return true;
              }
            };
          }

          this.itemRenderer.doRender(customItem, 0D, 0D, 0D, 0F, partialTicks);

          GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
      }
    }
  }

}
