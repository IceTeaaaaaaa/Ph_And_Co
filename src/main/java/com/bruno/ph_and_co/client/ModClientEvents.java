package com.bruno.ph_and_co.client;

import com.bruno.ph_and_co.PhAndCoMod;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = PhAndCoMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        for (var entry : event.getModels().entrySet()) {
            ResourceLocation id = entry.getKey().id();

            // AQUI ESTAVA O ERRO! O bloco no mundo é apenas "heavy_tank"
            if (id.getNamespace().equals(PhAndCoMod.MOD_ID) && id.getPath().equals("heavy_tank")) {

                BakedModel original = entry.getValue();
                event.getModels().put(entry.getKey(), new HeavyTankModel(original));
            }
        }
    }
}