package com.bruno.ph_and_co;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModFluids {
    // 1. Barramentos de Registro
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, PhAndCoMod.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, PhAndCoMod.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PhAndCoMod.MODID);

    // Usaremos o registro de itens existente na classe ModItems para os baldes

    // =========================================================================
    // 2. REGISTRO DOS FLUIDOS (Como era no KubeJS)
    // =========================================================================

    public static final FluidRegistryObject MOLTEN_GLASS = registerFluid("molten_glass",
            ResourceLocation.withDefaultNamespace("block/molten_glass_still"),
            ResourceLocation.withDefaultNamespace("block/molten_glass_flow"));

    public static final FluidRegistryObject SULFURIC_ACID = registerFluid("sulfuric_acid",
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MODID, "block/sulfuric_acid_still"),
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MODID, "block/sulfuric_acid_flow"));

    public static final FluidRegistryObject HYDROCHLORIC_ACID = registerFluid("hydrochloric_acid",
            ResourceLocation.fromNamespaceAndPath("block/hydrochloric_acid_still"),
            ResourceLocation.fromNamespaceAndPath("block/hydrochloric_acid_flow"));

    // =========================================================================
    // 3. O "HELPER" QUE FAZ A MÁGICA ACONTECER
    // =========================================================================

    // Classe contêiner para guardar as referências geradas
    public record FluidRegistryObject(Supplier<FluidType> type, Supplier<Fluid> source, Supplier<Fluid> flowing, Supplier<LiquidBlock> block, Supplier<Item> bucket) {}

    private static FluidRegistryObject registerFluid(String name, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        // A. Registra o Tipo de Fluido (Comportamento e Texturas)
        var type = FLUID_TYPES.register(name, () -> new FluidType(FluidType.Properties.create()) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override public ResourceLocation getStillTexture() { return stillTexture; }
                    @Override public ResourceLocation getFlowingTexture() { return flowingTexture; }
                });
            }
        });

        // B. Propriedades base (Conecta tudo e evita o problema do ovo e da galinha)
        Supplier<BaseFlowingFluid.Properties> properties = () -> new BaseFlowingFluid.Properties(
                type,
                // Precisamos criar as instâncias e referenciá-las logo abaixo
                null, null
        );

        // C. Registra a Fonte e o Fluxo
        var source = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(properties.get()));
        var flowing = FLUIDS.register(name + "_flowing", () -> new BaseFlowingFluid.Flowing(properties.get()));

        // D. Registra o Bloco do Fluido
        var block = BLOCKS.registerBlock(name + "_block", LiquidBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable());

        // E. Registra o Balde no ModItems
        var bucket = ModItems.ITEMS.register(name + "_bucket", () -> new BucketItem(source.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

        // Atualiza as propriedades com as referências reais
        properties.get().block(block).bucket(bucket);

        return new FluidRegistryObject(type, source, flowing, block, bucket);
    }
}