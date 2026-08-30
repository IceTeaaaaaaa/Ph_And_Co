package com.bruno.ph_and_co;

import com.bruno.ph_and_co.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, PhAndCoMod.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, PhAndCoMod.MOD_ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PhAndCoMod.MOD_ID);


    // =========================================================================
    // FLUID
    // =========================================================================

    public static final FluidRegistryObject MOLTEN_GLASS = registerFluid("molten_glass",
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/molten_glass_still"),
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/molten_glass_flow"));

    public static final FluidRegistryObject SULFURIC_ACID = registerFluid("sulfuric_acid",
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_still"),
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/sulfuric_acid_flow"));

    public static final FluidRegistryObject HYDROCHLORIC_ACID = registerFluid("hydrochloric_acid",
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/hydrocloric_acid_still"),
            ResourceLocation.fromNamespaceAndPath(PhAndCoMod.MOD_ID, "block/hydrocloric_acid_flow"));

    // =========================================================================
    // HELPER
    // =========================================================================


    public record FluidRegistryObject(Supplier<FluidType> type, Supplier<Fluid> source, Supplier<Fluid> flowing, Supplier<LiquidBlock> block, Supplier<Item> bucket) {}

    public static FluidRegistryObject registerFluid(String name, ResourceLocation resourceLocation, ResourceLocation location) {


        Supplier<FluidType> type = FLUID_TYPES.register(name, () -> new FluidType(FluidType.Properties.create()));

        final BaseFlowingFluid.Properties[] properties = new BaseFlowingFluid.Properties[1];

        Supplier<Fluid> source = FLUIDS.register(name, () -> new BaseFlowingFluid.Source(properties[0]));
        Supplier<Fluid> flowing = FLUIDS.register(name + "_flowing", () -> new BaseFlowingFluid.Flowing(properties[0]));

        Supplier<LiquidBlock> block = BLOCKS.registerBlock(name + "_block",
                p -> new LiquidBlock((FlowingFluid) source.get(), p),
                BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable());

        Supplier<Item> bucket = ModItems.ITEMS.registerItem(name + "_bucket",
                p -> new BucketItem(source.get(), p.craftRemainder(Items.BUCKET).stacksTo(1)));

        properties[0] = new BaseFlowingFluid.Properties(type, source, flowing)
                .block(block)
                .bucket(bucket);

        return new FluidRegistryObject(type, source, flowing, block, bucket);
    }
}