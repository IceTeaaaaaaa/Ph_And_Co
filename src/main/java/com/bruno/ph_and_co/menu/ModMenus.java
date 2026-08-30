package com.bruno.ph_and_co.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, "ph_and_co");

    public static final Supplier<MenuType<MortarMenu>> MORTAR_MENU = MENUS.register("mortar",
            () -> new MenuType<>(MortarMenu::new, FeatureFlags.DEFAULT_FLAGS));
}