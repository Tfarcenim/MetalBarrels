package tfar.metalbarrels.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.datagen.assets.ModItemModelProvider;
import tfar.metalbarrels.datagen.assets.ModLangProvider;
import tfar.metalbarrels.datagen.data.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = e.getLookupProvider();
        boolean client = e.includeClient();
        boolean server = e.includeServer();
        boolean dev = e.includeDev();

        generator.addProvider(client, new ModLangProvider(output));
        generator.addProvider(client, new ModItemModelProvider(output, helper));

        generator.addProvider(server, new ModRecipeProvider(output,provider));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output,provider, helper);
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModItemTagsProvider(output,provider, blockTagsProvider,helper));
        generator.addProvider(server,new ModLootTableProvider(output, List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
        ),provider));
    }

    public static Stream<Block> getKnownBlocks() {
        return getKnown(BuiltInRegistries.BLOCK);
    }
    public static Stream<Item> getKnownItems() {
        return getKnown(BuiltInRegistries.ITEM);
    }

    public static <V> Stream<V> getKnown(Registry<V> registry) {
        return registry.stream().filter(o -> registry.getKey(o).getNamespace().equals(MetalBarrels.MOD_ID));
    }

}
