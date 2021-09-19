package com.mickmick102.furnituremod.objects.tileEntity;

import com.mickmick102.furnituremod.objects.container.ContainerCase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;

public class TileEntityCase extends TileEntityLockableLoot implements IInventory {

    private NonNullList<ItemStack> placardContents = NonNullList.<ItemStack>withSize(25, ItemStack.EMPTY);
    private boolean hasBeenCleared;

    @Override
    protected NonNullList<ItemStack> getItems() {
        return placardContents;
    }

    @Override
    public int getSizeInventory() {
        return placardContents.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 50;
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
        this.fillWithLoot(entityPlayer);
        return new ContainerCase(inventoryPlayer, this);
    }

    @Override
    public String getGuiID() {
        return "furnituremod:gui_case";
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.case.name";
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return placardContents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        this.fillWithLoot((EntityPlayer) null);
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.placardContents, index, count);

        if (itemstack != null) {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        this.fillWithLoot((EntityPlayer) null);
        return ItemStackHelper.getAndRemove(this.placardContents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.fillWithLoot((EntityPlayer) null);
        this.placardContents.set(index, stack);

        if (stack != null && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();

    }

    public void readFromNBT(NBTTagCompound p_readFromNBT_1_) {
        super.readFromNBT(p_readFromNBT_1_);
        this.loadFromNbt(p_readFromNBT_1_);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound p_writeToNBT_1_) {
        super.writeToNBT(p_writeToNBT_1_);
        return this.saveToNbt(p_writeToNBT_1_);
    }

    public void loadFromNbt(NBTTagCompound p_loadFromNbt_1_) {
        this.placardContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(p_loadFromNbt_1_) && p_loadFromNbt_1_.hasKey("Items", 9)) {
            ItemStackHelper.loadAllItems(p_loadFromNbt_1_, this.placardContents);
        }

        if (p_loadFromNbt_1_.hasKey("CustomName", 8)) {
            this.customName = p_loadFromNbt_1_.getString("CustomName");
        }

    }

    public NBTTagCompound saveToNbt(NBTTagCompound p_saveToNbt_1_) {
        if (!this.checkLootAndWrite(p_saveToNbt_1_)) {
            ItemStackHelper.saveAllItems(p_saveToNbt_1_, this.placardContents, false);
        }

        if (this.hasCustomName()) {
            p_saveToNbt_1_.setString("CustomName", this.customName);
        }

        if (!p_saveToNbt_1_.hasKey("Lock") && this.isLocked()) {
            this.getLockCode().toNBT(p_saveToNbt_1_);
        }

        return p_saveToNbt_1_;
    }

    /*@Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = super.getUpdateTag();

        if (!this.checkLootAndWrite(compound)) {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.placardContents.size(); ++i) {
                if (!this.placardContents.get(i).isEmpty()) {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    nbttagcompound.setByte("Slot", (byte) i);
                    this.placardContents.get(i).writeToNBT(nbttagcompound);
                    nbttaglist.appendTag(nbttagcompound);
                }
            }

            compound.setTag("Items", nbttaglist);
        }

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }

        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        super.handleUpdateTag(compound);
        this.placardContents = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }

        if (!this.checkLootAndRead(compound)) {
            NBTTagList nbttaglist = compound.getTagList("Items", 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                if (j >= 0 && j < this.placardContents.size()) {
                    this.placardContents.set(j, new ItemStack(nbttagcompound));
                }
            }
        }
    }*/

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if (net.getDirection() == EnumPacketDirection.CLIENTBOUND) {
            readFromNBT(pkt.getNbtCompound());
        }
    }

    public boolean isCleared() {
        return this.hasBeenCleared;
    }

    public void clear() {
        this.hasBeenCleared = true;
        super.clear();
    }

    public boolean shouldDrop() {
        return !this.isEmpty() || this.hasCustomName() || this.lootTable != null;
    }

}
