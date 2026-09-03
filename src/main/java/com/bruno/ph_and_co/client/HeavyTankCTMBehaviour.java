//package com.bruno.ph_and_co.client;
//
//import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
//import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.BlockAndTintGetter;
//import net.minecraft.world.level.block.state.BlockState;
//
//public class HeavyTankCTMBehaviour extends ConnectedTextureBehaviour {
//
//    @Override
//    public SpriteShiftEntry getSpriteShift(BlockState state, Direction direction) {
//        if (direction.getAxis() == Direction.Axis.Y) {
//            return HeavyTankSpriteShifts.TOP;
//        }
//        return HeavyTankSpriteShifts.SIDE;
//    }
//
//    @Override
//    protected boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos, BlockPos otherPos, Direction face) {
//        // Conecta perfeitamente com qualquer outro Heavy Tank adjacente!
//        return state.getBlock() == other.getBlock();
//    }
//}