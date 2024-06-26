package tfar.metalbarrels.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import tfar.metalbarrels.blockentity.MetalBarrelBlockEntity;

public interface BarrelHandler extends CommonHandler {


    MetalBarrelBlockEntity<?> getBlockEntity();


    default void $onContentsChanged(int slot) {
        if (getBlockEntity() != null) {
            getBlockEntity().setChanged();
        }
    }

    default void $startOpen(Player player) {
        if (getBlockEntity() != null) {
            getBlockEntity().startOpen(player);
        }
    }

    default void $stopOpen(Player pPlayer) {
        if (getBlockEntity() != null) {
            getBlockEntity().stopOpen(pPlayer);
        }
    }

     Slot addInvSlot(int slot, int x, int y);


}
