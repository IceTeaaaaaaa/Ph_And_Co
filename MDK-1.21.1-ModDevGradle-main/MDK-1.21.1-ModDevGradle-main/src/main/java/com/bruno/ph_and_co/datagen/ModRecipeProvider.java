package com.bruno.ph_and_co.datagen;

import com.bruno.ph_and_co.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    // Helper method to fetch items from other mods safely without triggering classloader issues
    private Item getExternalItem(String id) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // =====================================================================
        // CREATE KINETICS OVERRIDES
        // =====================================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_press"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:shaft"))
                .define('B', getExternalItem("cgs:nail"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', Items.IRON_BLOCK)
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_press"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_mixer"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', getExternalItem("cgs:nail"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:whisk"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_mixer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:water_wheel"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', getExternalItem("cgs:nail"))
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("create:shaft"))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/water_wheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:large_water_wheel"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', getExternalItem("cgs:nail_steel"))
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("create:water_wheel"))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/large_water_wheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_fan"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', getExternalItem("cgs:nail"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:propeller"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_fan"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:gearbox"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', getExternalItem("cgs:nail"))
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/gearbox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:vertical_gearbox"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', getExternalItem("cgs:nail"))
                .define('C', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/vertical_gearbox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:weighted_ejector"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("overgeared:steel_plate"))
                .define('B', getExternalItem("cgs:nail"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:cogwheel"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/weighted_ejector"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:clutch"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:shaft"))
                .define('C', Items.REDSTONE)
                .define('D', getExternalItem("cgs:nail"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/clutch"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:gearshift"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', Items.REDSTONE)
                .define('D', getExternalItem("cgs:nail"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/gearshift"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_chain_drive"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', Items.IRON_NUGGET)
                .define('C', getExternalItem("cgs:nail"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_chain_drive"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_chain_drive"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:zinc_nugget"))
                .define('C', getExternalItem("cgs:nail"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_chain_drive_from_zinc"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:large_cogwheel"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:shaft"))
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("cgs:nail"))
                .unlockedBy("has_shaft", has(getExternalItem("create:shaft")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/large_cogwheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:cogwheel"))
                .pattern("AB").pattern("C ")
                .define('A', getExternalItem("create:shaft"))
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("cgs:nail"))
                .unlockedBy("has_shaft", has(getExternalItem("create:shaft")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/cogwheel"));

        // =====================================================================
        // ADVANCED KINETICS
        // =====================================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_drill"))
                .pattern(" A ").pattern("ABA").pattern("CDC")
                .define('A', getExternalItem("create:andesite_alloy"))
                .define('B', Items.IRON_INGOT)
                .define('C', getExternalItem("cgs:nail"))
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_drill"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_saw"))
                .pattern(" A ").pattern("ABA").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', Items.IRON_INGOT)
                .define('C', getExternalItem("cgs:nail"))
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_saw"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:deployer"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', getExternalItem("cgs:nail_steel"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:brass_hand"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/deployer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:portable_storage_interface"))
                .pattern("AB").pattern("C ")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:chute"))
                .define('C', getExternalItem("cgs:nail"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/portable_storage_interface"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_harvester"))
                .pattern("BAB").pattern("BAB").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', getExternalItem("cgs:nail"))
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_harvester"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_plough"))
                .pattern("AAA").pattern("BBB").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', getExternalItem("cgs:nail"))
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_plough"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_roller"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', getExternalItem("cgs:nail_steel"))
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:crushing_wheel"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_roller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_crafter"), 3)
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', getExternalItem("cgs:nail_steel"))
                .define('C', getExternalItem("create:brass_casing"))
                .define('D', Items.CRAFTING_TABLE)
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_crafter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:sequenced_gearshift"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:brass_casing"))
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', getExternalItem("create:electron_tube"))
                .define('D', getExternalItem("cgs:nail_steel"))
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/sequenced_gearshift"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:rotation_speed_controller"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:precision_mechanism"))
                .define('B', getExternalItem("cgs:nail_steel"))
                .define('C', getExternalItem("create:brass_casing"))
                .define('D', ModItems.REINFORCED_STEEL_PLATE.get())
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/rotation_speed_controller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_arm"))
                .pattern("AAB").pattern("ACC").pattern("DEF")
                .define('A', ModItems.BRASS_PLATE.get())
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', getExternalItem("cgs:nail_steel"))
                .define('D', getExternalItem("create:precision_mechanism"))
                .define('E', getExternalItem("create:brass_casing"))
                .define('F', ModItems.REINFORCED_STEEL_PLATE.get())
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_arm"));

        // =====================================================================
        // GLASS AND POWDERS
        // =====================================================================

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COMPACTED_SILICA.get())
                .requires(ModItems.SILICA.get(), 9)
                .unlockedBy("has_silica", has(ModItems.SILICA.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("ph_and_co", "compacted_silica"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_POWDER.get(), 2)
                .requires(ModItems.SILICA.get(), 5)
                .requires(ModItems.SHATTERED_GLASS.get(), 3)
                .requires(ModItems.SALT.get(), 1)
                .unlockedBy("has_silica", has(ModItems.SILICA.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("ph_and_co", "glass_powder_shapeless"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_POWDER.get(), 3)
                .pattern(" K ").pattern("NNN").pattern("CCM")
                .define('K', ModItems.COMPACTED_SILICA.get())
                .define('N', ModItems.SALT.get())
                .define('C', ModItems.CALCIUM.get())
                .define('M', ModItems.MAGNESIUM.get())
                .unlockedBy("has_compacted_silica", has(ModItems.COMPACTED_SILICA.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("ph_and_co", "glass_powder_shaped"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LOOSE_SAND.get(), 2)
                .requires(Items.SAND, 2)
                .unlockedBy("has_sand", has(Items.SAND))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("ph_and_co", "loose_sand"));
    }
}