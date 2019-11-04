package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.block.AbstractBarrelBlock;
import com.tfar.metalbarrels.util.BarrelHandler;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractBarrelTile extends TileEntity implements INamedContainerProvider, INameable {

  protected final int width,height;
  private ITextComponent customName;

  public AbstractBarrelTile(TileEntityType<?> tileEntityType, int width, int height) {
    super(tileEntityType);
    this.width = width;
    this.height = height;
    handler = new BarrelHandler(this.width * this.height,this);
  }

  public BarrelHandler handler;
  public LazyOptional<IItemHandler> optional = LazyOptional.of(() -> handler);

  public int players = 0;

  @Nonnull
  @Override
  public CompoundNBT write(CompoundNBT tag) {
    CompoundNBT compound = this.handler.serializeNBT();
    tag.put("inv", compound);
    if (this.customName != null) {
      tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
    }
    return super.write(tag);
  }

  public void changeState(BlockState p_213963_1_, boolean p_213963_2_) {
    if (p_213963_1_.getBlock() instanceof AbstractBarrelBlock)
    this.world.setBlockState(this.getPos(), p_213963_1_.with(BarrelBlock.PROPERTY_OPEN, p_213963_2_), 3);
    //else MetalBarrels.logger.warn("Attempted to set invalid property of {}",p_213963_1_.toString());
  }

  public void soundStuff(BlockState p_213965_1_, SoundEvent p_213965_2_) {
    if (!(p_213965_1_.getBlock() instanceof AbstractBarrelBlock)){
      //MetalBarrels.logger.warn("Attempted to set invalid property of {}",p_213965_1_.toString());
      return;
    }
    Vec3i lvt_3_1_ = p_213965_1_.get(BarrelBlock.PROPERTY_FACING).getDirectionVec();
    double lvt_4_1_ = this.pos.getX() + 0.5D + lvt_3_1_.getX() / 2.0D;
    double lvt_6_1_ = this.pos.getY() + 0.5D + lvt_3_1_.getY() / 2.0D;
    double lvt_8_1_ = this.pos.getZ() + 0.5D + lvt_3_1_.getZ() / 2.0D;
    this.world.playSound(null, lvt_4_1_, lvt_6_1_, lvt_8_1_, p_213965_2_, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
  }

  @Override
  public void read(CompoundNBT tag) {
    CompoundNBT invTag = tag.getCompound("inv");
    handler.deserializeNBT(invTag);
    if (tag.contains("CustomName", 8)) {
      this.customName = ITextComponent.Serializer.fromJson(tag.getString("CustomName"));
    }
    super.read(tag);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? optional.cast() : super.getCapability(cap, side);
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

  protected abstract ITextComponent getDefaultName();
}