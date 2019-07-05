package com.tfar.metalbarrels.block;

import com.tfar.metalbarrels.tiles.AbstractBarrelTile;
import com.tfar.metalbarrels.utils.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public abstract class AbstractBarrelBlock extends Block {

  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

  public AbstractBarrelBlock(Properties properties) {
    super(properties);
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(OPEN, false));
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

      for (int i = 0; i < barrel.handler.getSlots(); ++i) {
        ItemStack stack = barrel.handler.getStackInSlot(i);

        if (!stack.isEmpty()) {
          InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
      }
    }
  }

  @Override
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    if (!world.isRemote) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity instanceof INamedContainerProvider) {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
        player.addStat(Stats.OPEN_BARREL);
      } else {
        throw new IllegalStateException("Our named container provider is missing!");
      }
    }
    return true;
  }

  @Override
  public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
    TileEntity te = world.getTileEntity(pos);
    return te instanceof AbstractBarrelTile ? (INamedContainerProvider) te : null;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return null;
  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
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
  public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
    return InventoryHelper.calcRedstone(worldIn.getTileEntity(pos));
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
    if (stack.hasDisplayName()) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof AbstractBarrelTile) {
       // ((AbstractBarrelTile)tileentity).setCustomName(stack.getDisplayName());
      }
    }
  }

  /**
   * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
   * blockstate.
   * @deprecated call via {@link BlockState#rotate(Rotation)} whenever possible. Implementing/overriding is
   * fine.
   */
  @Nonnull
  @Override
  public BlockState rotate(BlockState state, Rotation rot) {
    return state.with(FACING, rot.rotate(state.get(FACING)));
  }

  /**
   * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
   * blockstate.
   * @deprecated call via {@link BlockState#mirror(Mirror)} whenever possible. Implementing/overriding is fine.
   */
  @Nonnull
  @Override
  public BlockState mirror(BlockState state, Mirror mirrorIn) {
    return state.rotate(mirrorIn.toRotation(state.get(FACING)));
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(FACING, OPEN);
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
