package com.mickmick102.furnituremod.objects.blocks;

import com.mickmick102.furnituremod.FurnitureMod;
import com.mickmick102.furnituremod.init.BlockInit;
import com.mickmick102.furnituremod.init.ItemInit;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import com.mickmick102.furnituremod.util.handlers.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BlockCase extends BlockContainer {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0,0.0,0.0,1,0.5,1.0);

    public BlockCase(String name, Material material, float hardness, float resistance, int miningLevel, String tool) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        setHardness(hardness);
        setResistance(resistance);
        setHarvestLevel(tool, miningLevel);
        setCreativeTab(CreativeTabs.MATERIALS);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {

        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        TileEntityCase tileEntity = new TileEntityCase();
        tileEntity.setCustomName("case");
        return tileEntity;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        // Uses the gui handler registered to your mod to open the gui for the given gui id
        // open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
        if (worldIn.isRemote) return true;

        playerIn.openGui(FurnitureMod.INSTANCE, GuiHandler.getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public void onBlockHarvested(World world, BlockPos blockPos, IBlockState state, EntityPlayer player) {
        if (world.getTileEntity(blockPos) instanceof TileEntityCase) {
            TileEntityCase tileEntity = (TileEntityCase) world.getTileEntity(blockPos);
            //tileEntity.setDestroyedByCreativePlayer(p_onBlockHarvested_4_.capabilities.isCreativeMode);
            tileEntity.fillWithLoot(player);
        }
    }

    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float i, int j) {
    }

    public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState state, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityCase) {
                ((TileEntityCase)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }

    }

    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityCase) {
            TileEntityCase tileEntityCase = (TileEntityCase)tileEntity;
            if (!tileEntityCase.isCleared() && tileEntityCase.shouldDrop()) {
                ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this));
                NBTTagCompound nbtTagCompound = new NBTTagCompound();
                NBTTagCompound nbtTagCompound1 = new NBTTagCompound();
                nbtTagCompound.setTag("BlockEntityTag", ((TileEntityCase)tileEntity).saveToNbt(nbtTagCompound1));
                itemStack.setTagCompound(nbtTagCompound);
                if (tileEntityCase.hasCustomName()) {
                    itemStack.setStackDisplayName(tileEntityCase.getName());
                    tileEntityCase.setCustomName("");
                }

                spawnAsEntity(world, pos, itemStack);
            }
        }

        super.breakBlock(world, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> names, ITooltipFlag tooltipFlag) {
        super.addInformation(stack, world, names, tooltipFlag);
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey("LootTable", 8)) {
                names.add("???????");
            }

            if (blockEntityTag.hasKey("Items", 9)) {
                NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(blockEntityTag, itemStacks);
                int totalStack = 0;
                int stackCount = 0;
                Iterator itemStackIterator = itemStacks.iterator();

                while(itemStackIterator.hasNext()) {
                    ItemStack itemStack = (ItemStack)itemStackIterator.next();
                    if (!itemStack.isEmpty()) {
                        ++stackCount;
                        if (totalStack <= 4) {
                            ++totalStack;
                            names.add(String.format("%s x%d", itemStack.getDisplayName(), itemStack.getCount()));
                        }
                    }
                }

                if (stackCount - totalStack > 0) {
                    names.add(String.format(TextFormatting.ITALIC + I18n.translateToLocal("container.shulkerBox.more"), stackCount - totalStack));
                }
            }
        }

    }

    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        ItemStack itemStack = super.getItem(world, pos, state);
        TileEntityCase tileEntity = (TileEntityCase) world.getTileEntity(pos);
        NBTTagCompound nbtTagCompound = tileEntity.saveToNbt(new NBTTagCompound());
        if (!nbtTagCompound.hasNoTags()) {
            itemStack.setTagInfo("BlockEntityTag", nbtTagCompound);
        }

        return itemStack;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

}
