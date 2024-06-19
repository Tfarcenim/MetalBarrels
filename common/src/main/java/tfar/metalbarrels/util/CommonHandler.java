package tfar.metalbarrels.util;

import net.minecraft.nbt.CompoundTag;

public interface CommonHandler {


    CompoundTag $serialize();

    void $deserialize(CompoundTag invTag);
}
