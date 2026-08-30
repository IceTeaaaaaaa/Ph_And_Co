package com.bruno.ph_and_co.datagen;

import com.bruno.ph_and_co.ModItems;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;


import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private Item getExternalItem(String id) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MORTAR.get())
                .requires(ModItems.MORTAR_BASE.get(), 1)
                .requires(ModItems.PESTLE.get(), 1)
                .unlockedBy("has_basalt", has(Items.BASALT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("ph_and_co:mortar_base"))
                .pattern("   ").pattern("A A").pattern(" A ")
                .define('A', Items.SMOOTH_BASALT)
                .unlockedBy("has_basalt", has(Items.BASALT))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar_base"));

        MortarRecipeBuilder.builder()
                // Input
                .addInput(new SizedIngredient(Ingredient.of(ModItems.SILICA.get()), 9))
                .addInput(new SizedIngredient(Ingredient.of(ModItems.SALT.get()), 3))
                .addInput(new SizedIngredient(Ingredient.of(ModItems.CALCIUM.get()), 2))
                .addInput(new SizedIngredient(Ingredient.of(ModItems.MAGNESIUM.get()), 1))

                // Output
                .addOutput(new ItemStack(ModItems.GLASS_POWDER.get(), 3), 1.0f)

                // Process Time(Ticks)
                .setTime(20)

                // UnlockedBy
                .unlockedBy("has_silica", has(ModItems.SILICA.get()))

                // Save
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/glass_powder_full_recipe"));

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(ModItems.SILICA.get()), 1))
                .addInput(new SizedIngredient(Ingredient.of(ModItems.SHATTERED_GLASS.get()), 1))

                .addOutput(new ItemStack(ModItems.GLASS_POWDER.get(), 1), 1.0f)
                .setTime(15)

                .unlockedBy("has_shattered_glass", has(ModItems.SHATTERED_GLASS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/glass_powder_from_shattered"));

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(Tags.Items.SANDS), 3))

                .addOutput(new ItemStack(ModItems.SILICA.get(), 4), 1.0f)
                .setTime(15)

                .unlockedBy("has_sand", has(ItemTags.SAND))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/silica_from_sands"));

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(Items.QUARTZ), 1))

                .addOutput(new ItemStack(ModItems.SILICA.get(), 2), 1.0f)
                .addOutput(new ItemStack(ModItems.SILICA.get(), 1), 0.5f)
                .setTime(15)

                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/silica_from_quartz"));

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(Tags.Items.GLASS_BLOCKS), 1))

                .addOutput(new ItemStack(ModItems.SHATTERED_GLASS.get(), 5), 1.0f)
                .setTime(20)

                .unlockedBy("has_glass", has(Items.GLASS))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/glass_shatter_from_block"));

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(Items.BLAZE_ROD), 1))

                .addOutput(new ItemStack(Items.BLAZE_POWDER, 3), 1.0f)
                .addOutput(new ItemStack(Items.BLAZE_POWDER, 1), 0.3f)
                .setTime(15)

                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/blaze_powder_from_mortar"));


        net.minecraft.core.registries.BuiltInRegistries.ITEM.holders().forEach(holder -> {
            ResourceLocation id = holder.key().location();

            if (id.getNamespace().equals("minecraft") && (id.getPath().endsWith("flower")
                    || id.getPath().contains("tulip") || id.getPath().contains("lily")
                    || id.getPath().contains("poppy") || id.getPath().contains("allium")
                    || id.getPath().contains("bluet") || id.getPath().contains("dandelion")
                    || id.getPath().contains("oxeye") || id.getPath().contains("rose")
                    || id.getPath().contains("lilac") || id.getPath().contains("orchid")
                    || id.getPath().contains("peony"))) {
                Item flowerItem = holder.value();

                Item correspondingDye = switch (id.getPath()) {
                    case "dandelion", "sunflower" -> Items.YELLOW_DYE;
                    case "poppy", "red_tulip", "rose_bush" -> Items.RED_DYE;
                    case "cornflower" -> Items.BLUE_DYE;
                    case "allium", "lilac" -> Items.MAGENTA_DYE;
                    case "azure_bluet", "white_tulip", "lily_of_the_valley" -> Items.WHITE_DYE;
                    case "orange_tulip", "torchflower" -> Items.ORANGE_DYE;
                    case "blue_orchid" -> Items.LIGHT_BLUE_DYE;
                    case "pink_tulip", "peony" -> Items.PINK_DYE;
                    case "oxeye_daisy" -> Items.LIGHT_GRAY_DYE;
                    case "wither_rose" -> Items.BLACK_DYE;
                    default -> Items.GREEN_DYE;
                };

                MortarRecipeBuilder.builder()
                        .addInput((new SizedIngredient(Ingredient.of(ModItems.SILICA.get()), 1)))
                        .addInput(new SizedIngredient(Ingredient.of(flowerItem), 1))
                        .addOutput(new ItemStack(correspondingDye, 4), 1.0f)
                        .setTime(15)
                        .unlockedBy("has_flower", has(flowerItem))
                        .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/mortar_dye_" + id.getPath()));
            }
        });

        MortarRecipeBuilder.builder()

                .addInput(new SizedIngredient(Ingredient.of(Items.CHARCOAL), 1))
                .addInput(new SizedIngredient(Ingredient.of(ModItems.SULFUR), 1))
                .addInput(new SizedIngredient(Ingredient.of(Items.BONE_MEAL), 4))

                .addOutput(new ItemStack(Items.GUNPOWDER, 2), 1.0f)
                .setTime(20)

                .unlockedBy("has_sulfur", has(ModItems.SULFUR))
                .save(output, ResourceLocation.fromNamespaceAndPath("ph_and_co", "mortar/gunpowder_from_sulfur"));

        // =====================================================================
        // CREATE KINETICS OVERRIDES
        // =====================================================================

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_press"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:shaft"))
                .define('B', ModItems.NAIL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', Items.IRON_BLOCK)
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_press"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_mixer"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', ModItems.NAIL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:whisk"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_mixer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:water_wheel"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', ModItems.NAIL.get())
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("create:shaft"))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/water_wheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:large_water_wheel"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', ModItems.NAIL_STEEL.get())
                .define('B', ItemTags.PLANKS)
                .define('C', getExternalItem("create:water_wheel"))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/large_water_wheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_fan"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', ModItems.NAIL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:propeller"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_fan"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:gearbox"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', ModItems.NAIL.get())
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/gearbox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:vertical_gearbox"))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', getExternalItem("create:cogwheel"))
                .define('B', ModItems.NAIL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/vertical_gearbox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:weighted_ejector"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("overgeared:steel_plate"))
                .define('B', ModItems.NAIL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:cogwheel"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/weighted_ejector"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:clutch"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:shaft"))
                .define('C', Items.REDSTONE)
                .define('D', ModItems.NAIL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/clutch"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:gearshift"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', Items.REDSTONE)
                .define('D', ModItems.NAIL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/gearshift"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_chain_drive"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', Items.IRON_NUGGET)
                .define('C', ModItems.NAIL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_chain_drive"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:encased_chain_drive"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:zinc_nugget"))
                .define('C', ModItems.NAIL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/encased_chain_drive_from_zinc"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:large_cogwheel"))
                .pattern("AB").pattern("BC")
                .define('A', getExternalItem("create:shaft"))
                .define('B', ItemTags.PLANKS)
                .define('C', ModItems.NAIL.get())
                .unlockedBy("has_shaft", has(getExternalItem("create:shaft")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/large_cogwheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:cogwheel"))
                .pattern("AB").pattern("C ")
                .define('A', getExternalItem("create:shaft"))
                .define('B', ItemTags.PLANKS)
                .define('C', ModItems.NAIL.get())
                .unlockedBy("has_shaft", has(getExternalItem("create:shaft")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/cogwheel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_drill"))
                .pattern(" A ").pattern("ABA").pattern("CDC")
                .define('A', getExternalItem("create:andesite_alloy"))
                .define('B', Items.IRON_INGOT)
                .define('C', ModItems.NAIL.get())
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_drill"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_saw"))
                .pattern(" A ").pattern("ABA").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', Items.IRON_INGOT)
                .define('C', ModItems.NAIL.get())
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_saw"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:deployer"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', ModItems.NAIL_STEEL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:brass_hand"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/deployer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:portable_storage_interface"))
                .pattern("AB").pattern("C ")
                .define('A', getExternalItem("create:andesite_casing"))
                .define('B', getExternalItem("create:chute"))
                .define('C', ModItems.NAIL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/portable_storage_interface"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_harvester"))
                .pattern("BAB").pattern("BAB").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', ModItems.NAIL.get())
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_harvester"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_plough"))
                .pattern("AAA").pattern("BBB").pattern("CDC")
                .define('A', getExternalItem("overgeared:iron_plate"))
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', ModItems.NAIL.get())
                .define('D', getExternalItem("create:andesite_casing"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_plough"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_roller"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', ModItems.NAIL_STEEL.get())
                .define('C', getExternalItem("create:andesite_casing"))
                .define('D', getExternalItem("create:crushing_wheel"))
                .unlockedBy("has_casing", has(getExternalItem("create:andesite_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_roller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_crafter"), 3)
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:electron_tube"))
                .define('B', ModItems.NAIL_STEEL.get())
                .define('C', getExternalItem("create:brass_casing"))
                .define('D', Items.CRAFTING_TABLE)
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_crafter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:sequenced_gearshift"))
                .pattern("AB").pattern("CD")
                .define('A', getExternalItem("create:brass_casing"))
                .define('B', getExternalItem("create:cogwheel"))
                .define('C', getExternalItem("create:electron_tube"))
                .define('D', ModItems.NAIL_STEEL.get())
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/sequenced_gearshift"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:rotation_speed_controller"))
                .pattern(" A ").pattern("BCB").pattern(" D ")
                .define('A', getExternalItem("create:precision_mechanism"))
                .define('B', ModItems.NAIL_STEEL.get())
                .define('C', getExternalItem("create:brass_casing"))
                .define('D', ModItems.REINFORCED_STEEL_PLATE.get())
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/rotation_speed_controller"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, getExternalItem("create:mechanical_arm"))
                .pattern("AAB").pattern("ACC").pattern("DEF")
                .define('A', ModItems.BRASS_PLATE.get())
                .define('B', getExternalItem("create:andesite_alloy"))
                .define('C', ModItems.NAIL_STEEL.get())
                .define('D', getExternalItem("create:precision_mechanism"))
                .define('E', getExternalItem("create:brass_casing"))
                .define('F', ModItems.REINFORCED_STEEL_PLATE.get())
                .unlockedBy("has_casing", has(getExternalItem("create:brass_casing")))
                .save(output, ResourceLocation.fromNamespaceAndPath("create", "crafting/kinetics/mechanical_arm"));

        // =====================================================================
        // GLASS AND POWDERS
        // =====================================================================



        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "glass_panel_from_blob")) {
            @Override
            protected ProcessingRecipeParams createParams() {
                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }

                .require(ModItems.MOLTEN_GLASS_BLOB.get())

                .output(ModItems.HEATED_GLASS_PANEL.get())
                .build(output);


        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "glass_pane_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(ModItems.HEATED_GLASS_PANEL.get())
                .output(Items.GLASS_PANE)
                .build(output);

        var builder = new ProcessingRecipeBuilder(CompactingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "glass_from_compacting")) {
            @Override
            protected ProcessingRecipeParams createParams() {
                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        };

        for (int i = 0; i < 6; i++) {
            builder.require(ModItems.HEATED_GLASS_PANEL.get());
        }

        builder.output(Items.GLASS)
                .build(output);

        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "heated_iron_plate_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(getExternalItem("overgeared:heated_iron_ingot"))
                .output(ModItems.HEATED_IRON_PLATE.get())
                .build(output);

        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "heated_steel_plate_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(getExternalItem("overgeared:heated_steel_ingot"))
                .output(ModItems.HEATED_STEEL_PLATE.get())
                .build(output);

        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "heated_gold_plate_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(ModItems.HEATED_GOLD_INGOT.get())
                .output(ModItems.HEATED_GOLD_INGOT.get())
                .build(output);

        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "heated_brass_plate_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(ModItems.HEATED_BRASS_INGOT.get())
                .output(ModItems.HEATED_BRASS_PLATE.get())
                .build(output);

        new ProcessingRecipeBuilder(PressingRecipe::new, ResourceLocation.fromNamespaceAndPath("ph_and_co", "heated_copper_plate_from_pressing")) {
            @Override
            protected ProcessingRecipeParams createParams() {

                return new ProcessingRecipeParams() {};
            }

            @Override
            public ProcessingRecipeBuilder self() {
                return this;
            }
        }
                .require(getExternalItem("overgeared:heated_copper_ingot"))
                .output(ModItems.HEATED_COPPER_PLATE.get())
                .build(output);

        new SequencedAssemblyRecipeBuilder(ResourceLocation.fromNamespaceAndPath("ph_and_co", "nail_sequence"))
                .require(ModItems.HEATED_IRON_PLATE.get())
                .transitionTo(ModItems.INCOMPLETE_NAILS.get())
                .addOutput(new ItemStack(ModItems.NAIL.get(), 12), 1.0f)
                .loops(1)

                .addStep(CuttingRecipe::new, step -> step)
                .addStep(PressingRecipe::new, step -> step)
                .addStep(CuttingRecipe::new, step -> step)
                .addStep(PressingRecipe::new, step -> step)

                .addStep(FillingRecipe::new, step -> step.require(Fluids.WATER, 250))
                .build(output);

        new SequencedAssemblyRecipeBuilder(ResourceLocation.fromNamespaceAndPath("ph_and_co", "nail_steel_sequence"))
                .require(ModItems.HEATED_STEEL_PLATE.get())
                .transitionTo(ModItems.INCOMPLETE_NAILS_STEEL.get())
                .addOutput(new ItemStack(ModItems.NAIL_STEEL.get(), 10), 1.0f)
                .loops(1)

                .addStep(CuttingRecipe::new, step -> step)
                .addStep(PressingRecipe::new, step -> step)
                .addStep(CuttingRecipe::new, step -> step)
                .addStep(PressingRecipe::new, step -> step)

                .addStep(FillingRecipe::new, step -> step.require(Fluids.WATER, 250))
                .build(output);


        new SequencedAssemblyRecipeBuilder(ResourceLocation.fromNamespaceAndPath("ph_and_co", "reinforced_steel_plate_sequence"))
                .require(ModItems.HEATED_STEEL_PLATE.get())
                .transitionTo(ModItems.INCOMPLETE_REINFORCED_STEEL_PLATE.get())
                .addOutput(new ItemStack(ModItems.REINFORCED_STEEL_PLATE.get(), 1), 1.0f)
                .loops(1)

                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.HEATED_IRON_PLATE))
                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.HEATED_STEEL_PLATE))

                .addStep(PressingRecipe::new, step -> step)

                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.NAIL_STEEL))
                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.NAIL_STEEL))
                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.NAIL_STEEL))
                .addStep(DeployerApplicationRecipe::new, step -> step.require(ModItems.NAIL_STEEL))

                .addStep(PressingRecipe::new, step -> step)

                .addStep(FillingRecipe::new, step -> step.require(Fluids.WATER, 250))
                .build(output);




        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(Items.RAW_GOLD),    // Input
                        RecipeCategory.MISC,              // Category
                        ModItems.HEATED_GOLD_INGOT.get(), // Output
                        0.7f,                             // XP
                        100                               // Ticks
                )

                .unlockedBy("has_raw_gold", has(Items.RAW_GOLD))
                .save(output, ResourceLocation.withDefaultNamespace("gold_ingot_from_blasting_raw_gold"));

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.GLASS_POWDER.get()),
                        RecipeCategory.MISC,
                        ModItems.MOLTEN_GLASS_BLOB.get(),
                        0.0f,
                        100
                )

                .unlockedBy("has_glass_powder", has(ModItems.GLASS_POWDER.get()))
                .save(output, ResourceLocation.withDefaultNamespace("molten_glass_from_blasting_glass_powder"));
    }


}

