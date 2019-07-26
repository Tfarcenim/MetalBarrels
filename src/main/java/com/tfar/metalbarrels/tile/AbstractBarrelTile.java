package com.tfar.metalbarrels.tile;

import com.tfar.metalbarrels.util.BarrelHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

;

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
    return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? LazyOptional.of(() -> handler).cast() : super.getCapability(cap, side);
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

  @Nullable
  @Override
  public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
    return null;
  }
}