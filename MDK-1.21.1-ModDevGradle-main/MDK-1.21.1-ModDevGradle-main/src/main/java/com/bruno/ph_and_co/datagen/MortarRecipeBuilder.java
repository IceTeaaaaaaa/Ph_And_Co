package com.bruno.ph_and_co.datagen; // Ajuste se necessário

import com.bruno.ph_and_co.recipe.ChanceResult;
import com.bruno.ph_and_co.recipe.MortarRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MortarRecipeBuilder implements RecipeBuilder {
    private final List<SizedIngredient> ingredients = new ArrayList<>();
    private final List<ChanceResult> results = new ArrayList<>();
    private int processTime = 20; // 20 ticks (1 segundo) é o padrão se você não definir
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public static MortarRecipeBuilder builder() {
        return new MortarRecipeBuilder();
    }

    public MortarRecipeBuilder addInput(SizedIngredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public MortarRecipeBuilder addOutput(ItemStack stack, float chance) {
        this.results.add(new ChanceResult(stack, chance));
        return this;
    }

    public MortarRecipeBuilder addOutput(ItemStack stack) {
        return addOutput(stack, 1.0f);
    }

    public MortarRecipeBuilder setTime(int ticks) {
        this.processTime = ticks;
        return this;
    }

    @Override
    public MortarRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public MortarRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        if (results.isEmpty()) {
            return Items.AIR;
        }
        return results.get(0).stack().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("Nenhum critério de desbloqueio para a receita: " + id);
        }

        // Cria o avanço de desbloqueio para a receita aparecer no livrinho verde
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        MortarRecipe recipe = new MortarRecipe(this.ingredients, this.results, this.processTime);
        recipeOutput.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/mortar/")));
    }
}