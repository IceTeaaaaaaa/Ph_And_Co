package com.bruno.ph_and_co.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public record MortarRecipe(
        List<SizedIngredient> ingredients,
        List<ChanceResult> results,
        int processTime
) implements Recipe<MortarRecipeInput> {

    @Override
    public boolean matches(MortarRecipeInput input, @NotNull Level level) {

        java.util.List<ItemStack> available = new java.util.ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (!input.getItem(i).isEmpty()) {
                available.add(input.getItem(i).copy());
            }
        }

        for (SizedIngredient sizedIng : ingredients) {
            boolean found = false;
            for (ItemStack stack : available) {
                if (sizedIng.ingredient().test(stack) && stack.getCount() >= sizedIng.count()) {
                    found = true;
                    stack.shrink(sizedIng.count());
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MortarRecipeInput input, HolderLookup.@NotNull Provider provider) {

        return results.isEmpty() ? ItemStack.EMPTY : results.getFirst().stack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return results.isEmpty() ? ItemStack.EMPTY : results.getFirst().stack().copy();
    }

    public List<SizedIngredient> getIngredientsSized() {
        return ingredients;
    }

    public List<ChanceResult> getResults() {
        return results;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.MORTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.MORTAR_TYPE.get();
    }
}