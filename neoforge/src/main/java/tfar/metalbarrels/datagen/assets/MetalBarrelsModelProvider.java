package tfar.metalbarrels.datagen.assets;

import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import tfar.metalbarrels.MetalBarrels;
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

        /*for (int i = 0; i < 6;i++) {
            registerUpgrade(MetalBarrels.tiers[i]+"_to_crystal",itemModels);
        }*/
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
