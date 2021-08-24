package com.mickmick102.furnituremod.objects.blocks;

import com.mickmick102.furnituremod.util.CollisionHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBarre extends BlockFurniture {

    private static final AxisAlignedBB SIDE_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH,0.0,-0.75,0.0,1,0.75,0.5);
    private static final AxisAlignedBB SIDE_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST,0.0,-0.75,0.0,1,0.75,0.5);
    private static final AxisAlignedBB SIDE_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH,0.0,-0.75,0.0,1,0.75,0.5);
    private static final AxisAlignedBB SIDE_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST,0.0,-0.75,0.0,1,0.75,0.5);

    public BlockBarre(String name, Material material, float hardness, float resistance, int miningLevel, String tool) {
        super(name, material, hardness, resistance, miningLevel, tool);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing face = state.getValue(FACING);
        AxisAlignedBB axisAlignedBB = SIDE_NORTH;
        switch (face) {
            case NORTH:
                axisAlignedBB = SIDE_WEST;
                break;
            case EAST:
                axisAlignedBB = SIDE_NORTH;
                break;
            case SOUTH:
                axisAlignedBB = SIDE_EAST;
                break;
            case WEST:
                axisAlignedBB = SIDE_SOUTH;
                break;
        }
        return axisAlignedBB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing face = blockState.getValue(FACING);
        AxisAlignedBB axisAlignedBB = SIDE_NORTH;
        switch (face) {
            case NORTH:
                axisAlignedBB = SIDE_WEST;
                break;
            case EAST:
                axisAlignedBB = SIDE_NORTH;
                break;
            case SOUTH:
                axisAlignedBB = SIDE_EAST;
                break;
            case WEST:
                axisAlignedBB = SIDE_SOUTH;
                break;
        }
        return axisAlignedBB;
    }
}
