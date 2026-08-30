package com.bruno.ph_and_co.integration.emi;

import com.bruno.ph_and_co.ModItems;
import com.bruno.ph_and_co.PhAndCoMod;
import com.bruno.ph_and_co.recipe.ModRecipes;
import com.bruno.ph_and_co.recipe.MortarRecipe;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

//import com.bruno.ph_and_co.block.ModBlocks;

@EmiEntrypoint
public class PhAndCoEmiPlugin implements EmiPlugin {


    public static final ResourceLocation MORTAR_ID = ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "mortar");
    public static final EmiRecipeCategory MORTAR_CATEGORY = new EmiRecipeCategory(MORTAR_ID, EmiStack.of(ModItems.MORTAR.get()));

    @Override
    public void register(EmiRegistry registry) {

        registry.addCategory(MORTAR_CATEGORY);


        registry.addWorkstation(MORTAR_CATEGORY, EmiStack.of(ModItems.MORTAR.get()));

        RecipeManager manager = registry.getRecipeManager();
        for (RecipeHolder<MortarRecipe> recipe : manager.getAllRecipesFor(ModRecipes.MORTAR_TYPE.get())) {
            registry.addRecipe(new MortarEmiRecipe(recipe));
        }
    }
}
