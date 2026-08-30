package com.bruno.ph_and_co.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, "ph_and_co");
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, "ph_and_co");

    public static final Supplier<RecipeSerializer<MortarRecipe>> MORTAR_SERIALIZER =
            SERIALIZERS.register("mortar", MortarRecipeSerializer::new);

    public static final Supplier<RecipeType<MortarRecipe>> MORTAR_TYPE =
            TYPES.register("mortar", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "mortar";
                }
            });
}