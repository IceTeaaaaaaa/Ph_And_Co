package com.bruno.ph_and_co.datagen;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MortarRecipeBuilder implements RecipeBuilder {
    private final List<SizedIngredient> ingredients = new ArrayList<>();
    private final List<ChanceResult> results = new ArrayList<>();
    private int processTime = 20;
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

    public MortarRecipeBuilder setTime(int ticks) {
        this.processTime = ticks;
        return this;
    }

    @Override
    public @NotNull MortarRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull MortarRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        if (results.isEmpty()) {
            return Items.AIR;
        }
        return results.getFirst().stack().getItem();
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("There are no criteria for releasing the revenue. " + id);
        }

        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        MortarRecipe recipe = new MortarRecipe(this.ingredients, this.results, this.processTime);
        recipeOutput.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/mortar/")));
    }

    public @Nullable String getGroup() {
        return group;
    }

    public void setGroup(@Nullable String group) {
        this.group = group;
    }
}