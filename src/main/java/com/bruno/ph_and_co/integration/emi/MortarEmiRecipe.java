package com.bruno.ph_and_co.integration.emi;

import com.bruno.ph_and_co.recipe.ChanceResult;
import com.bruno.ph_and_co.recipe.MortarRecipe;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class MortarEmiRecipe implements EmiRecipe {

    private final ResourceLocation id;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> outputs;
    private final int processTime;

    public MortarEmiRecipe(RecipeHolder<MortarRecipe> recipeHolder) {
        this.id = recipeHolder.id();
        MortarRecipe recipe = recipeHolder.value();
        this.processTime = recipe.processTime();

        this.inputs = new ArrayList<>();
        for (SizedIngredient sizedIng : recipe.ingredients()) {
            this.inputs.add(EmiIngredient.of(sizedIng.ingredient(), sizedIng.count()));
        }


        this.outputs = new ArrayList<>();
        for (ChanceResult chanceResult : recipe.results()) {
            EmiStack emiStack = EmiStack.of(chanceResult.stack());
            if (chanceResult.chance() < 1.0f) {
                emiStack.setChance(chanceResult.chance()); // Mostra % de chance no JEI/EMI!
            }
            this.outputs.add(emiStack);
        }
    }

    @Override
    public EmiRecipeCategory getCategory() { return PhAndCoEmiPlugin.MORTAR_CATEGORY; }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public List<EmiIngredient> getInputs() { return inputs; }

    @Override
    public List<EmiStack> getOutputs() { return outputs; }


    @Override
    public int getDisplayWidth() { return 134; }

    @Override
    public int getDisplayHeight() { return 46; }

    @Override
    public void addWidgets(WidgetHolder widgets) {

        for (int i = 0; i < inputs.size(); i++) {
            int column = i % 3;
            int row = i / 3;

            int x = 4 + (column * 18);
            int y = 5 + (row * 18);

            widgets.addSlot(inputs.get(i), x, y);
        }


        widgets.addFillingArrow(62, 14, processTime * 50);


        for (int i = 0; i < outputs.size(); i++) {
            // Se tiver mais de uma saída, elas ficam lado a lado
            int x = 94 + (i * 18);
            widgets.addSlot(outputs.get(i), x, 14).recipeContext(this);
        }
    }
}