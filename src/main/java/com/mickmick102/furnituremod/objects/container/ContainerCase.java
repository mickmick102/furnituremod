package com.mickmick102.furnituremod.objects.container;

import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCase extends Container {
    private TileEntityCase tileEntity;
    private IInventory inventory;

    public ContainerCase(IInventory inventory, TileEntityCase tileEntity) {
        this.tileEntity = tileEntity;
        this.inventory = inventory;

        for(int i = 0; i < 2; i++)
            for(int l = 0; l < 8; l++)
                addSlotToContainer(new Slot(tileEntity, l + (i * 8), 17 + l * 18, 17 + i * 18));

        for(int j = 0; j < 3; j++)
            for(int i1 = 0; i1 < 9; i1++)
                addSlotToContainer(new Slot(inventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));

        for(int k = 0; k < 9; k++)
            addSlotToContainer(new Slot(inventory, k, 8 + k * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return tileEntity.isUsableByPlayer(par1EntityPlayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)inventorySlots.get(par1);

        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(par1 < 16) {
                if(!mergeItemStack(itemstack1, 16, 52, true))
                    return ItemStack.EMPTY;
            } else if(((Slot)inventorySlots.get(0)).isItemValid(itemstack1)) {
                if(!mergeItemStack(itemstack1, 0, 16, false))
                    return ItemStack.EMPTY;
            } else {
                return ItemStack.EMPTY;
            }

            if(itemstack1.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if(itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;

            slot.onSlotChanged();
        }

        return itemstack;
    }

    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

}
