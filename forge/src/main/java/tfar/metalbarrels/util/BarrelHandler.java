package tfar.metalbarrels.util;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;
import tfar.metalbarrels.tile.MetalBarrelBlockEntity;

public class BarrelHandler extends ItemStackHandler {

    public final MetalBarrelBlockEntity metalBarrelBlockEntity;

    public BarrelHandler(int slots,MetalBarrelBlockEntity metalBarrelBlockEntity) {
        super(slots);
        this.metalBarrelBlockEntity = metalBarrelBlockEntity;
    }


    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (metalBarrelBlockEntity != null) {
            metalBarrelBlockEntity.setChanged();
        }
    }


    public void startOpen(Player player) {
        if (metalBarrelBlockEntity != null) {
            metalBarrelBlockEntity.startOpen(player);
        }
    }

    public void stopOpen(Player pPlayer) {
        if (metalBarrelBlockEntity != null) {
            metalBarrelBlockEntity.stopOpen(pPlayer);
        }
    }


}
