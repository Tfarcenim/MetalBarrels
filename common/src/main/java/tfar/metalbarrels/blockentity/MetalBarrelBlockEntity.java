package tfar.metalbarrels.blockentity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import tfar.metalbarrels.InventoryHandler;
import tfar.metalbarrels.block.MetalBarrelBlock;
import tfar.metalbarrels.menu.MetalBarrelMenu;
import tfar.metalbarrels.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tfar.metalbarrels.util.BarrelProperties;


public class MetalBarrelBlockEntity extends BlockEntity implements MenuProvider, Nameable {

  public final BarrelProperties barrelProperties;
  protected Component customName;

  public final InventoryHandler resourceHandler;

  public final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    protected void onOpen(Level level, BlockPos pos, BlockState state) {
      MetalBarrelBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
      MetalBarrelBlockEntity.this.updateBlockState(state, true);
    }

    protected void onClose(Level level, BlockPos pos, BlockState state) {
      MetalBarrelBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
      MetalBarrelBlockEntity.this.updateBlockState(state, false);
    }

    protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int previousCount, int newCount) {
    }

    public boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof MetalBarrelMenu metalBarrelMenu) {
        return metalBarrelMenu.handler == MetalBarrelBlockEntity.this.resourceHandler;
      } else {
        return false;
      }
    }
  };


  public MetalBarrelBlockEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
    super(tileEntityType, pos, state);
    barrelProperties = getPropertiesFromState(state);
    resourceHandler = Services.PLATFORM.createResourceHandler(this);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    resourceHandler.save(output);
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    resourceHandler.load(input);
  }

  public void setCustomName(Component name) {
    this.customName = name;
  }

  public Component getName() {
    return this.customName != null ? this.customName : this.getDefaultName();
  }

  public Component getDisplayName() {
    return this.getName();
  }

  public Component getCustomName() {
    return this.customName;
  }


  void updateBlockState(BlockState pState, boolean pOpen) {
    this.level.setBlock(this.getBlockPos(), pState.setValue(BarrelBlock.OPEN, Boolean.valueOf(pOpen)), 3);
  }

  void playSound(BlockState state, SoundEvent pSound) {
    Vec3i direction = state.getValue(BarrelBlock.FACING).getUnitVec3i();
    double d0 = this.worldPosition.getX() + 0.5D + direction.getX() / 2.0D;
    double d1 = this.worldPosition.getY() + 0.5D + direction.getY() / 2.0D;
    double d2 = this.worldPosition.getZ() + 0.5D + direction.getZ() / 2.0D;
    this.level.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
  }

  public void startOpen(ContainerUser containerUser) {
    if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
      this.openersCounter
              .incrementOpeners(
                      containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), containerUser.getContainerInteractionRange()
              );
    }
  }

  public void stopOpen(ContainerUser containerUser) {
    if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
      this.openersCounter.decrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  public void recheckOpen() {
    if (!isRemoved()) {
      openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  public int calculateRedstone() {
    return resourceHandler.getRedstoneSignal();
  }

  @Override
  public void preRemoveSideEffects(BlockPos pos, BlockState state) {
    resourceHandler.dropItems(level,pos);
  }

  protected Component getDefaultName() {
    return Component.translatable(getBlockState().getBlock().getDescriptionId());
  }

  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return barrelProperties.barrelMenuFactory().create(id,inv,resourceHandler);
  }

  public BarrelProperties getPropertiesFromState(BlockState state) {
    if (state.getBlock() instanceof MetalBarrelBlock metalBarrelBlock) {
      return metalBarrelBlock.getBarrelProperties();
    }
    return null;
  }

}