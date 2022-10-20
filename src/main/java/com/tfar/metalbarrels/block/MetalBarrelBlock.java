package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tile.MetalBarrelBlockEntity;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

import net.minecraft.block.AbstractBlock.Properties;

@SuppressWarnings("deprecation")
public class MetalBarrelBlock extends BarrelBlock {

  protected final Supplier<TileEntity> tileEntitySupplier;

  public MetalBarrelBlock(Properties properties, Supplier<TileEntity> tileEntitySupplier) {
    super(properties);
    this.tileEntitySupplier = tileEntitySupplier;
  }

  @Override
  public void onRemove(BlockState state, @Nonnull World worldIn,@Nonnull BlockPos pos,@Nonnull BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      TileEntity tileentity = worldIn.getBlockEntity(pos);
      if (tileentity instanceof MetalBarrelBlockEntity) {
        dropItems((MetalBarrelBlockEntity)tileentity,worldIn, pos);
        worldIn.updateNeighbourForOutputSignal(pos, this);
      }
      super.onRemove(state, worldIn, pos, newState, isMoving);
    }
  }

  public static void dropItems(MetalBarrelBlockEntity barrel, World world, BlockPos pos) {
    IntStream.range(0, barrel.handler.getSlots()).mapToObj(barrel.handler::getStackInSlot)
            .filter(stack -> !stack.isEmpty()).forEach(stack -> dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack));
  }

  @Override
  public ActionResultType use(BlockState state, World world, BlockPos pos,
                                         PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    if (!world.isClientSide) {
      INamedContainerProvider tileEntity = getMenuProvider(state,world,pos);
      if (tileEntity != null) {
        MetalBarrelBlockEntity metalBarrelBlockEntity = (MetalBarrelBlockEntity)tileEntity;
        world.setBlock(pos, state.setValue(BarrelBlock.OPEN, true), 3);
        if (metalBarrelBlockEntity.players == 0) {
          metalBarrelBlockEntity.soundStuff(state, SoundEvents.BARREL_OPEN);
          metalBarrelBlockEntity.changeState(state, true);
        }
        metalBarrelBlockEntity.players++;
        player.openMenu(tileEntity);
        player.awardStat(Stats.OPEN_BARREL);
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return tileEntitySupplier.get();
  }

  /**
   * @deprecated call via {@link BlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
   * is fine.
   */
  @Override
  public boolean hasAnalogOutputSignal(BlockState state) {
    return true;
  }

  /**
   * @deprecated call via {@link BlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
   * Implementing/overriding is fine.
   */
  @Override
  public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
    TileEntity barrel = world.getBlockEntity(pos);
    return barrel instanceof MetalBarrelBlockEntity ? ItemHandlerHelper.calcRedstoneFromInventory(((MetalBarrelBlockEntity) barrel).handler) : 0;
  }

  @Override
  public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    if (stack.hasCustomHoverName()) {
      TileEntity tileentity = worldIn.getBlockEntity(pos);
      if (tileentity instanceof MetalBarrelBlockEntity) {
        ((MetalBarrelBlockEntity)tileentity).setCustomName(stack.getHoverName());
      }
    }
  }

  @Nullable
  @Override
  public ToolType getHarvestTool(BlockState state) {
    return ToolType.PICKAXE;
  }
}
