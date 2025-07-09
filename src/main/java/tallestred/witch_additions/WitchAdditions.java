package tallestred.witch_additions;

import tallestred.witch_additions.config.Config;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import tocraft.walkers.api.platform.ApiLevel;

@Mod(WitchAdditions.MODID)
public class WitchAdditions {
    public static final String MODID = "witch_additions";

    public WitchAdditions(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ApiLevel.setApiLevel(ApiLevel.API_ONLY);
        WAMobEffects.MOB_EFFECTS.register(modEventBus);
        WAMobEffects.POTIONS.register(modEventBus);
        WADataAttachments.ATTACHMENT_TYPES.register(modEventBus);
    }
}
