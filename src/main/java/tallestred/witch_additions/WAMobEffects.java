package tallestred.witch_additions;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tallestred.witch_additions.mob_effects.FrogTransformationEffect;

import java.util.function.Supplier;

public class WAMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, WitchAdditions.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, WitchAdditions.MODID);
    public static final DeferredHolder<MobEffect, FrogTransformationEffect> FROG_TRANSFORMATION_EFFECT = MOB_EFFECTS.register("frog_transformation_effect", () -> new FrogTransformationEffect(
            MobEffectCategory.BENEFICIAL,
            0xffffff
    ));
    public static final Supplier<Potion> FROG_POTION = POTIONS.register("my_potion", () -> new Potion(new MobEffectInstance(FROG_TRANSFORMATION_EFFECT, 100)));
}
