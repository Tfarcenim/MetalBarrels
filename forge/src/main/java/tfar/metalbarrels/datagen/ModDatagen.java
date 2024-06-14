package tfar.metalbarrels.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.metalbarrels.datagen.assets.ModItemModelProvider;
import tfar.metalbarrels.datagen.assets.ModLangProvider;
import tfar.metalbarrels.datagen.data.ModBlockTagsProvider;
import tfar.metalbarrels.datagen.data.ModItemTagsProvider;
import tfar.metalbarrels.datagen.data.ModRecipeProvider;

import java.util.concurrent.CompletableFuture;


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
}
