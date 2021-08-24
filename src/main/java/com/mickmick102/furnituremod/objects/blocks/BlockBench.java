package com.mickmick102.furnituremod.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBench extends BlockFurniture {

    private static final PropertyBool LEFT = PropertyBool.create("left");
    private static final PropertyBool RIGHT = PropertyBool.create("right");

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0,0.0,0.0,1,0.5,1.0);

    public BlockBench(String name, Material material, float hardness, float resistance, int miningLevel, String tool) {
        super(name, material, hardness, resistance, miningLevel, tool);

        this.setDefaultState(this.blockState.getBaseState().withProperty(LEFT, false));
        this.setDefaultState(this.blockState.getBaseState().withProperty(RIGHT, false));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState actualState = world.getBlockState(pos);
        switch (world.getBlockState(pos).getValue(FACING)) {
            case NORTH:
                actualState = actualState.withProperty(LEFT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.west())));
                actualState = actualState.withProperty(RIGHT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.east())));
                break;
            case SOUTH:
                actualState = actualState.withProperty(LEFT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.east())));
                actualState = actualState.withProperty(RIGHT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.west())));
                break;
            case WEST:
                actualState = actualState.withProperty(LEFT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.south())));
                actualState = actualState.withProperty(RIGHT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.north())));
                break;
            case EAST:
                actualState = actualState.withProperty(LEFT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.north())));
                actualState = actualState.withProperty(RIGHT,
                        isWithSameOrientation(actualState, world.getBlockState(pos.south())));
                break;
        }
        return actualState;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    private Boolean isWithSameOrientation(IBlockState currentBlockState, IBlockState otherBlockState) {
        return otherBlockState.getBlock() instanceof BlockBench && otherBlockState.getValue(BlockBench.FACING) == currentBlockState.getValue(BlockBench.FACING);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, LEFT, RIGHT});
    }
}
