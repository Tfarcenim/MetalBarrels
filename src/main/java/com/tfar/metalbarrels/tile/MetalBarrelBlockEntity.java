package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import com.tfar.metalbarrels.block.MetalBarrelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class MetalBarrelBlockEntity extends BlockEntity implements MenuProvider, Nameable {

  protected final int width;
  protected final int height;
  protected final TriFunction<Integer, Inventory, ContainerLevelAccess,AbstractContainerMenu> containerFactory;
  protected Component customName;

  public MetalBarrelBlockEntity(BlockEntityType<?> tileEntityType, BlockPos pos,BlockState state) {
    super(tileEntityType, pos, state);
    this.width = ((MetalBarrelBlockEntityType<?>)tileEntityType).width;
    this.height = ((MetalBarrelBlockEntityType<?>)tileEntityType).height;
    this.containerFactory = ((MetalBarrelBlockEntityType<?>)tileEntityType).containerFactory;
    handler = new ItemStackHandler(width * height) {
      @Override
      protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        setChanged();
      }
    };
    optional = LazyOptional.of(() -> handler);
  }

  public final LazyOptional<IItemHandler> optional;
  public final ItemStackHandler handler;

  public int players = 0;

  @Override
  public void saveAdditional(CompoundTag tag) {
    CompoundTag compound = this.handler.serializeNBT();
    tag.put("inv", compound);
    if (this.customName != null) {
      tag.putString("CustomName", Component.Serializer.toJson(this.customName));
    }
    super.saveAdditional(tag);
  }

  public void changeState(BlockState p_213963_1_, boolean p_213963_2_) {
    if (p_213963_1_.getBlock() instanceof MetalBarrelBlock)
    this.level.setBlock(this.getBlockPos(), p_213963_1_.setValue(BarrelBlock.OPEN, p_213963_2_), 3);
    //else MetalBarrels.logger.warn("Attempted to set invalid property of {}",p_213963_1_.toString());
  }

  public void soundStuff(BlockState p_213965_1_, SoundEvent p_213965_2_) {
    if (!(p_213965_1_.getBlock() instanceof MetalBarrelBlock)){
      //MetalBarrels.logger.warn("Attempted to set invalid property of {}",p_213965_1_.toString());
      return;
    }
    Vec3i lvt_3_1_ = p_213965_1_.getValue(BarrelBlock.FACING).getNormal();
    double lvt_4_1_ = this.worldPosition.getX() + 0.5D + lvt_3_1_.getX() / 2.0D;
    double lvt_6_1_ = this.worldPosition.getY() + 0.5D + lvt_3_1_.getY() / 2.0D;
    double lvt_8_1_ = this.worldPosition.getZ() + 0.5D + lvt_3_1_.getZ() / 2.0D;
    this.level.playSound(null, lvt_4_1_, lvt_6_1_, lvt_8_1_, p_213965_2_, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
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
    return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? optional.cast() : super.getCapability(cap, side);
  }

  @Override
  public void setRemoved() {
    super.setRemoved();
    optional.invalidate();
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

  protected Component getDefaultName() {
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
    return containerFactory.apply(id, inv,ContainerLevelAccess.create(level,worldPosition));
  }
}