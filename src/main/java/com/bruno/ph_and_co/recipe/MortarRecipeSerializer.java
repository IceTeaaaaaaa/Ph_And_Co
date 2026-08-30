package com.bruno.ph_and_co.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class MortarRecipeSerializer implements RecipeSerializer<MortarRecipe> {

    // Lê e escreve o JSON (Para o seu Datagen e arquivos)
    public static final MapCodec<MortarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            // Codec correto do NeoForge para serializar listas de SizedIngredient
            SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(MortarRecipe::ingredients),
            ChanceResult.CODEC.listOf().fieldOf("results").forGetter(MortarRecipe::results),
            Codec.INT.optionalFieldOf("processing_time", 20).forGetter(MortarRecipe::processTime)
    ).apply(instance, MortarRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MortarRecipe> STREAM_CODEC = StreamCodec.composite(
            // StreamCodec correto para SizedIngredient em lista
            SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), MortarRecipe::ingredients,
            ChanceResult.STREAM_CODEC.apply(ByteBufCodecs.list()), MortarRecipe::results,
            ByteBufCodecs.INT, MortarRecipe::processTime,
            MortarRecipe::new
    );

    @Override
    public MapCodec<MortarRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, MortarRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}