package com.renyigesai.bakeries.block.luminous_light_sign;

import com.renyigesai.bakeries.init.BakeriesBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LuminousLightSignBlockEntity extends BlockEntity {
    private String text;
    private int color;
    public LuminousLightSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BakeriesBlocks.LUMINOUS_LIGHT_SIGN_ENTITY.get(), pPos, pBlockState);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("Text", Objects.requireNonNullElse(text, ""));
        pTag.putInt("Color",color);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Text")) {
            text = pTag.getString("Text");
        }
        color = pTag.getInt("Color");
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


}
