package com.bruno.ph_and_co;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(value = PhAndCoMod.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = PhAndCoMod.MOD_ID, value = Dist.CLIENT)
public class PhAndCoModClient {

    private static final Logger LOGGER = LogUtils.getLogger();

    public PhAndCoModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        LOGGER.info(">>> Phleguethon & Cocytus CLIENT INITIALIZED <<<");

        LOGGER.info("Welcome, {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterClientExtensions(net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent event) {

        event.registerFluidType(new net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions() {
            @Override
            public net.minecraft.resources.ResourceLocation getStillTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/molten_glass_still");
            }
            @Override
            public net.minecraft.resources.ResourceLocation getFlowingTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/molten_glass_flow");
            }
        }, ModFluids.MOLTEN_GLASS.type().get());


        event.registerFluidType(new net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions() {
            @Override
            public net.minecraft.resources.ResourceLocation getStillTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_still");
            }
            @Override
            public net.minecraft.resources.ResourceLocation getFlowingTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_flow");
            }
        }, ModFluids.SULFURIC_ACID.type().get());


        event.registerFluidType(new net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions() {
            @Override
            public net.minecraft.resources.ResourceLocation getStillTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_still");
            }
            @Override
            public net.minecraft.resources.ResourceLocation getFlowingTexture() {
                return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_flow");
            }
        }, ModFluids.HYDROCHLORIC_ACID.type().get());
    }
}
