package com.bruno.ph_and_co.recipe;

import net.minecraft.world.item.ItemStack;

public record ChanceResult(ItemStack stack, float chance) {

    public ItemStack getItem() {
        return stack;
    }

    public static final com.mojang.serialization.Codec<ChanceResult> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(ChanceResult::stack),
            com.mojang.serialization.Codec.FLOAT.optionalFieldOf("chance", 1.0f).forGetter(ChanceResult::chance)
    ).apply(instance, ChanceResult::new));

    public static final net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, ChanceResult> STREAM_CODEC = net.minecraft.network.codec.StreamCodec.composite(
            ItemStack.STREAM_CODEC, ChanceResult::stack,
            net.minecraft.network.codec.ByteBufCodecs.FLOAT, ChanceResult::chance,
            ChanceResult::new
    );
}
