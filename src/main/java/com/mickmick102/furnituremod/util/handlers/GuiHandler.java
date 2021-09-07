package com.mickmick102.furnituremod.util.handlers;

import com.mickmick102.furnituremod.objects.container.ContainerCase;
import com.mickmick102.furnituremod.objects.gui.GuiCase;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    private static final int GUIID_MBE_30 = 30;
    public static int getGuiID() {return GUIID_MBE_30;}

    // Gets the server side element for the given gui id- this should return a container
    @Override
    @Nullable
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityCase) {
            TileEntityCase tileEntityCase = (TileEntityCase) tileEntity;
            return new ContainerCase(player.inventory, tileEntityCase);
        }
        return null;
    }

    // Gets the client side element for the given gui id- this should return a gui
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID != getGuiID()) {
            System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
        }

        BlockPos xyz = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(xyz);
        if (tileEntity instanceof TileEntityCase) {
            TileEntityCase tileEntityCase = (TileEntityCase) tileEntity;
            return new GuiCase(tileEntityCase, player.inventory);
        }
        return null;
    }
}
