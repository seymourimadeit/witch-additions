package tallestred.witch_additions;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.frog.Frog;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import tocraft.walkers.api.PlayerShape;

@EventBusSubscriber(modid = WitchAdditions.MODID)
public class WAEvents {
    @SubscribeEvent
    public static void onEffectExpire(MobEffectEvent.Remove event) {
        if (event.getEffectInstance().is(WAMobEffects.FROG_TRANSFORMATION_EFFECT)) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                PlayerShape.updateShapes(serverPlayer, null);
            } else {
                if (event.getEntity() instanceof Frog frog) {
                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(frog.getData(WADataAttachments.FROG_TRANSFORMED_FROM.get())));
                    PathfinderMob mob = frog.convertTo((EntityType<PathfinderMob>) entityType, true);
                    mob.copyAttachmentsFrom(frog, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEffectExpire(MobEffectEvent.Expired event) {
        if (event.getEffectInstance().is(WAMobEffects.FROG_TRANSFORMATION_EFFECT)) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                PlayerShape.updateShapes(serverPlayer, null);
            } else {
                if (event.getEntity() instanceof Frog frog) {
                    EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(frog.getData(WADataAttachments.FROG_TRANSFORMED_FROM.get())));
                    PathfinderMob mob = frog.convertTo((EntityType<PathfinderMob>) entityType, true);
                    mob.copyAttachmentsFrom(frog, false);
                }
            }
        }
    }
}
