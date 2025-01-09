package com.renyigesai.bakeries.network;

import com.renyigesai.bakeries.api.Shortcuts;
import com.renyigesai.bakeries.api.item.FoodBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class FoodBlockItemMessages{
    private final UseOnContext useOnContext;
    private final ItemStack itemStack;
    public FoodBlockItemMessages(FriendlyByteBuf buffer){
        this.useOnContext = readTextState(buffer);
        this.itemStack = buffer.readItem();
    }
    public FoodBlockItemMessages(UseOnContext useOnContext, ItemStack itemStack) {
        this.useOnContext = useOnContext;
        this.itemStack = itemStack;
    }

    public static void toBytes(FoodBlockItemMessages message, FriendlyByteBuf buffer) {
        buffer.writeItem(message.itemStack);
        writeTextState(message.useOnContext, buffer);
    }

    public static void handle(FoodBlockItemMessages message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context networkEventContext = contextSupplier.get();
        networkEventContext.enqueueWork(() -> {
            UseOnContext context = message.useOnContext;
            ItemStack itemStack = message.itemStack;

            if(context.getItemInHand() == itemStack && itemStack.getItem() instanceof FoodBlockItem foodBlockItem){
                Shortcuts.setBlock(context.getLevel(),context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()),foodBlockItem.integerProperty, 1, true);
                context.getItemInHand().shrink(1);
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.WOOL_STEP, SoundSource.PLAYERS, 0.8F, 0.8F);
            }

        });
        networkEventContext.setPacketHandled(true);
    }

    public static void writeTextState(UseOnContext pContext, FriendlyByteBuf buffer) {
        int[] player = UUIDUtil.uuidToIntArray(Objects.requireNonNull(pContext.getPlayer()).getUUID());
        InteractionHand hand = pContext.getHand();
        Direction direction = pContext.getClickedFace();
        Vec3 vec3 = pContext.getClickLocation();
        BlockPos pos = pContext.getClickedPos();

        buffer.writeVarIntArray(player);
        buffer.writeInt(hand.ordinal());
        buffer.writeInt(direction.ordinal());
        buffer.writeDouble(vec3.x);
        buffer.writeDouble(vec3.y);
        buffer.writeDouble(vec3.z);
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
    }
    public static UUID getUUID(int[] pKey) {
        if (pKey.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + pKey.length + ".");
        } else {
            return UUIDUtil.uuidFromIntArray(pKey);
        }
    }

    public static UseOnContext readTextState(FriendlyByteBuf buffer) {
        int[] playerArray = buffer.readVarIntArray();
        UUID playerUUID = getUUID(playerArray);
        Player player = null;
        if (Minecraft.getInstance().level != null) {
            player = Minecraft.getInstance().level.getPlayerByUUID(playerUUID);
        }
        InteractionHand hand = InteractionHand.values()[buffer.readInt()];
        Direction direction = Direction.values()[buffer.readInt()];
        BlockPos pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
        Vec3 vec3 = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

        if (player != null) {
            return new UseOnContext(player, hand, new BlockHitResult(vec3, direction, pos, false));
        }
        return null;
    }
}
