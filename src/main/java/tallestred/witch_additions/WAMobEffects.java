package tallestred.witch_additions;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tallestred.witch_additions.mob_effects.MobTransformationEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static tallestred.witch_additions.WitchAdditions.ITEM_STACKS;


@EventBusSubscriber
public class WAMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, WitchAdditions.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, WitchAdditions.MODID);
    public static final DeferredHolder<MobEffect, MobTransformationEffect> FROG_TRANSFORMATION_EFFECT = registerTransformation("frog", EntityType.FROG, 60, 11521536);
    public static final DeferredHolder<MobEffect, MobTransformationEffect> AXO_TRANSFORMATION_EFFECT = registerTransformation("axo", EntityType.AXOLOTL, 60, 61403, mob -> {
        if (mob instanceof Axolotl axo && axo.level().getRandom().nextFloat() < 0.90) {
            axo.setVariant(Axolotl.Variant.WILD);
        }
    });
    public static final DeferredHolder<MobEffect, MobTransformationEffect> CAT_TRANSFORMATION_EFFECT = registerTransformation("cat", EntityType.CAT, 60, 16350378,mob -> {
        if (mob instanceof Cat cat && cat.level().getRandom().nextFloat() < 0.90) {
            cat.setVariant(BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(CatVariant.ALL_BLACK));
        }
    });
    public static final DeferredHolder<MobEffect, MobTransformationEffect> SPIDER_TRANSFORMATION_EFFECT = registerTransformation("spi", EntityType.SPIDER, 60, 16057389);

    public static DeferredHolder<MobEffect, MobTransformationEffect> registerTransformation(String mobName, EntityType type, int duration, int colorCode) {
        return registerTransformation(mobName, type, duration, colorCode, mob -> {
        });
    }

    public static DeferredHolder<MobEffect, MobTransformationEffect> registerTransformation(String mobName, EntityType type, int duration, int colorCode, MobTransformationEffect.ModifyMob modifyMob) {
        DeferredHolder<MobEffect, MobTransformationEffect> effect = MOB_EFFECTS.register(mobName + "_transformation_effect", () -> new MobTransformationEffect(
                MobEffectCategory.NEUTRAL,
                colorCode, type, modifyMob
        ));
        DeferredHolder<Item, Item> potion = ITEMS.register(mobName + "_potion", () -> new PotionItem(new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY.withEffectAdded(new MobEffectInstance(effect, duration)))));
        DeferredHolder<Item, Item> splashPotion = ITEMS.register(mobName + "_splash_potion", () -> new ThrowablePotionItem(new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY.withEffectAdded(new MobEffectInstance(effect, duration)))));
        ITEM_STACKS.add(potion);
        ITEM_STACKS.add(splashPotion);
        return effect;
    }

    @SubscribeEvent
    public static void creativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            ITEM_STACKS.forEach(itemLike -> event.accept(itemLike.get()));
        }
    }
}
