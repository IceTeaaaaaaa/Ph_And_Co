package com.bruno.ph_and_co.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import java.util.List;

// Record maravilhoso da 1.21 que gerencia os 6 slots de entrada
public record MortarRecipeInput(List<ItemStack> inputs) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        // Retorna o item do slot específico, ou vazio se estiver fora do limite
        if (index >= 0 && index < inputs.size()) {
            return inputs.get(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 6; // Temos exatamente 6 slots de pó
    }
}