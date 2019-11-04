package com.tfar.metalbarrels.item;

import com.tfar.metalbarrels.MetalBarrels;
import com.tfar.metalbarrels.tile.AbstractBarrelTile;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BarrelUpgradeItem extends Item {

  protected final UpgradeInfo upgradeInfo;



  public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
    super(properties);
    this.upgradeInfo = info;
  }

  private static final ITextComponent s = new TranslationTextComponent("tooltip.metalbarrels.ironchest").applyTextStyle(TextFormatting.GREEN);

  public static boolean IRON_CHESTS_LOADED;

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    if (IRON_CHESTS_LOADED){
      tooltip.add(s);
    }
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

    if (state.getBlock() instanceof BarrelBlock)
    if (state.get(BlockStateProperties.OPEN)) {
      player.sendStatusMessage(new TranslationTextComponent("metalbarrels.in_use")
              .setStyle(new Style().setColor(TextFormatting.RED)), true);
      return ActionResultType.PASS;
    }

    TileEntity oldBarrel = world.getTileEntity(pos);
    final List<ItemStack> oldBarrelContents = new ArrayList<>();

    Direction facing = Direction.NORTH;
    if (state.has(BlockStateProperties.HORIZONTAL_FACING)){
      facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
    }else if (state.has(BlockStateProperties.FACING)){
      facing = state.get(BlockStateProperties.FACING);
    }

    if (oldBarrel instanceof AbstractBarrelTile)
      //shortcut
      oldBarrelContents.addAll(((AbstractBarrelTile) oldBarrel).handler.getContents());
    else oldBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            .ifPresent((itemHandler) -> IntStream.range(0, itemHandler.getSlots())
                    .mapToObj(itemHandler::getStackInSlot).forEach(oldBarrelContents::add));
    oldBarrel.remove();

    Block newBlock = upgradeInfo.getBlock(state.getBlock());

    BlockState newState = newBlock.getDefaultState();

    if (newState.has(BlockStateProperties.HORIZONTAL_FACING)){
      newState = newState.with(BlockStateProperties.HORIZONTAL_FACING,facing);
    }else if (newState.has(BlockStateProperties.FACING)){
      newState = newState.with(BlockStateProperties.FACING,facing);
    }

    world.setBlockState(pos, newState, 3);
    TileEntity newBarrel = world.getTileEntity(pos);

    newBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((itemHandler) -> IntStream.range(0, oldBarrelContents.size()).forEach(i -> itemHandler.insertItem(i, oldBarrelContents.get(i), false)));

    if (!player.abilities.isCreativeMode)
      heldStack.shrink(1);

    player.sendStatusMessage(new TranslationTextComponent("metalbarrels.upgrade_successful")
            .setStyle(new Style().setColor(TextFormatting.GREEN)), true);
    return ActionResultType.SUCCESS;
  }
}