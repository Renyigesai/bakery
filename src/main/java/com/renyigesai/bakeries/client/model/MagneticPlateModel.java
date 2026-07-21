package com.renyigesai.bakeries.client.model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.EnumSet;


public class MagneticPlateModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation MAGNETIC_PLATE = new ModelLayerLocation(new ResourceLocation("bakeries", "magnetic_plate"), "main");
	private final ModelPart front;
	private final ModelPart back;
	private final ModelPart top;
	private final ModelPart bottom;
	private final ModelPart left;
	private final ModelPart right;

	public MagneticPlateModel(ModelPart root) {
		this.front = root.getChild("front");
		this.back = root.getChild("back");
		this.top = root.getChild("top");
		this.bottom = root.getChild("bottom");
		this.left = root.getChild("left");
		this.right = root.getChild("right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();

		CubeListBuilder frontBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, EnumSet.of(Direction.SOUTH));
		CubeListBuilder backBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, EnumSet.of(Direction.NORTH));
		CubeListBuilder topBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-8.0F, 0.0F, 0.0F, 16.0F, 0.0F, 1.0F, EnumSet.of(Direction.UP));
		CubeListBuilder bottomBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-8.0F, -16.0F, 0.0F, 16.0F, 0.0F, 1.0F, EnumSet.of(Direction.DOWN));
		CubeListBuilder leftBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(0.0F, -16.0F, 0.0F, 0.0F, 16.0F, 1.0F, EnumSet.of(Direction.WEST));
		CubeListBuilder rightBuilder = CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(0.0F, -16.0F, 0.0F, 0.0F, 16.0F, 1.0F, EnumSet.of(Direction.EAST));
		root.addOrReplaceChild("front", frontBuilder, PartPose.ZERO);
		root.addOrReplaceChild("back", backBuilder, PartPose.offset(0, 0, 1));
		root.addOrReplaceChild("top", topBuilder, PartPose.offset(0, 0, 0));
		root.addOrReplaceChild("bottom", bottomBuilder, PartPose.offset(0, 0, 0));
		root.addOrReplaceChild("left", leftBuilder, PartPose.offset(-8, 0, 0));
		root.addOrReplaceChild("right", rightBuilder, PartPose.offset(8, 0, 0));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
		front.render(poseStack, consumer, light, overlay, r, g, b, a);
		back.render(poseStack, consumer, light, overlay, r, g, b, a);
		top.render(poseStack, consumer, light, overlay, r, g, b, a);
		bottom.render(poseStack, consumer, light, overlay, r, g, b, a);
		left.render(poseStack, consumer, light, overlay, r, g, b, a);
		right.render(poseStack, consumer, light, overlay, r, g, b, a);
	}


}