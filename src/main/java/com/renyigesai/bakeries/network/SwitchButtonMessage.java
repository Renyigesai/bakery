package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.api.block.BakeriesWorkBlock;
import com.renyigesai.bakeries.init.BakeriesBlocks;
import com.renyigesai.bakeries.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchButtonMessage {
    private final int buttonID, x, y, z;

    public SwitchButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SwitchButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public static void toBytes(SwitchButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handle(SwitchButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int flag, int x, int y, int z) {
        Level world = entity.level();
        if (!world.hasChunkAt(new BlockPos(x, y, z)))
            return;
        BlockPos pos = BlockPos.containing(x, y, z);
        Block targetBlock = null;
        boolean isDoughCraftingTable = false;
        switch (flag){
            case 0 -> {
                targetBlock = BakeriesBlocks.BLENDER.get();
            }
            case 1 -> {
                targetBlock =  BakeriesBlocks.DOUGH_CRAFTING_TABLE.get();
                isDoughCraftingTable = true;
            }
            case 2 -> {
                targetBlock =  BakeriesBlocks.OVEN.get();
            }
        }
        if (targetBlock != null){
            BlockPos newPos = WorldUtil.findFirstIn5x5Cube(world,pos,targetBlock);
            if (newPos != null){
                BlockEntity blockEntity = world.getBlockEntity(newPos);
                BlockState blockState = blockEntity.getBlockState();
                if (isDoughCraftingTable){
                    entity.openMenu(blockState.getMenuProvider(world, newPos));
                    entity.awardStat(Stats.INTERACT_WITH_LOOM);
                    return;
                }
                if ((blockEntity instanceof BaseContainerBlockEntity container) && blockEntity instanceof BakeriesWorkBlock work) {
                    BakeriesWorkBlock.openScreen(((ServerPlayer) entity), container, newPos,world,blockState,work.getOpenSound());
                }
            }
        }
    }
}
