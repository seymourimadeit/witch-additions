package tallestred.witch_additions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tallestred.witch_additions.client.renderer.WitchHatRenderer;
import tallestred.witch_additions.config.Config;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import tocraft.walkers.api.platform.ApiLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber
@Mod(WitchAdditions.MODID)
public class WitchAdditions {
    public static final String MODID = "witch_additions";
    public static final List<Supplier<? extends ItemLike>> POTIONS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> SPLASH_POTIONS = new ArrayList<>();
    public static final List<Supplier<? extends ItemLike>> ITEM_STACKS = new ArrayList<>();

    public WitchAdditions(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ApiLevel.setApiLevel(ApiLevel.API_ONLY);
        WAMobEffectsAndItems.MOB_EFFECTS.register(modEventBus);
        WAMobEffectsAndItems.ITEMS.register(modEventBus);
        WADataAttachments.ATTACHMENT_TYPES.register(modEventBus);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(
                event.includeClient(),
                new ItemDataGen(output, existingFileHelper)
        );
    }
}
