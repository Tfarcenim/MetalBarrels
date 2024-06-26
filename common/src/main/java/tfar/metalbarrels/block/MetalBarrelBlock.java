package tfar.metalbarrels.block;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tfar.metalbarrels.util.BarrelProperties;

import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public class MetalBarrelBlock extends BarrelBlock {

  protected final BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> tileEntitySupplier;
  private final BarrelProperties barrelProperties;

  public MetalBarrelBlock(Properties properties, BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity<?>> tileEntitySupplier, BarrelProperties barrelProperties) {
    super(properties);
    this.tileEntitySupplier = tileEntitySupplier;
    this.barrelProperties = barrelProperties;
  }

  @Override
  public void onRemove(BlockState state,Level worldIn, BlockPos pos,BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity tileentity = worldIn.getBlockEntity(pos);
      if (tileentity instanceof MetalBarrelBlockEntity) {
        dropItems((MetalBarrelBlockEntity<?>)tileentity,worldIn, pos);
        worldIn.updateNeighbourForOutputSignal(pos, this);
      }
      super.onRemove(state, worldIn, pos, newState, isMoving);
    }
  }

  public static void dropItems(MetalBarrelBlockEntity<?> barrel, Level world, BlockPos pos) {
    IntStream.range(0, barrel.barrelHandler.$getSlotCount()).mapToObj(barrel.barrelHandler::$getStack)
            .filter(stack -> !stack.isEmpty()).forEach(stack -> Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack));
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (!level.isClientSide) {
      MenuProvider tileEntity = getMenuProvider(state,level,pos);
      if (tileEntity != null) {
        player.openMenu(tileEntity);
        player.awardStat(Stats.OPEN_BARREL);
        PiglinAi.angerNearbyPiglins(player, true);
      }
      return InteractionResult.CONSUME;
    } else {
      return InteractionResult.SUCCESS;
    }
  }

  public BarrelProperties getBarrelProperties() {
    return barrelProperties;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos,BlockState state) {
    return tileEntitySupplier.create(pos, state);
  }

  /**
   * @deprecated call via {@link BlockState#hasAnalogOutputSignal()} ()} whenever possible. Implementing/overriding
   * is fine.
   */
  @Override
  public boolean hasAnalogOutputSignal(BlockState state) {
    return true;
  }

  /**
   * @deprecated call via {@link BlockState#getAnalogOutputSignal(Level, BlockPos)} (World,BlockPos)} whenever possible.
   * Implementing/overriding is fine.
   */
  @Override
  public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
    BlockEntity barrel = level.getBlockEntity(pos);
    return barrel instanceof MetalBarrelBlockEntity metalBarrelBlockEntity? metalBarrelBlockEntity.calculateRedstone() : 0;
  }

  @Override
  public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
    BlockEntity blockentity = pLevel.getBlockEntity(pPos);
    if (blockentity instanceof MetalBarrelBlockEntity metalBarrelBlockEntity) {
      metalBarrelBlockEntity.recheckOpen();
    }
  }
}
