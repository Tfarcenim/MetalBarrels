package tfar.metalbarrels;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.metalbarrels.client.ModClientForge;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.network.PacketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MOD_ID)
public class MetalBarrelsForge {

  public MetalBarrelsForge() {
    IEventBus bus  = FMLJavaModLoadingContext.get().getModEventBus();
    bus.addListener(this::commonSetup);
    bus.addListener(this::register);
    bus.addListener(ModDatagen::start);

    if (FMLEnvironment.dist.isClient()) {
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

  private void commonSetup(final FMLCommonSetupEvent event) {
    registerLater.clear();
    PacketHandler.register();
  }
}
