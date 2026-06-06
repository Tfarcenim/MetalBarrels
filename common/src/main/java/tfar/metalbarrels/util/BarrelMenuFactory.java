package tfar.metalbarrels.util;

import net.minecraft.world.entity.player.Inventory;
import tfar.metalbarrels.InventoryHandler;
import tfar.metalbarrels.menu.MetalBarrelMenu;

@FunctionalInterface
public interface BarrelMenuFactory {

    MetalBarrelMenu create(int id, Inventory inventory, InventoryHandler barrelHandler);

}
