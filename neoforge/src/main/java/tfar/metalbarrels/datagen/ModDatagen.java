package tfar.metalbarrels.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.datagen.assets.ModItemModelProvider;
import tfar.metalbarrels.datagen.assets.ModLangProvider;
import tfar.metalbarrels.datagen.data.ModBlockTagsProvider;
import tfar.metalbarrels.datagen.data.ModItemTagsProvider;
import tfar.metalbarrels.datagen.data.ModRecipeProvider;

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

        generator.addProvider(server, new ModRecipeProvider(output));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output,provider, helper);
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModItemTagsProvider(output,provider, blockTagsProvider,helper));
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
