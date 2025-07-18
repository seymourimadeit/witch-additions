package tallestred.witch_additions.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static tallestred.witch_additions.WitchAdditions.MODID;

public class WitchHatModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID,  "witch_hat"), "main");
    public final ModelPart hatroot;
    private final ModelPart head;
    private final ModelPart headwear;
    private final ModelPart hat2;
    private final ModelPart headwear2;

    public WitchHatModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.hatroot = root.getChild("hatroot");
        this.head = this.hatroot.getChild("head");
        this.headwear = this.hatroot.getChild("headwear");
        this.hat2 = this.hatroot.getChild("hat2");
        this.headwear2 = this.hatroot.getChild("headwear2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hatroot = partdefinition.addOrReplaceChild("hatroot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = hatroot.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition headwear = hatroot.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.6822F, -7.4249F, 16.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition hat2 = hatroot.addOrReplaceChild("hat2", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0F, -11.7434F, -0.514F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition headwear2 = hatroot.addOrReplaceChild("headwear2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.hatroot.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}