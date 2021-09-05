package com.mickmick102.furnituremod.objects.gui;

import com.mickmick102.furnituremod.objects.container.ContainerCase;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiCase extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("furnituremod", "textures/gui/furniture.png");
    public TileEntityCase tilentity;

    public GuiCase(InventoryPlayer inventorySlotsIn, TileEntityCase tile) {
        super(new ContainerCase(inventorySlotsIn, tile));
        tilentity = tile;

        xSize = 512;
        ySize = 512;
    }

    // draw the background for the GUI - rendered first
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        // Bind the image texture of our custom container
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        // Draw the image
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    // draw the foreground for the GUI - rendered after the slots, but before the dragged items and tooltips
    // renders relative to the top left corner of the background
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;
        fontRenderer.drawString(tilentity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());
    }
}
