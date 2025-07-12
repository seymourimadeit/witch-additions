package tallestred.witch_additions.mob_effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import tallestred.witch_additions.WADataAttachments;
import tocraft.walkers.api.PlayerShape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class MobTransformationEffect extends MobEffect {
    private final EntityType<Mob> convertType;
    private final ModifyMob modifyMob;

    public MobTransformationEffect(MobEffectCategory category, int color, EntityType<?> convertType, ModifyMob modifyMob) {
        super(category, color);
        this.convertType = (EntityType<Mob>) convertType;
        this.modifyMob = modifyMob;
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        Collection<MobEffectInstance> originalEffects = new ArrayList<>(livingEntity.getActiveEffects());
        Mob mob;
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            mob = convertType.create(livingEntity.level());
            PlayerShape.updateShapes(serverPlayer, mob);
            this.modifyMob(mob);
        } else {
            if (livingEntity instanceof PathfinderMob pathfinderMob) {
                String mobId = pathfinderMob.getEncodeId();
                mob = pathfinderMob.convertTo(convertType, true);
                for (MobEffectInstance mobeffectinstance : originalEffects) {
                    mob.forceAddEffect(new MobEffectInstance(mobeffectinstance), mob);
                }
                mob.copyAttachmentsFrom(pathfinderMob, false);
                mob.setData(WADataAttachments.TRANSFORMED_FROM.get(), mobId);
                this.modifyMob(mob);
            }
        }
    }

    @Override
    public void onMobRemoved(LivingEntity livingEntity, int amplifier, Entity.RemovalReason reason) {
        super.onMobRemoved(livingEntity, amplifier, reason);
        if (livingEntity instanceof ServerPlayer serverPlayer)
            PlayerShape.updateShapes(serverPlayer, null);
    }

    public EntityType<Mob> getConvertType() {
        return convertType;
    }

    public void modifyMob(Mob mob) {
        this.modifyMob.modifyMob(mob);
    }

    public interface ModifyMob {
        void modifyMob(Mob mob);
    }
}
