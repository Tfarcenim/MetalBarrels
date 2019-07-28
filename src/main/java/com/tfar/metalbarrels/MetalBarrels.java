package com.tfar.metalbarrels;

import com.tfar.metalbarrels.block.*;
import com.tfar.metalbarrels.container.*;
import com.tfar.metalbarrels.item.BarrelUpgradeItem;
import com.tfar.metalbarrels.item.UpgradeInfo;
import com.tfar.metalbarrels.network.PacketHandler;
import com.tfar.metalbarrels.render.CrystalBarrelTileSpecialRenderer;
import com.tfar.metalbarrels.screens.*;
import com.tfar.metalbarrels.tile.*;
import com.tfar.metalbarrels.util.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MODID)
public class MetalBarrels {

  public static final String MODID = "metalbarrels";

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
    PacketHandler.register();
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    ScreenManager.registerFactory(MetalBarrels.ObjectHolders.COPPER_CONTAINER, CopperBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.IRON_CONTAINER, IronBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.SILVER_CONTAINER, SilverBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.GOLD_CONTAINER, GoldBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.DIAMOND_CONTAINER, DiamondBarrelScreen::new);
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      Block.Properties metal = Block.Properties.create(Material.IRON).hardnessAndResistance(2.5f,6);
      Block.Properties obsidian = Block.Properties.create(Material.ROCK).hardnessAndResistance(15,6000);
      registerBlock(new CopperBarrelBlock(metal),"copper_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new IronBarrelBlock(metal),"iron_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new SilverBarrelBlock(metal),"silver_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new GoldBarrelBlock(metal),"gold_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new DiamondBarrelBlock(metal),"diamond_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new DiamondBarrelBlock(obsidian),"obsidian_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new CrystalBarrelBlock(metal),"crystal_barrel",blockRegistryEvent.getRegistry());
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

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.COPPER_BARREL)),"wood_to_copper",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.IRON_BARREL)),"wood_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.SILVER_BARREL)),"wood_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.GOLD_BARREL)),"wood_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.DIAMOND_BARREL)),"wood_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"wood_to_obsidian",registry);

      //copper to x

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.IRON_BARREL)),"copper_to_iron",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.SILVER_BARREL)),"copper_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.GOLD_BARREL)),"copper_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.DIAMOND_BARREL)),"copper_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"copper_to_obsidian",registry);

      //iron to x

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.IRON_BARRELS,
              ObjectHolders.SILVER_BARREL)),"iron_to_silver",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.IRON_BARRELS,
              ObjectHolders.GOLD_BARREL)),"iron_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.IRON_BARRELS,
              ObjectHolders.DIAMOND_BARREL)),"iron_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.IRON_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"iron_to_obsidian",registry);

      //silver to x

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.SILVER_BARRELS,
              ObjectHolders.GOLD_BARREL)),"silver_to_gold",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.SILVER_BARRELS,
              ObjectHolders.DIAMOND_BARREL)),"silver_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.SILVER_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"silver_to_obsidian",registry);

      //gold to x

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.GOLD_BARRELS,
              ObjectHolders.DIAMOND_BARREL)),"gold_to_diamond",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.GOLD_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"gold_to_obsidian",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.DIAMOND_BARRELS,
              ObjectHolders.OBSIDIAN_BARREL)),"diamond_to_obsidian",registry);

      //crystal

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.WOOD_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"wood_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.COPPER_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"copper_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.IRON_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"iron_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.SILVER_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"silver_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.GOLD_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"gold_to_crystal",registry);

      registerItem(new BarrelUpgradeItem(properties, new UpgradeInfo(Tags.Blocks.DIAMOND_BARRELS,
              ObjectHolders.CRYSTAL_BARREL)),"diamond_to_crystal",registry);

    }

    private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
      registry.register(item.setRegistryName(name));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {

      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new CopperBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("copper_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new IronBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("iron_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new SilverBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("silver_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new GoldBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("gold_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new DiamondBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("diamond_container"));

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

  @ObjectHolder(MetalBarrels.MODID)
  public static class ObjectHolders {

    public static final Block COPPER_BARREL = null;
    public static final ContainerType<CopperBarrelContainer> COPPER_CONTAINER = null;
    public static final TileEntityType<CopperBarrelTile> COPPER_TILE = null;

    public static final Block IRON_BARREL = null;
    public static final ContainerType<IronBarrelContainer> IRON_CONTAINER = null;
    public static final TileEntityType<IronBarrelTile> IRON_TILE = null;

    public static final Block GOLD_BARREL = null;
    public static final ContainerType<GoldBarrelContainer> GOLD_CONTAINER = null;
    public static final TileEntityType<GoldBarrelTile> GOLD_TILE = null;

    public static final Block DIAMOND_BARREL = null;
    public static final ContainerType<DiamondBarrelContainer> DIAMOND_CONTAINER = null;
    public static final TileEntityType<DiamondBarrelTile> DIAMOND_TILE = null;

    public static final Block OBSIDIAN_BARREL = null;

    public static final Block SILVER_BARREL = null;
    public static final ContainerType<SilverBarrelContainer> SILVER_CONTAINER = null;
    public static final TileEntityType<SilverBarrelTile> SILVER_TILE = null;

    public static final Block CRYSTAL_BARREL = null;
    public static final TileEntityType<CrystalBarrelTile> CRYSTAL_TILE = null;
  }
}
