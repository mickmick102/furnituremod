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
    private int numRows;

    public ContainerCase(IInventory inventory, TileEntityCase tileEntity) {

        this.tileEntity = tileEntity;
        this.inventory = inventory;
        numRows = tileEntity.getSizeInventory() / 6;
        // par2IInventory.openChest();
        int i = (numRows - 4) * 18;

        for (int j = 0; j < numRows; j++)
            for (int i1 = 0; i1 < 6; i1++)
                this.addSlotToContainer(new Slot(tileEntity, i1 + j * 6, 35 + i1 * 18, 20 + j * 18));

        for (int k = 0; k < 3; k++)
            for (int j1 = 0; j1 < 9; j1++)
                this.addSlotToContainer(new Slot(inventory, j1 + k * 9 + 9, 8 + j1 * 18, 103 + k * 18 + i));

        for (int l = 0; l < 9; l++)
            this.addSlotToContainer(new Slot(inventory, l, 8 + l * 18, 161 + i));

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        // TODO Auto-generated method stub
        return tileEntity.isUsableByPlayer(playerIn);
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.inventory;
    }
}
