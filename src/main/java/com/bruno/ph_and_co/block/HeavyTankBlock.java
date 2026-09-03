package com.bruno.ph_and_co.block;

import com.bruno.ph_and_co.blockentity.HeavyTankBlockEntity;
import com.bruno.ph_and_co.blockentity.ModBlockEntities;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HeavyTankBlock extends Block implements EntityBlock {

    public HeavyTankBlock(Properties properties) {
        super(properties);
    }

    // Cria a entidade atrelada a este bloco quando ele é colocado no mundo
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HeavyTankBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }

        return type == ModBlockEntities.HEAVY_TANK_BE.get() ?
                (lvl, pos, st, be) -> HeavyTankBlockEntity.tick(lvl, pos, st, (HeavyTankBlockEntity) be) : null;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getBlock() != oldState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof FluidTankBlockEntity be) {
                // O Create acha os vizinhos e monta a estrutura sozinho!
                ConnectivityHandler.formMulti(be);
            }
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof FluidTankBlockEntity be) {
                // O Create divide a água, refaz o tamanho e avisa o cliente sozinho!
                ConnectivityHandler.splitMulti(be);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
