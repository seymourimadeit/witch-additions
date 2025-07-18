package tallestred.witch_additions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import tallestred.witch_additions.WAMobEffectsAndItems;
import tallestred.witch_additions.WitchAdditionsClient;
import tallestred.witch_additions.client.models.WitchHatModel;

public class WitchHatRenderer extends BlockEntityWithoutLevelRenderer {
    public final WitchHatModel witchHatModel;

    public WitchHatRenderer(BlockEntityRenderDispatcher berd, EntityModelSet set) {
        super(berd, set);
        this.witchHatModel = new WitchHatModel(set.bakeLayer(WitchHatModel.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext p_239207_2_, PoseStack matrixStack,
                             MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();
        if (item == WAMobEffectsAndItems.WITCH_HAT.get()) {
            matrixStack.pushPose();
            matrixStack.scale(1.0F, -1.0F, -1.0F);
            Material rendermaterial = WitchAdditionsClient.WITCH_HAT_MODEL;
            VertexConsumer ivertexbuilder = rendermaterial.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer,
                    this.witchHatModel.renderType(rendermaterial.atlasLocation()), true, stack.hasFoil()));
            this.witchHatModel.renderToBuffer(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, -1);
            matrixStack.popPose();
        }
    }
}
