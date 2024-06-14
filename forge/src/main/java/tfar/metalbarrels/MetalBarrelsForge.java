package tfar.metalbarrels;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.init.ModBlockEntityTypes;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.init.ModItems;
import tfar.metalbarrels.init.ModMenuTypes;
import tfar.metalbarrels.item.BarrelUpgradeItem;
import tfar.metalbarrels.network.PacketHandler;
import tfar.metalbarrels.client.MetalBarrelsClientForge;
import tfar.metalbarrels.screens.MetalBarrelScreen;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MOD_ID)
public class MetalBarrelsForge {

  public static final Logger logger = LogManager.getLogger();

  public static final String[] tiers = new String[]{"wood","copper","iron","silver","gold","diamond","obsidian","netherite"};

  public MetalBarrelsForge() {
    IEventBus bus  = FMLJavaModLoadingContext.get().getModEventBus();
    bus.addListener(this::doClientStuff);
    bus.addListener(this::commonSetup);
    bus.addListener(this::register);
    bus.addListener(ModDatagen::start);

    if (FMLEnvironment.dist.isClient()) {
      bus.addListener(MetalBarrelsClientForge::doClientStuff);
    }

  }

  public static <T> void superRegister(RegisterEvent e, Class<?> clazz, ResourceKey<? extends Registry<T>> resourceKey, Class filter) {
    for (Field field : clazz.getFields()) {
      try {
        Object o = field.get(null);
        if (filter.isInstance(o)) {
          e.register(resourceKey,new ResourceLocation(MetalBarrels.MOD_ID,field.getName().toLowerCase(Locale.ROOT)),() -> (T)o);
        }
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      }
    }
  }

  private void register(final RegisterEvent event) {
    superRegister(event,ModBlocks.class, Registries.BLOCK,Block.class);
    superRegister(event, ModItems.class,Registries.ITEM, Item.class);
    for (Map.Entry<String,BarrelUpgradeItem> entry : ModItems.upgrade_items.entrySet()) {
      event.register(Registries.ITEM,new ResourceLocation(MetalBarrels.MOD_ID,entry.getKey()), entry::getValue);
    }
    superRegister(event, ModBlockEntityTypes.class,Registries.BLOCK_ENTITY_TYPE, BlockEntityType.class);
    superRegister(event, ModMenuTypes.class,Registries.MENU, MenuType.class);
    event.register(Registries.CREATIVE_MODE_TAB,new ResourceLocation(MetalBarrels.MOD_ID,"tab"),() -> tab);
  }

  public static final CreativeModeTab tab = CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.DIAMOND_BARREL))
          .displayItems((pEnabledFeatures, pOutput) -> ModItems.getItems().forEach(pOutput::accept)).title(Component.translatable("itemGroup."+ MetalBarrels.MOD_ID)).build();

  private void commonSetup(final FMLCommonSetupEvent event) {
    PacketHandler.register();
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    MenuScreens.register(ModMenuTypes.COPPER, MetalBarrelScreen::copper);
    MenuScreens.register(ModMenuTypes.IRON, MetalBarrelScreen::iron);
    MenuScreens.register(ModMenuTypes.SILVER, MetalBarrelScreen::silver);
    MenuScreens.register(ModMenuTypes.GOLD, MetalBarrelScreen::gold);
    MenuScreens.register(ModMenuTypes.DIAMOND, MetalBarrelScreen::diamond);
    MenuScreens.register(ModMenuTypes.NETHERITE, MetalBarrelScreen::netherite);
  }
}
