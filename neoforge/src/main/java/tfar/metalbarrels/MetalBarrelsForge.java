package tfar.metalbarrels;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.metalbarrels.client.ModClientForge;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.init.ModBlockEntityTypes;
import tfar.metalbarrels.network.PacketHandler;
import tfar.metalbarrels.util.BarrelHandlerForge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MOD_ID)
public class MetalBarrelsForge {

  public MetalBarrelsForge(IEventBus bus, Dist dist) {
    bus.addListener(this::commonSetup);
    bus.addListener(this::register);
    bus.addListener(ModDatagen::start);
    bus.addListener(this::capabilities);

    if (dist.isClient()) {
      ModClientForge.init(bus);
    }
    MetalBarrels.init();
  }

  public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<?>>>> registerLater = new HashMap<>();
  private void register(RegisterEvent e) {
    for (Map.Entry<Registry<?>,List<Pair<ResourceLocation, Supplier<?>>>> entry : registerLater.entrySet()) {
      Registry<?> registry = entry.getKey();
      List<Pair<ResourceLocation, Supplier<?>>> toRegister = entry.getValue();
      for (Pair<ResourceLocation,Supplier<?>> pair : toRegister) {
        e.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(),(Supplier<Object>)pair.getValue());
      }
    }
  }

  private void capabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.COPPER, (container, side) -> (BarrelHandlerForge)container.barrelHandler);
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.IRON, (container, side) -> (BarrelHandlerForge)container.barrelHandler);
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.SILVER, (container, side) -> (BarrelHandlerForge)container.barrelHandler);
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.GOLD, (container, side) -> (BarrelHandlerForge)container.barrelHandler);
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.DIAMOND, (container, side) -> (BarrelHandlerForge)container.barrelHandler);
    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntityTypes.NETHERITE, (container, side) -> (BarrelHandlerForge)container.barrelHandler);

  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    registerLater.clear();
    PacketHandler.register();
  }
}
