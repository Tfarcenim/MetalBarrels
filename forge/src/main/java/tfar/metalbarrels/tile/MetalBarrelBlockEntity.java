package tfar.metalbarrels.tile;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import tfar.metalbarrels.block.MetalBarrelBlock;
import tfar.metalbarrels.container.MetalBarrelContainer;
import tfar.metalbarrels.util.BarrelHandler;
import tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalBarrelBlockEntity extends BlockEntity implements MenuProvider, Nameable {

  protected Component customName;

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

    protected boolean isOwnContainer(Player player) {
      if (player.containerMenu instanceof MetalBarrelContainer metalBarrelContainer) {
        ItemStackHandler handler1 = metalBarrelContainer.handler;
        return handler1 == MetalBarrelBlockEntity.this.handler;
      } else {
        return false;
      }
    }
  };


  public MetalBarrelBlockEntity(MetalBarrelBlockEntityType<?> tileEntityType, BlockPos pos,BlockState state) {
    super(tileEntityType, pos, state);
    int width = tileEntityType.width;
    int height = tileEntityType.height;
    handler = new BarrelHandler(width * height,this);
    optional = LazyOptional.of(() -> handler);
  }

  public LazyOptional<IItemHandler> optional;
  public final BarrelHandler handler;

  @Override
  public void saveAdditional(CompoundTag tag) {
    CompoundTag compound = this.handler.serializeNBT();
    tag.put("inv", compound);
    if (this.customName != null) {
      tag.putString("CustomName", Component.Serializer.toJson(this.customName));
    }
    super.saveAdditional(tag);
  }

  @Override//read
  public void load(CompoundTag tag) {
    CompoundTag invTag = tag.getCompound("inv");
    handler.deserializeNBT(invTag);
    if (tag.contains("CustomName", 8)) {
      this.customName = Component.Serializer.fromJson(tag.getString("CustomName"));
    }
    super.load(tag);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == ForgeCapabilities.ITEM_HANDLER ? optional.cast() : super.getCapability(cap, side);
  }

  @Override
  public void setRemoved() {
    super.setRemoved();
    optional.invalidate();
  }

  @Override
  public void reviveCaps() {
    super.reviveCaps();
    optional = LazyOptional.of(() -> handler);
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

  @Nullable
  public Component getCustomName() {
    return this.customName;
  }


  void updateBlockState(BlockState pState, boolean pOpen) {
    this.level.setBlock(this.getBlockPos(), pState.setValue(BarrelBlock.OPEN, Boolean.valueOf(pOpen)), 3);
  }

  void playSound(BlockState pState, SoundEvent pSound) {
    Vec3i vec3i = pState.getValue(BarrelBlock.FACING).getNormal();
    double d0 = this.worldPosition.getX() + 0.5D + vec3i.getX() / 2.0D;
    double d1 = this.worldPosition.getY() + 0.5D + vec3i.getY() / 2.0D;
    double d2 = this.worldPosition.getZ() + 0.5D + vec3i.getZ() / 2.0D;
    this.level.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
  }


  public void startOpen(Player pPlayer) {
    if (!isRemoved() && !pPlayer.isSpectator()) {
      openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

  }

  public void stopOpen(Player pPlayer) {
    if (!isRemoved() && !pPlayer.isSpectator()) {
      openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

  }

  public void recheckOpen() {
    if (!isRemoved()) {
      openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }



  protected Component getDefaultName() {
    return Component.translatable(getBlockState().getBlock().getDescriptionId());
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return ((MetalBarrelBlockEntityType<?>)getType()).containerFactory.apply(id, inv,handler);
  }
}