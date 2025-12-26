package com.renyigesai.bakeries.client.model;// Made with Blockbench 5.0.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.renyigesai.bakeries.BakeriesMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BlenderModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation BLENDER =
			new ModelLayerLocation(new ResourceLocation(BakeriesMod.MODID, "blender"), "main");
	private final ModelPart all;
	private final ModelPart up;
	private final ModelPart head;

	public BlenderModel(ModelPart root) {
		this.all = root.getChild("all");
		this.up = this.all.getChild("up");
		this.head = this.up.getChild("head");
	}



	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -8.0F, 8.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(30, 18).addBox(-3.0F, -8.0F, 2.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(16, 32).addBox(-3.0F, -8.0F, 1.0F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-3.0F, -8.0F, 7.0F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 42).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(2.0F, -7.0F, -6.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 9).addBox(-3.0F, -7.0F, -6.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(14, 45).addBox(-3.0F, -7.0F, -2.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(46, 42).addBox(-3.0F, -7.0F, -7.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-2.0F, -10.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition up = all.addOrReplaceChild("up", CubeListBuilder.create().texOffs(14, 40).addBox(-2.0F, -2.0F, -14.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(52, 18).addBox(3.0F, -6.0F, -12.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-3.0F, -7.0F, -15.0F, 6.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(28, 47).addBox(-3.0F, -5.0F, -1.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 30).addBox(-3.0F, -7.0F, -6.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-3.0F, -7.0F, -16.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 8.0F));

		PartDefinition head = up.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 48).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -12.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 48).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart getAll() {
		return all;
	}

	public ModelPart getHead() {
		return head;
	}

	public ModelPart getUp() {
		return up;
	}
}