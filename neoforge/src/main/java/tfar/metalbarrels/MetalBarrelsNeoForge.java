package tfar.metalbarrels;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.metalbarrels.client.ModClientNeoForge;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.init.ModBlockEntityTypes;
import tfar.metalbarrels.network.PacketHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MOD_ID)
public class MetalBarrelsNeoForge {

  public MetalBarrelsNeoForge(IEventBus bus, Dist dist) {
    bus.addListener(this::commonSetup);
    bus.addListener(this::register);
    bus.addListener(ModDatagen::start);
    bus.addListener(this::capabilities);

    if (dist.isClient()) {
      ModClientNeoForge.init(bus);
    }
    MetalBarrels.init();
  }

  private void register(RegisterEvent e) {
    if (e.getRegistry() == BuiltInRegistries.BLOCK) {
      MetalBarrels.register();
    }
  }

  private void capabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.COPPER, (container, side) -> new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.IRON, (container, side) -> new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.SILVER, (container, side) -> new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.GOLD, (container, side) -> new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.DIAMOND, (container, side) ->new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.CRYSTAL, (container, side) -> new BarrelResourceHandler(container));
    event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.NETHERITE, (container, side) -> new BarrelResourceHandler(container));

  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    PacketHandler.register();
  }
}
