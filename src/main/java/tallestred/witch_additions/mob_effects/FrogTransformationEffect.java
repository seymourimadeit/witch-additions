package tallestred.witch_additions.mob_effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.frog.Frog;
import org.jetbrains.annotations.Nullable;
import tallestred.witch_additions.WADataAttachments;
import tallestred.witch_additions.WAMobEffects;
import tocraft.walkers.api.PlayerShape;
import tocraft.walkers.api.PlayerShapeChanger;
import tocraft.walkers.api.variant.ShapeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FrogTransformationEffect extends MobEffect {
    public FrogTransformationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        Collection<MobEffectInstance> originalEffects = new ArrayList<>(livingEntity.getActiveEffects());
        Frog frog;
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            frog = EntityType.FROG.create(livingEntity.level());
            PlayerShape.updateShapes(serverPlayer, frog);
        } else {
            if (livingEntity instanceof PathfinderMob pathfinderMob) {
                String mobId = pathfinderMob.getEncodeId();
                frog = pathfinderMob.convertTo(EntityType.FROG, true);
                for (MobEffectInstance mobeffectinstance : originalEffects) {
                    frog.forceAddEffect(new MobEffectInstance(mobeffectinstance), frog);
                }
                System.out.println("ass");
                frog.copyAttachmentsFrom(pathfinderMob, false);
                frog.setData(WADataAttachments.FROG_TRANSFORMED_FROM.get(), mobId);
            }
        }
    }

    @Override
    public void onMobRemoved(LivingEntity livingEntity, int amplifier, Entity.RemovalReason reason) {
        super.onMobRemoved(livingEntity, amplifier, reason);
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            PlayerShape.updateShapes(serverPlayer, null);
        }
    }
}
