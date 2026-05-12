package com.renyigesai.bakeries.common.client.model;// Made with Blockbench 5.0.6
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

public class OvenModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation OVEN = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BakeriesMod.MODID, "oven"), "main");
	private final ModelPart all;
	private final ModelPart button_1;
	private final ModelPart button_2;
	private final ModelPart door;

	public OvenModel(ModelPart root) {
		this.all = root.getChild("all");
		this.button_1 = this.all.getChild("button_1");
		this.button_2 = this.all.getChild("button_2");
		this.door = this.all.getChild("door");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 33).addBox(17.0F, -6.0F, -7.0F, 3.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(54, 0).addBox(4.0F, -6.0F, -7.0F, 1.0F, 14.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(5.0F, -6.0F, -7.0F, 12.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(36, 61).addBox(5.0F, -5.0F, 7.0F, 12.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(5.0F, 6.0F, -7.0F, 12.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(36, 33).addBox(5.0F, 4.0F, -6.0F, 12.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(36, 33).addBox(5.0F, 0.0F, -6.0F, 12.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 16.0F, 0.0F));

		PartDefinition button_1 = all.addOrReplaceChild("button_1", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(18.5F, 1.0F, -7.0F));

		PartDefinition button_2 = all.addOrReplaceChild("button_2", CubeListBuilder.create().texOffs(30, 62).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(18.5F, 4.0F, -7.0F));

		PartDefinition door = all.addOrReplaceChild("door", CubeListBuilder.create().texOffs(2, 2).addBox(3.0F, -2.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(4.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(0.0F, -9.0F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(-5.0F, -6.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(54, 29).addBox(-6.0F, -11.0F, -1.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(22, 62).addBox(-6.0F, -11.0F, 0.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 62).addBox(-5.0F, -11.0F, 0.0F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(54, 31).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 62).addBox(5.0F, -11.0F, 0.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(4.0F, -9.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(-5.0F, -8.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 2).addBox(-4.0F, -9.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 6.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	public ModelPart getAll() {
		return all;
	}

	public ModelPart getDoor() {
		return door;
	}

	public ModelPart getButton1() {
		return button_1;
	}

	public ModelPart getButton2() {
		return button_2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int i2) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay);
	}
}