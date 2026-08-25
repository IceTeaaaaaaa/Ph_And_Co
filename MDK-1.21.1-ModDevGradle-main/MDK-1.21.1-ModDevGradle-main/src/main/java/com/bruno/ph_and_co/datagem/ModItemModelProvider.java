package com.bruno.ph_and_co.datagen;

import com.bruno.ph_and_co.ModItems;
import com.bruno.ph_and_co.PhAndCoMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PhAndCoMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // Heated Plates and Ingots
        basicItem(ModItems.HEATED_IRON_PLATE.get());
        basicItem(ModItems.HEATED_GOLD_PLATE.get());
        basicItem(ModItems.HEATED_COPPER_PLATE.get());
        basicItem(ModItems.HEATED_BRASS_PLATE.get());
        basicItem(ModItems.HEATED_STEEL_PLATE.get());
        basicItem(ModItems.HEATED_GOLD_INGOT.get());
        basicItem(ModItems.HEATED_BRASS_INGOT.get());
        basicItem(ModItems.GOLD_PLATE.get());
        basicItem(ModItems.BRASS_PLATE.get());

        // Incomplete materials
        basicItem(ModItems.INCOMPLETE_NAILS.get());
        basicItem(ModItems.INCOMPLETE_NAILS_STEEL.get());
        basicItem(ModItems.INCOMPLETE_REINFORCED_STEEL_PLATE.get());
        basicItem(ModItems.REINFORCED_STEEL_PLATE.get());

        // Chemical Powders and Compounds
        basicItem(ModItems.SILICA.get());
        basicItem(ModItems.SALT.get());
        basicItem(ModItems.CALCIUM.get());
        basicItem(ModItems.MAGNESIUM.get());
        basicItem(ModItems.COMPACTED_SILICA.get());
        basicItem(ModItems.SULFUR.get());

        // Glass
        basicItem(ModItems.GLASS_POWDER.get());
        basicItem(ModItems.HEATED_GLASS_PANEL.get());
        basicItem(ModItems.REINFORCED_GLASS.get());
        basicItem(ModItems.SHATTERED_GLASS.get());
        basicItem(ModItems.LOOSE_SAND.get());
    }
}