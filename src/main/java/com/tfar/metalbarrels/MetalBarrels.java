package com.tfar.metalbarrels;

import com.tfar.metalbarrels.block.*;
import com.tfar.metalbarrels.container.*;
import com.tfar.metalbarrels.item.BarrelUpgradeItem;
import com.tfar.metalbarrels.item.UpgradeInfo;
import com.tfar.metalbarrels.network.PacketHandler;
import com.tfar.metalbarrels.screens.*;
import com.tfar.metalbarrels.tile.*;
import com.tfar.metalbarrels.util.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
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
  //public static final boolean IRON_CHESTS_LOADED = ModList.get().isLoaded("ironchest");

  public static final Logger logger = LogManager.getLogger();

  public static final ItemGroup tab = new ItemGroup(MODID) {
    @Override
    public ItemStack createIcon() {
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
    BarrelUpgradeItem.IRON_CHESTS_LOADED = ModList.get().isLoaded("ironchest");
    PacketHandler.register();
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    ScreenManager.registerFactory(MetalBarrels.ObjectHolders.COPPER, CopperBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.IRON, IronBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.SILVER, SilverBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.GOLD, GoldBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.DIAMOND, DiamondBarrelScreen::new);
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      Block.Properties metal = Block.Properties.create(Material.IRON).hardnessAndResistance(2.5f,6).sound(SoundType.METAL);
      Block.Properties softmetal = metal.harvestLevel(1);
      Block.Properties hardmetal = metal.harvestLevel(2);
      Block.Properties obsidian = Block.Properties.create(Material.ROCK).hardnessAndResistance(15,6000).harvestLevel(3);
      registerBlock(new MetalBarrelBlock(softmetal,CopperBarrelTile::new),"copper_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(softmetal,IronBarrelTile::new),"iron_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(hardmetal,SilverBarrelTile::new),"silver_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(hardmetal,GoldBarrelTile::new),"gold_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(hardmetal,DiamondBarrelTile::new),"diamond_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new MetalBarrelBlock(obsidian,DiamondBarrelTile::new),"obsidian_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new CrystalBarrelBlock(hardmetal.notSolid(),CrystalBarrelTile::new),"crystal_barrel",blockRegistryEvent.getRegistry());
    }
    private static void registerBlock(Block block, String name, IForgeRegistry<Block> registry) {
      registry.register(block.setRegistryName(name));
      MOD_BLOCKS.add(block);
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {

      IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
      Item.Properties properties = new Item.Properties().group(tab);
      for (Block block : MOD_BLOCKS) {
        registerItem(new BlockItem(block, properties), block.getRegistryName().toString(), registry);
      }

      //wood to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.COPPER_BARREL))
              ).add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.COPPER_CHEST,"ironchest"))
              ,"wood_to_copper",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.IRON_BARREL))
              ).add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.IRON_CHEST,"ironchest"))
              ,"wood_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL))
              ).add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.SILVER_CHEST,"ironchest"))
              ,"wood_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
              ).add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"wood_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL)))
                      .add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"wood_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL)))
                      .add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"wood_to_obsidian",registry);

      //copper to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.IRON_BARREL)))
                      .add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.IRON_CHEST,"ironchest"))
              ,"copper_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL)))
                      .add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.SILVER_CHEST,"ironchest"))
              ,"copper_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL)))
                      .add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"copper_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL)))
                      .add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"copper_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
                      new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL)))
                      .add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"copper_to_obsidian",registry);

      //iron to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.SILVER_BARREL))
              ).add(Tags.Blocks.IRON_CHESTS,IronChestObjectHolders.SILVER_CHEST,"ironchest"))

              ,"iron_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
              ).add(Tags.Blocks.IRON_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))

              ,"iron_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
              ).add(Tags.Blocks.IRON_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))

              ,"iron_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
              ).add(Tags.Blocks.IRON_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))

              ,"iron_to_obsidian",registry);

      //silver to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.GOLD_BARREL))
      ).add(Tags.Blocks.SILVER_CHESTS,IronChestObjectHolders.GOLD_CHEST,"ironchest"))
              ,"silver_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
      ).add(Tags.Blocks.SILVER_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"silver_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(Tags.Blocks.SILVER_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"silver_to_obsidian",registry);

      //gold to x

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.DIAMOND_BARREL))
      ).add(Tags.Blocks.GOLD_CHESTS,IronChestObjectHolders.DIAMOND_CHEST,"ironchest"))
              ,"gold_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(Tags.Blocks.GOLD_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"gold_to_obsidian",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.DIAMOND_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.OBSIDIAN_BARREL))
      ).add(Tags.Blocks.DIAMOND_CHESTS,IronChestObjectHolders.OBSIDIAN_CHEST,"ironchest"))
              ,"diamond_to_obsidian",registry);

      //crystal

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.WOODEN_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.WOODEN_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"wood_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.COPPER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.COPPER_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"copper_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties,  new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.IRON_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.IRON_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"iron_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.SILVER_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.SILVER_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"silver_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.GOLD_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.GOLD_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"gold_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(new ArrayList<>(Collections.singleton(Tags.Blocks.DIAMOND_BARRELS)),
              new ArrayList<>(Collections.singleton(ObjectHolders.CRYSTAL_BARREL))
      ).add(Tags.Blocks.DIAMOND_CHESTS,IronChestObjectHolders.CRYSTAL_CHEST,"ironchest"))
              ,"diamond_to_crystal",registry);

    }

    private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
      registry.register(item.setRegistryName(name));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {

      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> MetalBarrelContainer.copper(windowId,inv.player.world,data.readBlockPos(),inv,inv.player)).setRegistryName("copper"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> MetalBarrelContainer.iron(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("iron"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> MetalBarrelContainer.silver(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("silver"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> MetalBarrelContainer.gold(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("gold"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> MetalBarrelContainer.diamond(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("diamond"));

    }

    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {

      event.getRegistry().register(TileEntityType.Builder.create(CopperBarrelTile::new, ObjectHolders.COPPER_BARREL).build(null).setRegistryName("copper_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(IronBarrelTile::new, ObjectHolders.IRON_BARREL).build(null).setRegistryName("iron_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(SilverBarrelTile::new, ObjectHolders.SILVER_BARREL).build(null).setRegistryName("silver_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(GoldBarrelTile::new, ObjectHolders.GOLD_BARREL).build(null).setRegistryName("gold_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(DiamondBarrelTile::new, ObjectHolders.DIAMOND_BARREL,ObjectHolders.OBSIDIAN_BARREL).build(null).setRegistryName("diamond_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(CrystalBarrelTile::new, ObjectHolders.CRYSTAL_BARREL).build(null).setRegistryName("crystal_tile"));

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
    public static final ContainerType<MetalBarrelContainer> COPPER = null;
    public static final TileEntityType<CopperBarrelTile> COPPER_TILE = null;

    public static final Block IRON_BARREL = null;
    public static final ContainerType<MetalBarrelContainer> IRON = null;
    public static final TileEntityType<IronBarrelTile> IRON_TILE = null;

    public static final Block GOLD_BARREL = null;
    public static final ContainerType<MetalBarrelContainer> GOLD = null;
    public static final TileEntityType<GoldBarrelTile> GOLD_TILE = null;

    public static final Block DIAMOND_BARREL = null;
    public static final ContainerType<MetalBarrelContainer> DIAMOND = null;
    public static final TileEntityType<DiamondBarrelTile> DIAMOND_TILE = null;

    public static final Block OBSIDIAN_BARREL = null;

    public static final Block SILVER_BARREL = null;
    public static final ContainerType<MetalBarrelContainer> SILVER = null;
    public static final TileEntityType<SilverBarrelTile> SILVER_TILE = null;

    public static final Block CRYSTAL_BARREL = null;
    public static final TileEntityType<CrystalBarrelTile> CRYSTAL_TILE = null;
  }
}
