package tallestred.witch_additions.common.mob_effects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import tallestred.witch_additions.WADataAttachments;
import tocraft.walkers.api.PlayerShape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class MobTransformationEffect extends MobEffect {
    protected final EntityType<Mob> convertType;
    protected final ModifyMob modifyMob;
    protected final ConvertFailure convertFailure;
    protected final Predicate<LivingEntity> shouldConvert;
    protected final Predicate<LivingEntity> stopApply;
    protected final boolean convertAtEnd;

    public MobTransformationEffect(MobEffectCategory category, int color, EntityType<?> convertType, ModifyMob modifyMob, Predicate<LivingEntity> shouldConvert, boolean convertAtEnd, ConvertFailure convertFailure, Predicate<LivingEntity> stopApply) {
        super(category, color);
        this.convertType = (EntityType<Mob>) convertType;
        this.modifyMob = modifyMob;
        this.shouldConvert = shouldConvert;
        this.convertAtEnd = convertAtEnd;
        this.convertFailure = convertFailure;
        this.stopApply = stopApply;
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        if (!this.shouldConvert.test(livingEntity)) {
            this.convertFailure(livingEntity);
            return;
        }
        if (!isConvertAtEnd())
            this.transformMob(livingEntity);
    }


    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (this.stopApply.test(livingEntity))
            this.removeThisEffect(livingEntity);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


    public boolean isConvertAtEnd() {
        return convertAtEnd;
    }

    public void transformMob(LivingEntity livingEntity) {
        if (!this.shouldConvert.test(livingEntity))
            return;
        Collection<MobEffectInstance> originalEffects = new ArrayList<>(livingEntity.getActiveEffects());
        Mob mob;
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            mob = convertType.create(livingEntity.level());
            PlayerShape.updateShapes(serverPlayer, mob);
            this.modifyMob(mob);
        } else {
            if (livingEntity instanceof PathfinderMob pathfinderMob) {
                if (pathfinderMob instanceof Villager villager) {
                    villager.releasePoi(MemoryModuleType.HOME);
                    villager.releasePoi(MemoryModuleType.JOB_SITE);
                    villager.releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
                    villager.releasePoi(MemoryModuleType.MEETING_POINT);
                }
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

    public void convertFailure(LivingEntity mob) {
        this.removeThisEffect(mob);
        this.convertFailure.convertFailure(mob);
    }

    public void removeThisEffect(LivingEntity mob) {
        mob.removeEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(this));
    }

    @FunctionalInterface
    public interface ModifyMob {
        void modifyMob(Mob mob);
    }

    @FunctionalInterface
    public interface ConvertFailure {
        void convertFailure(LivingEntity mob);
    }
}
