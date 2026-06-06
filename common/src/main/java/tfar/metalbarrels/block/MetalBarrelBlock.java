package tfar.metalbarrels.block;

import net.minecraft.core.Direction;
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

public class MetalBarrelBlock extends BarrelBlock {

  protected final BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity> tileEntitySupplier;
  private final BarrelProperties barrelProperties;

  public MetalBarrelBlock(Properties properties, BlockEntityType.BlockEntitySupplier<MetalBarrelBlockEntity> tileEntitySupplier, BarrelProperties barrelProperties) {
    super(properties);
    this.tileEntitySupplier = tileEntitySupplier;
    this.barrelProperties = barrelProperties;
  }

  public static void dropItems(MetalBarrelBlockEntity barrel, Level world, BlockPos pos) {
   // IntStream.range(0, barrel.barrelHandler.$getSlotCount()).mapToObj(barrel.barrelHandler::$getStack)
    //        .filter(stack -> !stack.isEmpty()).forEach(stack -> Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack));
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (level instanceof ServerLevel serverLevel) {
      MenuProvider menuProvider = getMenuProvider(state,level,pos);
      if (menuProvider != null) {
        player.openMenu(menuProvider);
        player.awardStat(Stats.OPEN_BARREL);
        PiglinAi.angerNearbyPiglins(serverLevel,player, true);
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

  @Override
  protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
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
