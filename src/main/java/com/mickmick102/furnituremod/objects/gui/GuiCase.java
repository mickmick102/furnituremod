package com.mickmick102.furnituremod.objects.gui;

import com.mickmick102.furnituremod.objects.container.ContainerCase;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;

public class GuiCase extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("furnituremod", "textures/gui/bookshelf.png");
    public TileEntityCase tilentity;

    public GuiCase(TileEntityCase tileEntity, InventoryPlayer inventory) {
        super(new ContainerCase(inventory, tileEntity));
        this.tilentity = tileEntity;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(tilentity.getName(), 60, 6, 0/*titleColor*/);
        fontRenderer.drawString(new TextComponentTranslation("container.inventory").getFormattedText(), 8, (ySize - 96) + 2, 0/*titleColor*/);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
