package com.tfar.metalbarrels;

import com.google.common.collect.Sets;
import com.tfar.metalbarrels.block.*;
import com.tfar.metalbarrels.container.*;
import com.tfar.metalbarrels.item.BarrelUpgradeItem;
import com.tfar.metalbarrels.item.UpgradeInfo;
import com.tfar.metalbarrels.network.PacketHandler;
import com.tfar.metalbarrels.screens.*;
import com.tfar.metalbarrels.tile.*;
import com.tfar.metalbarrels.util.MetalBarrelBlockEntityType;
import com.tfar.metalbarrels.util.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MODID)
public class MetalBarrels {

  public static final String MODID = "metalbarrels";

  public static final Logger logger = LogManager.getLogger();

  public static final CreativeModeTab tab = new CreativeModeTab(MODID) {
    @Override
    public ItemStack makeIcon() {
      return new ItemStack(ObjectHolders.DIAMOND_BARREL);
    }
  };

  public MetalBarrels() {
    // Register doClientStuff method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    // Register commonSetup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
  }

  private void commonSetup(final FMLCommonSetupEvent event){
    PacketHandler.register();
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    MenuScreens.register(ObjectHolders.COPPER_CONTAINER, MetalBarrelScreen::copper);
    MenuScreens.register(ObjectHolders.IRON_CONTAINER, MetalBarrelScreen::iron);
    MenuScreens.register(ObjectHolders.SILVER_CONTAINER, MetalBarrelScreen::silver);
    MenuScreens.register(ObjectHolders.GOLD_CONTAINER, MetalBarrelScreen::gold);
    MenuScreens.register(ObjectHolders.DIAMOND_CONTAINER, MetalBarrelScreen::diamond);
    MenuScreens.register(ObjectHolders.NETHERITE_CONTAINER, MetalBarrelScreen::netherite);
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      Block.Properties metal = Block.Properties.of(Material.METAL).strength(1,6).sound(SoundType.METAL);
      Block.Properties obsidian = Block.Properties.of(Material.STONE).strength(2.5f,6000);
      registerBlock(new MetalBarrelBlock(metal, MetalBarrelBlockEntityType.copper()),"copper_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(metal,MetalBarrelBlockEntityType.iron()),"iron_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(metal,MetalBarrelBlockEntityType.silver()),"silver_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(metal,MetalBarrelBlockEntityType.gold()),"gold_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(metal,MetalBarrelBlockEntityType.diamond()),"diamond_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(obsidian,MetalBarrelBlockEntityType.diamond()),"obsidian_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(obsidian,MetalBarrelBlockEntityType.netherite()),"netherite_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new CrystalBarrelBlock(metal.noOcclusion(),MetalBarrelBlockEntityType.diamond()),"crystal_barrel",blockRegistryEvent.getRegistry());
    }
    private static void registerBlock(Block block, String name, IForgeRegistry<Block> registry) {
      registry.register(block.setRegistryName(name));
      MOD_BLOCKS.add(block);
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {

      IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
      for (Block block : MOD_BLOCKS) {
        Item.Properties properties = new Item.Properties().tab(tab);
        if (block == ObjectHolders.NETHERITE_BARREL) {
          properties.fireResistant();
        }
        Item item = new BlockItem(block, properties);
        registerItem(item, block.getRegistryName().toString(), registry);
      }

      //wood to x
      Item.Properties properties = new Item.Properties().tab(tab);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.COPPER_BARREL))
              ).add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.COPPER_CHEST,"ironchest"))
              ,"wood_to_copper",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.IRON_BARREL))
              ).add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.IRON_CHEST,"ironchest"))
              ,"wood_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL))
              ).add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.SILVER_CHEST,"ironchest"))
              ,"wood_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
              ).add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"wood_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL)))
                      .add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"wood_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL)))
                      .add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"wood_to_obsidian",registry);

			registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(
							Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
											new ArrayList<>(Collections.singleton(ObjectHolders.NETHERITE_BARREL))))
							,"wood_to_netherite",registry);

      //copper to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.IRON_BARREL)))
                      .add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.IRON_CHEST,"ironchest"))
              ,"copper_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL)))
                      .add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.SILVER_CHEST,"ironchest"))
              ,"copper_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL)))
                      .add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"copper_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL)))
                      .add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"copper_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL)))
                      .add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"copper_to_obsidian",registry);

      //iron to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL))
              ).add(ModTags.Blocks.IRON_CHESTS,IronChestObjectHolders.SILVER_CHEST,"ironchest"))

              ,"iron_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
              ).add(ModTags.Blocks.IRON_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))

              ,"iron_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
              ).add(ModTags.Blocks.IRON_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))

              ,"iron_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
              ).add(ModTags.Blocks.IRON_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))

              ,"iron_to_obsidian",registry);

      //silver to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
      ).add(ModTags.Blocks.SILVER_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"silver_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
      ).add(ModTags.Blocks.SILVER_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"silver_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(ModTags.Blocks.SILVER_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"silver_to_obsidian",registry);

      //gold to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
      ).add(ModTags.Blocks.GOLD_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"gold_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(ModTags.Blocks.GOLD_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"gold_to_obsidian",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.DIAMOND_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(ModTags.Blocks.DIAMOND_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"diamond_to_obsidian",registry);

      //crystal

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.CHESTS_WOODEN,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"wood_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.COPPER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(ModTags.Blocks.COPPER_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"copper_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(ModTags.Blocks.IRON_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"iron_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(ModTags.Blocks.SILVER_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"silver_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(ModTags.Blocks.GOLD_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"gold_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.DIAMOND_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(ModTags.Blocks.DIAMOND_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"diamond_to_crystal",registry);

      //obsidian to netherite
      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(ModTags.Blocks.OBSIDIAN_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.NETHERITE_BARREL))
              ))
              ,"obsidian_to_netherite",registry);
    }

    private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
      registry.register(item.setRegistryName(name));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {

      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::copper).setRegistryName("copper_container"));
      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::iron).setRegistryName("iron_container"));
      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::silver).setRegistryName("silver_container"));
      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::gold).setRegistryName("gold_container"));
      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::diamond).setRegistryName("diamond_container"));
      event.getRegistry().register(new MenuType<>(MetalBarrelContainer::netherite).setRegistryName("netherite_container"));

    }

    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<BlockEntityType<?>> event) {
      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.copper(),
              Sets.newHashSet(ObjectHolders.COPPER_BARREL),
              null, 9, 5, MetalBarrelContainer::copperS)
              .setRegistryName("copper_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.iron(),
              Sets.newHashSet(ObjectHolders.IRON_BARREL),
              null, 9, 6,MetalBarrelContainer::ironS)
              .setRegistryName("iron_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.silver(),
              Sets.newHashSet(ObjectHolders.SILVER_BARREL),
              null, 9, 8, MetalBarrelContainer::silverS)
              .setRegistryName("silver_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.gold(),
              Sets.newHashSet(ObjectHolders.GOLD_BARREL),
              null, 9, 9, MetalBarrelContainer::goldS)
              .setRegistryName("gold_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.diamond(),
              Sets.newHashSet(ObjectHolders.DIAMOND_BARREL,ObjectHolders.OBSIDIAN_BARREL),
              null, 12, 9,MetalBarrelContainer::diamondS)
              .setRegistryName("diamond_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.netherite(),
              Sets.newHashSet(ObjectHolders.NETHERITE_BARREL),
              null, 15, 9, MetalBarrelContainer::netheriteS)
              .setRegistryName("netherite_tile"));

      event.getRegistry().register(new MetalBarrelBlockEntityType<>(MetalBarrelBlockEntityType.diamond(),
              Sets.newHashSet(ObjectHolders.CRYSTAL_BARREL),
              null, 12, 9, MetalBarrelContainer::diamondS)
              .setRegistryName("crystal_tile"));
    }
  }

  @ObjectHolder("ironchest")
  public static class IronChestObjectHolders {
    public static final Block COPPER_CHEST = null;
    public static final Block IRON_CHEST = null;
    public static final Block SILVER_CHEST = null;
    public static final Block GOLD_CHEST = null;
    public static final Block DIAMOND_CHEST = null;
    public static final Block CRYSTAL_CHEST = null;
    public static final Block OBSIDIAN_CHEST = null;
  }

  @ObjectHolder(MetalBarrels.MODID)
  public static class ObjectHolders {

    public static final Block COPPER_BARREL = null;
    public static final MenuType<MetalBarrelContainer> COPPER_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> COPPER_TILE = null;

    public static final Block IRON_BARREL = null;
    public static final MenuType<MetalBarrelContainer> IRON_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> IRON_TILE = null;

    public static final Block GOLD_BARREL = null;
    public static final MenuType<MetalBarrelContainer> GOLD_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> GOLD_TILE = null;

    public static final Block DIAMOND_BARREL = null;
    public static final MenuType<MetalBarrelContainer> DIAMOND_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> DIAMOND_TILE = null;

    public static final Block NETHERITE_BARREL = null;
    public static final MenuType<MetalBarrelContainer> NETHERITE_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> NETHERITE_TILE = null;

    public static final Block OBSIDIAN_BARREL = null;

    public static final Block SILVER_BARREL = null;
    public static final MenuType<MetalBarrelContainer> SILVER_CONTAINER = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> SILVER_TILE = null;

    public static final Block CRYSTAL_BARREL = null;
    public static final BlockEntityType<MetalBarrelBlockEntity> CRYSTAL_TILE = null;
  }
}
