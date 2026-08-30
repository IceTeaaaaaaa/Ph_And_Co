package com.bruno.ph_and_co.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;


public record MortarRecipe(
        List<SizedIngredient> ingredients, // Ingredientes COM quantidade (ex: 9 silica)
        List<ChanceResult> results,           // Saídas (Até 2)
        int processTime                    // Tempo em ticks (ex: 20)
) implements Recipe<MortarRecipeInput> {

    @Override
    public boolean matches(MortarRecipeInput input, Level level) {
        // Pega tudo que tem dentro da máquina
        java.util.List<ItemStack> available = new java.util.ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (!input.getItem(i).isEmpty()) {
                available.add(input.getItem(i).copy());
            }
        }

        // Verifica se cada ingrediente da receita existe na quantidade certa
        for (SizedIngredient sizedIng : ingredients) {
            boolean found = false;
            for (int i = 0; i < available.size(); i++) {
                ItemStack stack = available.get(i);
                if (sizedIng.ingredient().test(stack) && stack.getCount() >= sizedIng.count()) {
                    found = true;
                    stack.shrink(sizedIng.count()); // Consumo virtual só para validar
                    break;
                }
            }
            if (!found) return false; // Faltou algum pó ou a quantidade tá errada
        }
        return true;
    }

    @Override
    public ItemStack assemble(MortarRecipeInput input, HolderLookup.Provider provider) {
        // Retorna o primeiro resultado para validações do Minecraft
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0).stack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true; // É uma interface portátil, não depende de grid espacial
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return results.isEmpty() ? ItemStack.EMPTY : results.get(0).stack().copy();
    }

    // Retorna todos os ingredientes originais
    public List<SizedIngredient> getIngredientsSized() {
        return ingredients;
    }

    // Retorna todos os resultados (os 2 slots)
    public List<ChanceResult> getResults() {
        return results;
    }

    // Esses dois nós vamos registrar na próxima etapa!
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MORTAR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MORTAR_TYPE.get();
    }
}