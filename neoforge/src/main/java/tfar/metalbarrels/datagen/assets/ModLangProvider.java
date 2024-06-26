package tfar.metalbarrels.datagen.assets;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.metalbarrels.MetalBarrels;
import tfar.metalbarrels.MetalBarrelsForge;
import tfar.metalbarrels.datagen.ModDatagen;
import tfar.metalbarrels.init.ModCreativeTabs;

import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, MetalBarrels.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (Block block : ModDatagen.getKnownBlocks().toList()) {
            addDefaultBlock(() -> block);
        }

        for (Item item : ModDatagen.getKnownItems().toList()) {
            if (!(item instanceof BlockItem)) {
                addDefaultItem(() -> item);
            }
        }

        addGroup(ModCreativeTabs.tab,"Metal Barrels");

        add("metalbarrels.upgrade_successful", "Upgrade Successful");
        add("metalbarrels.in_use", "Barrel is in Use");
    }

    protected void addGroup(CreativeModeTab group,String name) {
        add(group.getDisplayName().getString(),name);
    }

    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    protected void addDefaultBlock(Supplier<? extends Block> supplier) {
        addBlock(supplier,getNameFromBlock(supplier.get()));
    }

    public static String getNameFromBlock(Block block) {
        return StringUtils.capitaliseAllWords(block.getDescriptionId().split("\\.")[2].replace("_", " "));
    }


    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }
}
