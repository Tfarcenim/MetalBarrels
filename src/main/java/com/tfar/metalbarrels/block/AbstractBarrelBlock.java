package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tile.AbstractBarrelTile;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public abstract class AbstractBarrelBlock extends BarrelBlock {

  private static final Random RANDOM = new Random();

  public AbstractBarrelBlock(Properties properties) {
    super(properties);
   // this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OPEN, false));
  }

  @Override
  public void onReplaced(BlockState state, @Nonnull World worldIn,@Nonnull BlockPos pos,@Nonnull BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof AbstractBarrelTile) {
        dropItems((AbstractBarrelTile)tileentity,worldIn, pos);
        worldIn.updateComparatorOutputLevel(pos, this);
      }
      super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
  }

  public static void dropItems(AbstractBarrelTile barrel, World world, BlockPos pos) {
    IntStream.range(0, barrel.handler.getSlots()).mapToObj(i -> barrel.handler.getStackInSlot(i)).filter(stack -> !stack.isEmpty()).forEach(stack -> spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack));
  }

  public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
    double d0 = EntityType.ITEM.getWidth();
    double d1 = 1 - d0;
    double d2 = d0 / 2;
    double d3 = Math.floor(x) + RANDOM.nextDouble() * d1 + d2;
    double d4 = Math.floor(y) + RANDOM.nextDouble() * d1;
    double d5 = Math.floor(z) + RANDOM.nextDouble() * d1 + d2;

    while(!stack.isEmpty()) {
      ItemEntity itementity = new ItemEntity(worldIn, d3, d4, d5, stack.split(RANDOM.nextInt(21) + 10));
      float f = 0.05F;
      itementity.setMotion(RANDOM.nextGaussian() * f, RANDOM.nextGaussian() * f + 0.2, RANDOM.nextGaussian() * f);
      worldIn.addEntity(itementity);
    }
  }

  @Override
  public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos,
                                         PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    if (!world.isRemote) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity instanceof INamedContainerProvider) {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
        player.addStat(Stats.OPEN_BARREL);
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  /**
   * @deprecated call via {@link BlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
   * is fine.
   */
  @Override
  public boolean hasComparatorInputOverride(BlockState state) {
    return true;
  }

  /**
   * @deprecated call via {@link BlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
   * Implementing/overriding is fine.
   */
  @Override
  public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
    TileEntity barrel = world.getTileEntity(pos);
    return barrel instanceof AbstractBarrelTile ? ItemHandlerHelper.calcRedstoneFromInventory(((AbstractBarrelTile) barrel).handler) : 0;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    if (stack.hasDisplayName()) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof AbstractBarrelTile) {
        ((AbstractBarrelTile)tileentity).setCustomName(stack.getDisplayName());
      }
    }
  }

  @Override
  public int getHarvestLevel(BlockState state) {
    return 1;
  }

  @Nullable
  @Override
  public ToolType getHarvestTool(BlockState state) {
    return ToolType.PICKAXE;
  }
}
