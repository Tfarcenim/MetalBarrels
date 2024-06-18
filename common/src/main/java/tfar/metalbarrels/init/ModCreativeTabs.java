package tfar.metalbarrels.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import tfar.metalbarrels.MetalBarrels;

public class ModCreativeTabs {

    public static final CreativeModeTab tab = CreativeModeTab.builder(null,-1)
            .icon(() -> new ItemStack(ModBlocks.DIAMOND_BARREL))
            .displayItems((pEnabledFeatures, pOutput) -> ModItems.getItems().forEach(pOutput::accept))
            .title(Component.translatable("itemGroup."+ MetalBarrels.MOD_ID)).build();

}
