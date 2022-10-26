package tfar.metalbarrels.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.metalbarrels.datagen.assets.ModItemModelProvider;
import tfar.metalbarrels.datagen.assets.ModLangProvider;
import tfar.metalbarrels.datagen.data.ModBlockTagsProvider;
import tfar.metalbarrels.datagen.data.ModItemTagsProvider;
import tfar.metalbarrels.datagen.data.ModRecipeProvider;


public class ModDatagen {

    public static void start(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper helper = e.getExistingFileHelper();
        boolean client = e.includeClient();
        boolean server = e.includeServer();
        boolean dev = e.includeDev();

        generator.addProvider(client, new ModLangProvider(generator));
        generator.addProvider(client, new ModItemModelProvider(generator, helper));

        generator.addProvider(server, new ModRecipeProvider(generator));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, helper);
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModItemTagsProvider(generator, blockTagsProvider,helper));
    }
}
