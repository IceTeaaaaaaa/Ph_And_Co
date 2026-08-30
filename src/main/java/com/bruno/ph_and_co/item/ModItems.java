package com.bruno.ph_and_co.item;

import com.bruno.ph_and_co.PhAndCoMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem; // Importação necessária
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PhAndCoMod.MOD_ID);

    // -------------------------------------------------------------
    // Heated Plates and Ingots
    // -------------------------------------------------------------
    public static final DeferredItem<Item> HEATED_IRON_PLATE = ITEMS.registerSimpleItem("heated_iron_plate", new Item.Properties());
    public static final DeferredItem<Item> HEATED_GOLD_PLATE = ITEMS.registerSimpleItem("heated_gold_plate", new Item.Properties());
    public static final DeferredItem<Item> HEATED_COPPER_PLATE = ITEMS.registerSimpleItem("heated_copper_plate", new Item.Properties());
    public static final DeferredItem<Item> HEATED_BRASS_PLATE = ITEMS.registerSimpleItem("heated_brass_plate", new Item.Properties());
    public static final DeferredItem<Item> HEATED_STEEL_PLATE = ITEMS.registerSimpleItem("heated_steel_plate", new Item.Properties());
    public static final DeferredItem<Item> HEATED_GOLD_INGOT = ITEMS.registerSimpleItem("heated_gold_ingot", new Item.Properties());
    public static final DeferredItem<Item> HEATED_BRASS_INGOT = ITEMS.registerSimpleItem("heated_brass_ingot", new Item.Properties());
    public static final DeferredItem<Item> GOLD_PLATE = ITEMS.registerSimpleItem("gold_plate", new Item.Properties());
    public static final DeferredItem<Item> BRASS_PLATE = ITEMS.registerSimpleItem("brass_plate", new Item.Properties());

    // -------------------------------------------------------------
    // Incomplete materials
    // -------------------------------------------------------------
    public static final DeferredItem<Item> INCOMPLETE_NAILS = ITEMS.registerSimpleItem("incomplete_nails", new Item.Properties());
    public static final DeferredItem<Item> INCOMPLETE_NAILS_STEEL = ITEMS.registerSimpleItem("incomplete_nails_steel", new Item.Properties());
    public static final DeferredItem<Item> INCOMPLETE_REINFORCED_STEEL_PLATE = ITEMS.registerSimpleItem("incomplete_reinforced_steel_plate", new Item.Properties());
    public static final DeferredItem<Item> REINFORCED_STEEL_PLATE = ITEMS.registerSimpleItem("reinforced_steel_plate", new Item.Properties());

    // -------------------------------------------------------------
    // Chemical Powders and Compounds
    // -------------------------------------------------------------

    public static final Supplier<Item> MORTAR = ITEMS.register("mortar", () -> new MortarItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MORTAR_BASE = ITEMS.registerSimpleItem("mortar_base", new Item.Properties());
    public static final DeferredItem<Item> PESTLE = ITEMS.registerSimpleItem("pestle", new Item.Properties());


    public static final DeferredItem<Item> SILICA = ITEMS.registerSimpleItem("silica", new Item.Properties());
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt", new Item.Properties());
    public static final DeferredItem<Item> CALCIUM = ITEMS.registerSimpleItem("calcium", new Item.Properties());
    public static final DeferredItem<Item> MAGNESIUM = ITEMS.registerSimpleItem("magnesium", new Item.Properties());
    public static final DeferredItem<Item> SULFUR = ITEMS.registerSimpleItem("sulfur", new Item.Properties());
    public static final DeferredItem<Item> CRUSHED_ANDESITE = ITEMS.registerSimpleItem("crushed_andesite", new Item.Properties());

    // -------------------------------------------------------------
    // Glass
    // -------------------------------------------------------------
    public static final DeferredItem<Item> GLASS_POWDER = ITEMS.registerSimpleItem("glass_powder", new Item.Properties());
    public static final DeferredItem<Item> REINFORCED_GLASS = ITEMS.registerSimpleItem("reinforced_glass", new Item.Properties());
    public static final DeferredItem<Item> HEATED_GLASS_PANEL = ITEMS.registerSimpleItem("heated_glass_panel", new Item.Properties());
    public static final DeferredItem<Item> SHATTERED_GLASS = ITEMS.registerSimpleItem("shattered_glass", new Item.Properties());
    public static final DeferredItem<Item> LOOSE_SAND = ITEMS.registerSimpleItem("loose_sand", new Item.Properties());
    public static final DeferredItem<Item> MOLTEN_GLASS_BLOB = ITEMS.registerSimpleItem("molten_glass_blob", new Item.Properties());


    // -------------------------------------------------------------
    // Clock work
    // -------------------------------------------------------------
    public static final DeferredItem<Item> NAIL = ITEMS.registerSimpleItem("nail", new Item.Properties());
    public static final DeferredItem<Item> NAIL_STEEL = ITEMS.registerSimpleItem("nail_steel", new Item.Properties());
    public static final DeferredItem<Item> BRASS_SCREW = ITEMS.registerSimpleItem("brass_screw", new Item.Properties());
    public static final DeferredItem<Item> RUDIMENTARY_COMPONENT = ITEMS.registerSimpleItem("rudimentary_component", new Item.Properties());
    public static final DeferredItem<Item> ESSENTIAL_COMPONENT = ITEMS.registerSimpleItem("essential_component", new Item.Properties());
    public static final DeferredItem<Item> SOPHISTICATED_COMPONENT = ITEMS.registerSimpleItem("sophisticated_component", new Item.Properties());
    public static final DeferredItem<Item> FLAWLESS_COMPONENT = ITEMS.registerSimpleItem("flawless_component", new Item.Properties());


}