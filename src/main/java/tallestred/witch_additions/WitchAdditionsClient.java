package tallestred.witch_additions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import tallestred.witch_additions.client.models.WitchHatModel;
import tallestred.witch_additions.client.renderer.WitchHatRenderer;
import tallestred.witch_additions.config.Config;
import tocraft.walkers.api.platform.ApiLevel;

import static tallestred.witch_additions.WitchAdditions.MODID;

@EventBusSubscriber(value = Dist.CLIENT)
@Mod(value = MODID, dist = Dist.CLIENT)
public class WitchAdditionsClient {
    public static final Material WITCH_HAT_MODEL = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(MODID, "item/witch_hat"));

    public WitchAdditionsClient(IEventBus modEventBus, ModContainer modContainer) {
    }

    @SubscribeEvent
    public static void doClientExtensionsStuff(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new WitchHatRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            }
        }, WAMobEffectsAndItems.WITCH_HAT.get());
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WitchHatModel.LAYER_LOCATION, WitchHatModel::createBodyLayer);
    }
}
