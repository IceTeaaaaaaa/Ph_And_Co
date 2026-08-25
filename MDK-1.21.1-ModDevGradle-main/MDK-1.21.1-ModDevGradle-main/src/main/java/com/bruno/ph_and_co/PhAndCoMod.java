package com.bruno.ph_and_co;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PhAndCoMod.MODID)
public class PhAndCoMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ph_and_co";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    ModItems.ITEMS.register(modEventBus);

    // Registrando os novos barramentos de fluido
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluids.BLOCKS.register(modEventBus);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public PhAndCoMod(IEventBus modEventBus) {

        // Configurando e registrando a aba
        CREATIVE_TABS.register("tab_principal", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + MODID))
                .withTabsBefore(CreativeModeTabs.COMBAT) // Coloca sua aba antes da aba de Combate
                .icon(() -> ModItems.HEATED_IRON_PLATE.get().getDefaultInstance()) // Ícone temporário
                .displayItems((parameters, output) -> {

                    output.accept(ModItems.HEATED_IRON_PLATE.get());
                    output.accept(ModItems.HEATED_GOLD_PLATE.get());
                    output.accept(ModItems.HEATED_STEEL_PLATE.get());
                    output.accept(ModItems.HEATED_COOPER_PLATE.get());
                    output.accept(ModItems.HEATED_BRASS_PLATE.get());
                    output.accept(ModItems.HEATED_GOLD_INGOT.get());
                    output.accept(ModItems.HEATED_BRASS_INGOT.get());
                    output.accept(ModItems.GOLD_PLATE.get());
                    output.accept(ModItems.BRASS_PLATE.get());

                    output.accept(ModItems.INCOMPLETE_NAILS.get());
                    output.accept(ModItems.INCOMPLETE_STEEL_NAILS.get());
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
                    output.accept(ModFluids.HIDROCLORIDRIC_ACID.bucket().get());


                }).build());

        // Conectando o registro ao motor do NeoForge para ele carregar no jogo
        CREATIVE_TABS.register(modEventBus);
    }

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ExampleMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
