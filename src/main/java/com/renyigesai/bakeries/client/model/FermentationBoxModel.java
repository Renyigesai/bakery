package com.renyigesai.bakeries.client.model;// Made with Blockbench 5.1.1
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

public class FermentationBoxModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation FERMENTATION_BOX = new ModelLayerLocation(new ResourceLocation(BakeriesMod.MODID, "fermentation_box"), "main");
	private final ModelPart all;
	private final ModelPart door;

	public FermentationBoxModel(ModelPart root) {
		this.all = root.getChild("all");
		this.door = this.all.getChild("door");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(54, 32).addBox(-8.0F, -16.0F, -7.0F, 1.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 60).addBox(-7.0F, -16.0F, 7.0F, 14.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 46).addBox(-7.0F, -8.0F, -6.0F, 14.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 46).addBox(-7.0F, -3.0F, -6.0F, 14.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(38, 69).addBox(-6.0F, -15.5F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 69).addBox(-1.0F, -15.5F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-7.0F, -16.0F, -7.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(1, 9).addBox(3.0F, -15.5F, -7.1F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(56, 0).addBox(7.0F, -16.0F, -7.0F, 1.0F, 16.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = all.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-0.5F, -2.0F, -0.15F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -13.5F, -7.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition door = all.addOrReplaceChild("door", CubeListBuilder.create().texOffs(30, 69).addBox(-7.0F, -12.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(34, 69).addBox(6.0F, -12.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(54, 63).addBox(-6.0F, -12.0F, 0.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(54, 67).addBox(-6.0F, -11.0F, -1.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 60).addBox(-6.0F, -10.0F, 0.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(54, 65).addBox(-6.0F, -1.0F, 0.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart getDoor() {
		return door;
	}

	public ModelPart getAll() {
		return all;
	}
}