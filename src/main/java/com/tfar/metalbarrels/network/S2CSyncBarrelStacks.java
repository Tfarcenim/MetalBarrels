/************************
borrowed from iron chests
 ********************* */
package com.tfar.metalbarrels.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncBarrelStacks {
  private final int dimension;

  private final BlockPos pos;

  private final NonNullList<ItemStack> topStacks;

  public S2CSyncBarrelStacks(int dimension, BlockPos pos, NonNullList<ItemStack> topStacks) {
    this.dimension = dimension;
    this.pos = pos;
    this.topStacks = topStacks;
  }

  public static void encode(S2CSyncBarrelStacks msg, FriendlyByteBuf buf) {
    buf.writeInt(msg.dimension);
    buf.writeInt(msg.pos.getX());
    buf.writeInt(msg.pos.getY());
    buf.writeInt(msg.pos.getZ());
    buf.writeInt(msg.topStacks.size());

    for (ItemStack stack : msg.topStacks) {
      buf.writeItem(stack);
    }
  }

  public static S2CSyncBarrelStacks decode(FriendlyByteBuf buf) {
    int dimension = buf.readInt();
    BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

    int size = buf.readInt();
    NonNullList<ItemStack> topStacks = NonNullList.withSize(size, ItemStack.EMPTY);

    for (int item = 0; item < size; item++) {
      ItemStack itemStack = buf.readItem();

      topStacks.set(item, itemStack);
    }

    return new S2CSyncBarrelStacks(dimension, pos, topStacks);
  }

  public static class Handler {
    public static void handle(final S2CSyncBarrelStacks message, Supplier<NetworkEvent.Context> ctx) {
      ctx.get().enqueueWork(() -> {

      Level world = DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().level);

        if (world != null) {
          BlockEntity tile = world.getBlockEntity(message.pos);

          /*if (tile instanceof CrystalBarrelTile) {
            ((CrystalBarrelTile) tile).receiveMessageFromServer(message.topStacks);
          }*/
        }
      });
      ctx.get().setPacketHandled(true);
    }
  }

}

