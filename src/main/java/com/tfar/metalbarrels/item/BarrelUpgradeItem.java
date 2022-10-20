package com.tfar.metalbarrels.item;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import net.minecraft.item.Item.Properties;

public class BarrelUpgradeItem extends Item {

  protected final UpgradeInfo upgradeInfo;

  public BarrelUpgradeItem(Properties properties, UpgradeInfo info) {
    super(properties);
    this.upgradeInfo = info;
  }

  public static final Method method;

  static {
    method = ObfuscationReflectionHelper.findMethod(ChestTileEntity.class,"getItems");//getItems
  }

  private static final ITextComponent s = new TranslationTextComponent("tooltip.metalbarrels.ironchest").withStyle(TextFormatting.GREEN);

  public static boolean IRON_CHESTS_LOADED;

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    if (IRON_CHESTS_LOADED) {
      tooltip.add(s);
    }
  }

  @Nonnull
  @Override
  public ActionResultType useOn(ItemUseContext context) {
    PlayerEntity player = context.getPlayer();
    BlockPos pos = context.getClickedPos();
    World world = context.getLevel();
    ItemStack heldStack = context.getItemInHand();
    BlockState state = world.getBlockState(pos);

    if (player == null || !upgradeInfo.canUpgrade(world.getBlockState(pos).getBlock())) {
      return ActionResultType.FAIL;
    }
    if (world.isClientSide || player.getPose() != Pose.CROUCHING)
      return ActionResultType.PASS;

    if (state.getBlock() instanceof BarrelBlock)
    if (state.getValue(BlockStateProperties.OPEN)) {
      player.displayClientMessage(new TranslationTextComponent("metalbarrels.in_use")
              .withStyle(Style.EMPTY.applyFormat(TextFormatting.RED)), true);
      return ActionResultType.PASS;
    }

    TileEntity oldBarrel = world.getBlockEntity(pos);
    final List<ItemStack> oldBarrelContents = new ArrayList<>();

    Direction facing = Direction.NORTH;
    if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
      facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
    } else if (state.hasProperty(BlockStateProperties.FACING)) {
      facing = state.getValue(BlockStateProperties.FACING);
    }

    if (oldBarrel instanceof ChestTileEntity) {
      try {
        oldBarrelContents.addAll((Collection<? extends ItemStack>) method.invoke(oldBarrel));
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    } else oldBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            .ifPresent((itemHandler) -> IntStream.range(0, itemHandler.getSlots())
                    .mapToObj(itemHandler::getStackInSlot).forEach(oldBarrelContents::add));
    oldBarrel.setRemoved();

    Block newBlock = upgradeInfo.getBlock(state.getBlock());

    BlockState newState = newBlock.defaultBlockState();

    if (newState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
      newState = newState.setValue(BlockStateProperties.HORIZONTAL_FACING,facing);
    } else if (newState.hasProperty(BlockStateProperties.FACING)){
      newState = newState.setValue(BlockStateProperties.FACING,facing);
    }

    world.setBlock(pos, newState, 3);
    TileEntity newBarrel = world.getBlockEntity(pos);

    newBarrel.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((itemHandler) -> IntStream.range(0, oldBarrelContents.size()).forEach(i -> itemHandler.insertItem(i, oldBarrelContents.get(i), false)));

    if (!player.abilities.instabuild)
      heldStack.shrink(1);

    player.displayClientMessage(new TranslationTextComponent("metalbarrels.upgrade_successful")
            .withStyle(Style.EMPTY.applyFormat(TextFormatting.GREEN)), true);
    return ActionResultType.SUCCESS;
  }
}