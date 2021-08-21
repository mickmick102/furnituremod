package com.mickmick102.furnituremod.objects.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBench extends BlockFurniture {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final PropertyBool LEFT = PropertyBool.create("left");
    private static final PropertyBool RIGHT = PropertyBool.create("right");

    public BlockBench(String name, Material material, float hardness, float resistance, int miningLevel, String tool) {
        super(name, material, hardness, resistance, miningLevel, tool);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IBlockState actualState = world.getBlockState(pos);
        switch (world.getBlockState(pos).getValue(FACING)) {
            case NORTH:
                actualState = actualState.withProperty(LEFT,
                        world.getBlockState(pos.west()).getBlock() instanceof BlockBench);
                actualState = actualState.withProperty(RIGHT,
                        world.getBlockState(pos.east()).getBlock() instanceof BlockBench);
                break;
            case SOUTH:
                actualState = actualState.withProperty(LEFT,
                        world.getBlockState(pos.east()).getBlock() instanceof BlockBench);
                actualState = actualState.withProperty(RIGHT,
                        world.getBlockState(pos.west()).getBlock() instanceof BlockBench);
                break;
            case WEST:
                actualState = actualState.withProperty(LEFT,
                        world.getBlockState(pos.south()).getBlock() instanceof BlockBench);
                actualState = actualState.withProperty(RIGHT,
                        world.getBlockState(pos.north()).getBlock() instanceof BlockBench);
                break;
            case EAST:
                actualState = actualState.withProperty(LEFT,
                        world.getBlockState(pos.north()).getBlock() instanceof BlockBench);
                actualState = actualState.withProperty(RIGHT,
                        world.getBlockState(pos.south()).getBlock() instanceof BlockBench);
                break;
        }
        return actualState;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, LEFT, RIGHT});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {

        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return EnumBlockRenderType.MODEL;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public BlockRenderLayer getBlockLayer() {
//        return BlockRenderLayer.CUTOUT;
//    }
//
//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
//        return BlockFaceShape.SOLID;
//    }
}
