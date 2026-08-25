package com.bruno.ph_and_co;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    // Cria o registro para os itens
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PhAndCoMod.MODID);

    // -------------------------------------------------------------
    // Heated Plates and Ingots
    // -------------------------------------------------------------
    public static final var HEATED_IRON_PLATE = ITEMS.registerSimpleItem("heated_iron_plate", new Item.Properties());
    public static final var HEATED_GOLD_PLATE = ITEMS.registerSimpleItem("heated_gold_plate", new Item.Properties());
    public static final var HEATED_COPPER_PLATE = ITEMS.registerSimpleItem("heated_copper_plate", new Item.Properties());
    public static final var HEATED_BRASS_PLATE = ITEMS.registerSimpleItem("heated_brass_plate", new Item.Properties());
    public static final var HEATED_STEEL_PLATE = ITEMS.registerSimpleItem("heated_steel_plate", new Item.Properties());
    public static final var HEATED_GOLD_INGOT = ITEMS.registerSimpleItem("heated_gold_ingot", new Item.Properties());
    public static final var HEATED_BRASS_INGOT = ITEMS.registerSimpleItem("heated_brass_ingot", new Item.Properties());
    public static final var GOLD_PLATE = ITEMS.registerSimpleItem("gold_plate", new Item.Properties());
    public static final var BRASS_PLATE = ITEMS.registerSimpleItem("brass_plate", new Item.Properties());

    // -------------------------------------------------------------
    // Incomplete materials
    // -------------------------------------------------------------
    public static final var INCOMPLETE_NAILS = ITEMS.registerSimpleItem("incomplete_nails", new Item.Properties());
    public static final var INCOMPLETE_NAILS_STEEL = ITEMS.registerSimpleItem("incomplete_nails_steel", new Item.Properties());
    public static final var INCOMPLETE_REINFORCED_STEEL_PLATE = ITEMS.registerSimpleItem("incomplete_reinforced_steel_plate", new Item.Properties());
    public static final var REINFORCED_STEEL_PLATE = ITEMS.registerSimpleItem("reinforced_steel_plate", new Item.Properties());

    // -------------------------------------------------------------
    // Chemical Powders and Compounds
    // -------------------------------------------------------------
    public static final var SILICA = ITEMS.registerSimpleItem("silica", new Item.Properties());
    public static final var SALT = ITEMS.registerSimpleItem("salt", new Item.Properties());
    public static final var CALCIUM = ITEMS.registerSimpleItem("calcium", new Item.Properties());
    public static final var MAGNESIUM = ITEMS.registerSimpleItem("magnesium", new Item.Properties());
    public static final var COMPACTED_SILICA = ITEMS.registerSimpleItem("compacted_silica", new Item.Properties());
    public static final var SULFUR = ITEMS.registerSimpleItem("sulfur", new Item.Properties());

    // -------------------------------------------------------------
    // Glass
    // -------------------------------------------------------------
    public static final var GLASS_POWDER = ITEMS.registerSimpleItem("glass_powder", new Item.Properties());
    public static final var REINFORCED_GLASS = ITEMS.registerSimpleItem("reinforced_glass", new Item.Properties());
    public static final var HEATED_GLASS_PANEL = ITEMS.registerSimpleItem("heated_glass_panel", new Item.Properties());
    public static final var SHATTERED_GLASS = ITEMS.registerSimpleItem("shattered_glass", new Item.Properties());
    public static final var LOOSE_SAND = ITEMS.registerSimpleItem("loose_sand", new Item.Properties());
}