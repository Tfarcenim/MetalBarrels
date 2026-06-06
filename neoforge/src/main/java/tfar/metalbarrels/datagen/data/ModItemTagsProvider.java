package tfar.metalbarrels.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.VanillaItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.util.ModTags;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output,lookupProvider,  MetalBarrels.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        new ModBlockItemTagsProvider() {
            @Override
            protected TagAppender<Block, Block> tag(TagKey<Block> blockTag, TagKey<Item> itemTag) {
                return new VanillaItemTagsProvider.BlockToItemConverter(ModItemTagsProvider.this.tag(itemTag));
            }
        }.run();

        Item[] gold = ModDatagen.getKnownItems().filter(item -> BuiltInRegistries.ITEM.getKey(item).getPath().contains("gold")).toArray(Item[]::new);
        tag(ItemTags.PIGLIN_LOVED).add(gold);
    }
}
