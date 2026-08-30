package com.bruno.ph_and_co.screen;

import com.bruno.ph_and_co.PhAndCoMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import com.bruno.ph_and_co.menu.ModMenus;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import com.bruno.ph_and_co.menu.ModMenus;

public class ClientSetup {

    public static void onRegisterMenus(RegisterMenuScreensEvent event) {
        event.register(ModMenus.MORTAR_MENU.get(), MortarScreen::new);
    }
}
