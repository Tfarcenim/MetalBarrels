package tfar.metalbarrels;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.metalbarrels.init.*;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.platform.Services;

import java.util.Map;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class MetalBarrels {

    public static final String MOD_ID = "metalbarrels";
    public static final String MOD_NAME = "MetalBarrels";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String[] tiers = new String[]{"wood","copper","iron","silver","gold","diamond","obsidian","netherite"};

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        Services.PLATFORM.registerAll(ModBlocks.class, BuiltInRegistries.BLOCK, Block.class);
        Services.PLATFORM.registerAll(ModBlockEntityTypes.class, BuiltInRegistries.BLOCK_ENTITY_TYPE, BlockEntityType.class);
        Services.PLATFORM.registerAll(ModMenuTypes.class,BuiltInRegistries.MENU, MenuType.class);
        Services.PLATFORM.registerAll(ModCreativeTabs.class,BuiltInRegistries.CREATIVE_MODE_TAB, CreativeModeTab.class);
        Services.PLATFORM.registerAll(ModItems.class,BuiltInRegistries.ITEM, Item.class);


        for (Map.Entry<String, BarrelUpgradeItem> entry : ModItems.upgrade_items.entrySet()) {
            Services.PLATFORM.register(BuiltInRegistries.ITEM,entry.getValue(), id(entry.getKey()));
        }

    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID,path);
    }

}