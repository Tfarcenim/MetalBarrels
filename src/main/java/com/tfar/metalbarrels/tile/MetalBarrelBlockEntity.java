package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import com.tfar.metalbarrels.block.MetalBarrelBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.data.BlockStateVariantBuilder.ITriFunction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MetalBarrelBlockEntity extends TileEntity implements INamedContainerProvider, INameable {

  protected final int width;
  protected final int height;
  protected final ITriFunction<Integer, PlayerInventory, IWorldPosCallable,Container> containerFactory;
  protected ITextComponent customName;

  public MetalBarrelBlockEntity(TileEntityType<?> tileEntityType) {
    super(tileEntityType);
    this.width = ((MetalBarrelBlockEntityType)tileEntityType).width;
    this.height = ((MetalBarrelBlockEntityType)tileEntityType).height;
    this.containerFactory = ((MetalBarrelBlockEntityType)tileEntityType).containerFactory;
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

  @Nonnull
  @Override
  public CompoundNBT save(CompoundNBT tag) {
    CompoundNBT compound = this.handler.serializeNBT();
    tag.put("inv", compound);
    if (this.customName != null) {
      tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
    }
    return super.save(tag);
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
    Vector3i lvt_3_1_ = p_213965_1_.getValue(BarrelBlock.FACING).getNormal();
    double lvt_4_1_ = this.worldPosition.getX() + 0.5D + lvt_3_1_.getX() / 2.0D;
    double lvt_6_1_ = this.worldPosition.getY() + 0.5D + lvt_3_1_.getY() / 2.0D;
    double lvt_8_1_ = this.worldPosition.getZ() + 0.5D + lvt_3_1_.getZ() / 2.0D;
    this.level.playSound(null, lvt_4_1_, lvt_6_1_, lvt_8_1_, p_213965_2_, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
  }

  @Override//read
  public void load(BlockState state,CompoundNBT tag) {
    CompoundNBT invTag = tag.getCompound("inv");
    handler.deserializeNBT(invTag);
    if (tag.contains("CustomName", 8)) {
      this.customName = ITextComponent.Serializer.fromJson(tag.getString("CustomName"));
    }
    super.load(state,tag);
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

  public void setCustomName(ITextComponent name) {
    this.customName = name;
  }

  public ITextComponent getName() {
    return this.customName != null ? this.customName : this.getDefaultName();
  }

  public ITextComponent getDisplayName() {
    return this.getName();
  }

  @Nullable
  public ITextComponent getCustomName() {
    return this.customName;
  }

  protected ITextComponent getDefaultName() {
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
    return containerFactory.apply(id, inv,IWorldPosCallable.create(level,worldPosition));
  }
}