package com.tfar.metalbarrels.item;

import com.tfar.metalbarrels.tile.AbstractBarrelTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BarrelUpgradeItem extends Item {

  public final UpgradeInfo upgradeInfo;

  public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
    super(properties);
    this.upgradeInfo = info;
  }

  @Nonnull
  @Override
  public ActionResultType onItemUse(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockPos pos = context.getPos();
    World world = context.getWorld();
    ItemStack heldStack = context.getItem();
    BlockState state = world.getBlockState(pos);

    if (player == null || !upgradeInfo.canUpgrade(world.getBlockState(pos).getBlock())) {
      return ActionResultType.FAIL;
    }
    if (world.isRemote || !player.isSneaking())
      return ActionResultType.PASS;

    if (state.get(BlockStateProperties.OPEN)) {
      player.sendStatusMessage(new TranslationTextComponent("metalbarrels.in_use")
              .setStyle(new Style().setColor(TextFormatting.RED)), true);
      return ActionResultType.PASS;
    }

    TileEntity oldBarrel = world.getTileEntity(pos);
    final List<ItemStack> oldBarrelContents = new ArrayList<>();

    if (oldBarrel instanceof AbstractBarrelTile)
      oldBarrelContents.addAll(((AbstractBarrelTile) oldBarrel).handler.getContents());
    else oldBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            .ifPresent((itemHandler) -> IntStream.range(0, itemHandler.getSlots())
                    .mapToObj(itemHandler::getStackInSlot).forEach(oldBarrelContents::add));
    oldBarrel.remove();

    BlockState newState = upgradeInfo.end_block.getDefaultState().with(BlockStateProperties.FACING, state.get(BlockStateProperties.FACING));

    world.setBlockState(pos, newState, 3);
    TileEntity newBarrel = world.getTileEntity(pos);

    ((AbstractBarrelTile) newBarrel).handler.setContents(oldBarrelContents, ((AbstractBarrelTile) newBarrel).handler.getSlots());

    if (!player.abilities.isCreativeMode)
      heldStack.shrink(1);

    player.sendStatusMessage(new TranslationTextComponent("metalbarrels.upgrade_successful")
            .setStyle(new Style().setColor(TextFormatting.GREEN)), true);
    return ActionResultType.SUCCESS;
  }
}