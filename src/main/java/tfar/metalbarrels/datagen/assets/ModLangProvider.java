package tfar.metalbarrels.datagen.assets;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.metalbarrels.MetalBarrels;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, MetalBarrels.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }

    protected void addGroup(CreativeModeTab group,String name) {
        add(group.getDisplayName().getString(),name);
    }

    public void addPotions() {

    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }
}
