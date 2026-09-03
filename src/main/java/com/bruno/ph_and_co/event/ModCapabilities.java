package com.bruno.ph_and_co.event;

import com.bruno.ph_and_co.PhAndCoMod;
import com.bruno.ph_and_co.blockentity.ModBlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = PhAndCoMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.HEAVY_TANK_BE.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler()
        );
    }
}
