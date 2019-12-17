package com.tfar.metalbarrels.render;

import com.google.common.primitives.SignedBytes;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.tfar.metalbarrels.tile.CrystalBarrelTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class CrystalBarrelTileSpecialRenderer extends TileEntityRenderer<CrystalBarrelTile> {
  private static ItemEntity customItem;

  private Random random = new Random();

  private ItemRenderer itemRenderer;

  private static float[][] shifts = {{0.3F, 0.45F, 0.3F}, {0.7F, 0.45F, 0.3F}, {0.3F, 0.45F, 0.7F}, {0.7F, 0.45F, 0.7F}, {0.3F, 0.1F, 0.3F}, {0.7F, 0.1F, 0.3F}, {0.3F, 0.1F, 0.7F}, {0.7F, 0.1F, 0.7F}, {0.5F, 0.32F, 0.5F}};

  public CrystalBarrelTileSpecialRenderer(TileEntityRendererDispatcher p_i226006_1_) {
    super(p_i226006_1_);
  }

  @Override
  public void func_225616_a_(CrystalBarrelTile crystalTile, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {

    if (this.field_228858_b_.renderInfo != null) {
      if (crystalTile.getDistanceSq(this.field_228858_b_.renderInfo.getProjectedView().x, this.field_228858_b_.renderInfo.getProjectedView().y, this.field_228858_b_.renderInfo.getProjectedView().z) < 128d) {
        this.random.setSeed(254L);
        int shift = 0;

        if (crystalTile.topStacks.get(1).isEmpty()) {
          shift = 8;
        }

        if (customItem == null) {
          customItem = new ItemEntity(EntityType.ITEM, crystalTile.getWorld());
        }

        for (ItemStack item : crystalTile.topStacks) {
          if (shift > shifts.length || shift > 8) {
            break;
          }

          if (item.isEmpty()) {
            shift++;
            continue;
          }

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

          this.itemRenderer.func_225623_a_(customItem, 0f, 0f, matrixStack, iRenderTypeBuffer,1);

        }
      }
    }
  }
}
