package tfar.metalbarrels.datagen.assets;

import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.block.MetalBarrelBlock;
import tfar.metalbarrels.init.ModBlocks;
import tfar.metalbarrels.init.ModItems;
import tfar.metalbarrels.item.BarrelUpgradeItem;

public class MetalBarrelsModelProvider extends ModelProvider {
    public MetalBarrelsModelProvider(PackOutput output) {
        super(output, MetalBarrels.MOD_ID);
    }


    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        /*for (int i = 0; i < MetalBarrels.tiers.length; i++){
            for (int j = i + 1; j < MetalBarrels.tiers.length; j++) {
                registerUpgrade(MetalBarrels.tiers[i] + "_to_" + MetalBarrels.tiers[j],itemModels);
            }
        }*/

        for (BarrelUpgradeItem item : ModItems.upgrade_items.values()) {
            registerUpgrade(item, itemModels);
        }

        registerUpgrade(ModItems.wood_to_crystal, itemModels);
        registerUpgrade(ModItems.copper_to_crystal, itemModels);
        registerUpgrade(ModItems.iron_to_crystal, itemModels);
        registerUpgrade(ModItems.silver_to_crystal, itemModels);
        registerUpgrade(ModItems.gold_to_crystal, itemModels);
        registerUpgrade(ModItems.diamond_to_crystal, itemModels);

        createBarrel(blockModels, ModBlocks.COPPER_BARREL);
        createBarrel(blockModels, ModBlocks.IRON_BARREL);
        createBarrel(blockModels, ModBlocks.SILVER_BARREL);
        createBarrel(blockModels, ModBlocks.GOLD_BARREL);
        createBarrel(blockModels, ModBlocks.DIAMOND_BARREL);
        createBarrel(blockModels, ModBlocks.CRYSTAL_BARREL);
        createBarrel(blockModels, ModBlocks.OBSIDIAN_BARREL);
        createBarrel(blockModels, ModBlocks.NETHERITE_BARREL);
        /*for (int i = 0; i < 6;i++) {
            registerUpgrade(MetalBarrels.tiers[i]+"_to_crystal",itemModels);
        }*/
    }

    public void createBarrel(BlockModelGenerators blockModels, MetalBarrelBlock block) {
        Material openTop = TextureMapping.getBlockTexture(block, "_top_open");
        MultiVariant closedModel = BlockModelGenerators.plainVariant(TexturedModel.CUBE_TOP_BOTTOM.create(block, blockModels.modelOutput));
        MultiVariant openModel = BlockModelGenerators.plainVariant(
                TexturedModel.CUBE_TOP_BOTTOM
                        .get(Blocks.BARREL)
                        .updateTextures(t -> t.put(TextureSlot.TOP, openTop))
                        .createWithSuffix(block, "_open", blockModels.modelOutput)
        );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(PropertyDispatch.initial(BlockStateProperties.OPEN).select(false, closedModel).select(true, openModel))
                                .with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING)
                );
    }

    String name(Item item){
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    protected void registerUpgrade(Item item,ItemModelGenerators itemModels) {
        String name =name(item);
        String[] strings = name.split("_");

        Identifier model = itemModels.generateLayeredItem(item,new Material(modLocation("item/from_"+strings[0])),
                new Material(modLocation("item/to_"+strings[2]))
        );
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
    }
}
