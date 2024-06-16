package tfar.metalbarrels.datagen.assets;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.MetalBarrelsForge;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MetalBarrels.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        for (int i = 0; i < MetalBarrelsForge.tiers.length; i++){
            for (int j = i + 1; j < MetalBarrelsForge.tiers.length; j++) {
                registerUpgrade(MetalBarrelsForge.tiers[i] + "_to_" + MetalBarrelsForge.tiers[j]);
            }
        }

        for (int i = 0; i < 6;i++) {
            registerUpgrade(MetalBarrelsForge.tiers[i]+"_to_crystal");
        }
    }

    protected void registerUpgrade(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        registerUpgrade(name);
    }
    protected void registerUpgrade(String name) {
        String[] strings = name.split("_");
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0","item/from_"+strings[0])
                .texture("layer1","item/to_"+strings[2]);
    }


}
