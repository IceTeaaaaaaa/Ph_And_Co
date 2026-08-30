package com.bruno.ph_and_co;

import com.bruno.ph_and_co.item.ModItems;
import com.bruno.ph_and_co.menu.ModMenus;
import com.bruno.ph_and_co.recipe.ModRecipes;
import com.bruno.ph_and_co.screen.ClientSetup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredRegister;
 import net.neoforged.neoforge.common.NeoForge;
 import com.bruno.ph_and_co.menu.MortarMenu;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PhAndCoMod.MOD_ID)
public class PhAndCoMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ph_and_co";

//    public static final Logger LOGGER = LogUtils.getLogger();




    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public PhAndCoMod(IEventBus modEventBus) {

        modEventBus.addListener(com.bruno.ph_and_co.datagen.DataGenerators::gatherData);

        ModItems.ITEMS.register(modEventBus);

        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.BLOCKS.register(modEventBus);

        ModRecipes.SERIALIZERS.register(modEventBus);
        ModRecipes.TYPES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);


        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientSetup::onRegisterMenus);
        }

        NeoForge.EVENT_BUS.addListener(MortarMenu::onPlayerTick);


        CREATIVE_TABS.register("tab_principal", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + MOD_ID))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> ModItems.HEATED_IRON_PLATE.get().getDefaultInstance())
                .displayItems((parameters, output) -> {

                    output.accept(ModItems.HEATED_IRON_PLATE.get());
                    output.accept(ModItems.HEATED_GOLD_PLATE.get());
                    output.accept(ModItems.HEATED_STEEL_PLATE.get());
                    output.accept(ModItems.HEATED_COPPER_PLATE.get());
                    output.accept(ModItems.HEATED_BRASS_PLATE.get());
                    output.accept(ModItems.HEATED_GOLD_INGOT.get());
                    output.accept(ModItems.HEATED_BRASS_INGOT.get());
                    output.accept(ModItems.GOLD_PLATE.get());
                    output.accept(ModItems.BRASS_PLATE.get());

                    output.accept(ModItems.INCOMPLETE_NAILS.get());
                    output.accept(ModItems.INCOMPLETE_NAILS_STEEL.get());
                    output.accept(ModItems.INCOMPLETE_REINFORCED_STEEL_PLATE.get());
                    output.accept(ModItems.REINFORCED_STEEL_PLATE.get());

                    output.accept(ModItems.SILICA.get());
                    output.accept(ModItems.SALT.get());
                    output.accept(ModItems.CALCIUM.get());
                    output.accept(ModItems.MAGNESIUM.get());
                    output.accept(ModItems.SULFUR.get());
                    output.accept(ModItems.CRUSHED_ANDESITE.get());

                    output.accept(ModItems.MORTAR.get());
                    output.accept(ModItems.MORTAR_BASE.get());
                    output.accept(ModItems.PESTLE.get());

                    output.accept(ModItems.MOLTEN_GLASS_BLOB.get());
                    output.accept(ModItems.HEATED_GLASS_PANEL.get());
                    output.accept(ModItems.REINFORCED_GLASS.get());
                    output.accept(ModItems.SHATTERED_GLASS.get());
                    output.accept(ModItems.GLASS_POWDER.get());
                    output.accept(ModItems.LOOSE_SAND.get());

                    output.accept(ModItems.NAIL.get());
                    output.accept(ModItems.NAIL_STEEL.get());
                    output.accept(ModItems.BRASS_SCREW.get());
                    output.accept(ModItems.RUDIMENTARY_COMPONENT.get());
                    output.accept(ModItems.ESSENTIAL_COMPONENT.get());
                    output.accept(ModItems.SOPHISTICATED_COMPONENT.get());
                    output.accept(ModItems.FLAWLESS_COMPONENT.get());


                    output.accept(ModFluids.MOLTEN_GLASS.bucket().get());
                    output.accept(ModFluids.SULFURIC_ACID.bucket().get());
                    output.accept(ModFluids.HYDROCHLORIC_ACID.bucket().get());


                }).build());

        CREATIVE_TABS.register(modEventBus);
    }
}
