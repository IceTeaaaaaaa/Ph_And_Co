package com.bruno.ph_and_co.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public record MortarRecipeInput(List<ItemStack> inputs) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int index) {

        if (index >= 0 && index < inputs.size()) {
            return inputs.get(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 6;
    }
}