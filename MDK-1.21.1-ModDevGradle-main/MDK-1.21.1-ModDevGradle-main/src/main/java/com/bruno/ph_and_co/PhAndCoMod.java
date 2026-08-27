package com.bruno.ph_and_co;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PhAndCoMod.MOD_ID)
public class PhAndCoMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ph_and_co";
//    public static final Logger LOGGER = LogUtils.getLogger();




    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public PhAndCoMod(IEventBus modEventBus) {


        ModItems.ITEMS.register(modEventBus);

        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.BLOCKS.register(modEventBus);


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
                    output.accept(ModItems.COMPACTED_SILICA.get());
                    output.accept(ModItems.SULFUR.get());

                    output.accept(ModItems.GLASS_POWDER.get());
                    output.accept(ModItems.HEATED_GLASS_PANEL.get());
                    output.accept(ModItems.REINFORCED_GLASS.get());
                    output.accept(ModItems.SHATTERED_GLASS.get());
                    output.accept(ModItems.LOOSE_SAND.get());

                    output.accept(ModFluids.MOLTEN_GLASS.bucket().get());
                    output.accept(ModFluids.SULFURIC_ACID.bucket().get());
                    output.accept(ModFluids.HYDROCHLORIC_ACID.bucket().get());


                }).build());

        CREATIVE_TABS.register(modEventBus);
    }
}
