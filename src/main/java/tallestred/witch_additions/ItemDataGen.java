package tallestred.witch_additions;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static tallestred.witch_additions.WitchAdditions.MODID;

public class ItemDataGen extends ItemModelProvider {
    public ItemDataGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        justItemName("frog_potion");
        justItemName("axo_potion");
        justItemName("cat_potion");
        justItemName("frog_splash_potion");
        justItemName("axo_splash_potion");
        justItemName("cat_splash_potion");
        justItemName("spi_potion");
        justItemName("spi_splash_potion");
        justItemName("forbidden_potion");
        justItemName("forbidden_splash_potion");
    }

    public void justItemName(String itemName) {
        withExistingParent(itemName, mcLoc("item/generated")).texture("layer0", "item/" + itemName);
    }
}