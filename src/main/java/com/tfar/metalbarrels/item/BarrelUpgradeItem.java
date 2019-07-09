package com.tfar.metalbarrels.item;

import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BarrelUpgradeItem extends Item {

  public final UpgradeInfo upgradeInfo;

  public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
    super(properties);
    this.upgradeInfo = info;
  }

  @Override
  public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockPos pos = context.getPos();
    World world = context.getWorld();
    ItemStack heldStack = context.getItem();
    BlockState state = world.getBlockState(pos);

    if (!upgradeInfo.canUpgrade(world.getBlockState(pos).getBlock())) {
      System.out.println(upgradeInfo.start_block.getAllElements());
      return ActionResultType.FAIL;

    }
    if (world.isRemote || !player.isSneaking())
      return ActionResultType.PASS;


    TileEntity tileEntity = world.getTileEntity(pos);

    AbstractBarrelTile newBarrel = upgradeInfo.tile;

    List<ItemStack> barrelContents = new ArrayList<>();


    if (tileEntity == null)return ActionResultType.PASS;

    if (tileEntity instanceof BarrelTileEntity) {
      BarrelTileEntity barrelEntity = (BarrelTileEntity) tileEntity;
      for (int i = 0; i < barrelEntity.getSizeInventory(); i++) {
        barrelContents.add(((BarrelTileEntity) tileEntity).getStackInSlot(i));
      }
    } else if (tileEntity instanceof AbstractBarrelTile) {
      AbstractBarrelTile barrelEntity = (AbstractBarrelTile) tileEntity;
          barrelContents = barrelEntity.handler.getContents();
      }
      tileEntity.updateContainingBlockInfo();

//    world.destroyBlock(blockPos,false);
      world.removeTileEntity(pos);

      BlockState newState = upgradeInfo.end_block.getDefaultState().with(BlockStateProperties.FACING, state.get(BlockStateProperties.FACING));

      world.setTileEntity(pos, newBarrel);
      world.setBlockState(pos, newState, 3);
      world.notifyBlockUpdate(pos, newState, newState, 3);

      TileEntity tileEntity2 = world.getTileEntity(pos);

    ((AbstractBarrelTile)tileEntity2).handler.setContents(barrelContents,((AbstractBarrelTile)tileEntity2).handler.getSlots());

    if (!player.abilities.isCreativeMode)
    heldStack.shrink(1);

    player.sendStatusMessage(new TranslationTextComponent("metalbarrels.upgrade_successful")
            .setStyle(new Style().setColor(TextFormatting.GREEN)),true);
    return ActionResultType.SUCCESS;
  }
}




