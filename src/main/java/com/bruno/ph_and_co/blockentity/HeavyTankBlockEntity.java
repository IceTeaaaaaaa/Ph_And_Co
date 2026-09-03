package com.bruno.ph_and_co.blockentity;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.api.connectivity.IMultiBlockEntityContainer;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidTank;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.List;

public class HeavyTankBlockEntity extends BlockEntity implements IHaveGoggleInformation, IMultiBlockEntityContainer.Fluid {

    public static final ModelProperty<TankRenderData> RENDER_DATA = new ModelProperty<>();
    public record TankRenderData(boolean up, boolean down, boolean north, boolean south, boolean west, boolean east) {}

    // Variáveis exigidas pelo Create
    protected BlockPos controllerPos;
    protected int mbWidth = 1;
    protected int mbHeight = 1;
    protected boolean updateConnectivity;

    // O Tanque (A capacidade será mudada pelo Create dinamicamente)
    public final FluidTank waterTank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    public FluidTankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEAVY_TANK_BE.get(), pos, state);
    }

    // =====================================================================
    // MOTOR MULTIBLOCK DO CREATE (IMultiBlockEntityContainer)
    // =====================================================================
    @Override
    public BlockPos getController() {
        return controllerPos == null ? worldPosition : controllerPos;
    }

    @Override
    public void setController(BlockPos controller) {
        if (level != null && !level.isClientSide && !controller.equals(this.controllerPos)) {
            this.controllerPos = controller;
            setChanged();
        }
    }

    @Override
    public void removeController(boolean keepContents) {
        if (level != null && !level.isClientSide) {
            this.controllerPos = null;
            this.mbWidth = 1;
            this.mbHeight = 1;
            setTankSize(0, 1); // Reseta pra 1 bloco
            setChanged();
        }
    }

    @Override
    public boolean isController() {
        return getController().equals(worldPosition);
    }

    @Override
    public BlockEntity getControllerBE() {
        return isController() ? this : level.getBlockEntity(getController());
    }

    @Override
    public void notifyMultiUpdated() {
        // Disparado pelo Create sempre que o multiblock cresce ou diminui
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public Direction.Axis getMainConnectionAxis() { return Direction.Axis.Y; }
    @Override
    public int getMaxWidth() { return 3; }
    @Override
    public int getMaxLength(Direction.Axis axis, int width) { return axis == Direction.Axis.Y ? 6 : 3; }

    @Override
    public int getWidth() { return mbWidth; }
    @Override
    public void setWidth(int width) { this.mbWidth = width; }
    @Override
    public int getHeight() { return mbHeight; }
    @Override
    public void setHeight(int height) { this.mbHeight = height; }

    @Override
    public void preventConnectivityUpdate() { this.updateConnectivity = false; }

    // =====================================================================
    // MOTOR DE FLUIDO DO CREATE (IMultiBlockEntityContainer.Fluid)
    // =====================================================================
    @Override
    public boolean hasTank() { return true; }

    @Override
    public IFluidTank getTank(int tank) { return waterTank; }

    @Override
    public FluidStack getFluid(int tank) { return waterTank.getFluid(); }

    @Override
    public int getTankSize(int tank) { return waterTank.getCapacity(); }

    @Override
    public void setTankSize(int tank, int blocks) {
        // O Create diz quantos blocos formam o tanque. Nós multiplicamos!
        waterTank.setCapacity(blocks * 4000);
    }

    // =====================================================================
    // RENDERIZAÇÃO INTELIGENTE (MODEL DATA)
    // =====================================================================
    @Override
    public ModelData getModelData() {
        if (level == null) return ModelData.EMPTY;
        // Delega para o Create perguntar se o vizinho tem o mesmo controlador que eu
        return ModelData.builder()
                .with(RENDER_DATA, new TankRenderData(
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.above()),
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.below()),
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.north()),
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.south()),
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.west()),
                        ConnectivityHandler.isConnected(level, worldPosition, worldPosition.east())
                )).build();
    }

    // =====================================================================
    // SINCRONIZAÇÃO NBT PADRÃO NEOFORGE (Para a tela saber que o bloco mudou)
    // =====================================================================
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("MBWidth", mbWidth);
        tag.putInt("MBHeight", mbHeight);
        if (controllerPos != null) {
            tag.put("Controller", NbtUtils.writeBlockPos(controllerPos));
        }
        waterTank.writeToNBT(registries, tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        mbWidth = tag.getInt("MBWidth");
        mbHeight = tag.getInt("MBHeight");

        // Aumenta a capacidade no Cliente antes de ler a água
        setTankSize(0, mbWidth * mbWidth * mbHeight);

        if (tag.contains("Controller")) {
            controllerPos = NbtUtils.readBlockPos(tag, "Controller").orElse(null);
        } else {
            controllerPos = null;
        }
        waterTank.readFromNBT(registries, tag);

        // Força o modelo CTM a redesenhar no instante em que os dados chegam
        if (level != null && level.isClientSide) {
            requestModelDataUpdate();
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    // =====================================================================
    // GOGGLES DO CREATE
    // =====================================================================
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Redireciona pro mestre, pois só ele tem os dados completos do Multiblock
        FluidTankBlockEntity controller = (FluidTankBlockEntity) getControllerBE();
        if (controller == null) controller = this;

        // Formata os textos bonitos aqui usando controller.mbWidth, controller.waterTank.getFluidAmount(), etc.
        tooltip.add(Component.literal("Heavy Tank - " + controller.mbWidth + "x" + controller.mbWidth + "x" + controller.mbHeight));
        return true;
    }
}