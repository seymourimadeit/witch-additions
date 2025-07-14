package tallestred.witch_additions;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.TradeWithVillagerEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import tallestred.witch_additions.mob_effects.MobTransformationEffect;
import tocraft.walkers.api.PlayerShape;

@EventBusSubscriber(modid = WitchAdditions.MODID)
public class WAEvents {

    @SubscribeEvent
    public static void onEffectApply(MobEffectEvent.Applicable event) {
        if (event.getEffectInstance() == null)
            return;
        if (event.getEffectInstance().getEffect().value() instanceof MobTransformationEffect && event.getEntity().getActiveEffects().stream().anyMatch(mobEffectInstance -> mobEffectInstance.getEffect().value() instanceof MobTransformationEffect)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }

    @SubscribeEvent
    public static void onVillagerTrade(TradeWithVillagerEvent event) {
        AbstractVillager villager = event.getAbstractVillager();
        for (MobEffectInstance mobEffectInstance : villager.getActiveEffects()) {
            if (mobEffectInstance.getEffect().value() instanceof MobTransformationEffect mobTransformationEffect) {
                mobTransformationEffect.removeThisEffect(villager);
            }
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        if (event.getEffectInstance() == null)
            return;
        transformBack(event.getEffectInstance().getEffect().value(), event.getEntity(), false);
    }

    @SubscribeEvent
    public static void onEffectExpire(MobEffectEvent.Expired event) {
        if (event.getEffectInstance() == null)
            return;
        transformBack(event.getEffectInstance().getEffect().value(), event.getEntity(), true);
    }

    public static void transformBack(MobEffect instance, LivingEntity entity, boolean transFormOnEnd) {
        if (instance == null)
            return;
        if (instance instanceof MobTransformationEffect transformationEffect) {
            if (!transformationEffect.isConvertAtEnd()) {
                if (entity instanceof ServerPlayer serverPlayer) {
                    PlayerShape.updateShapes(serverPlayer, null);
                } else {
                    if (entity instanceof PathfinderMob pathfinderMob && pathfinderMob.hasData(WADataAttachments.TRANSFORMED_FROM.get())) {
                        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(pathfinderMob.getData(WADataAttachments.TRANSFORMED_FROM.get())));
                        PathfinderMob mob = pathfinderMob.convertTo((EntityType<PathfinderMob>) entityType, true);
                        mob.copyAttachmentsFrom(pathfinderMob, false);
                    }
                }
            } else if (transFormOnEnd){
                transformationEffect.transformMob(entity);
            }
        }
    }
}
