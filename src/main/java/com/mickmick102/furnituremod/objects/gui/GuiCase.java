package com.mickmick102.furnituremod.objects.gui;

import com.mickmick102.furnituremod.objects.container.ContainerCase;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCase extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation("furnituremod", "textures/gui/furniture.png");
    public TileEntityCase tilentity;
    private final int inventoryRows;
    private final IInventory upperPlacardInventory;
    private final IInventory lowerPlacardInventory;

    public GuiCase(TileEntityCase tileEntity, InventoryPlayer inventory) {
        super(new ContainerCase(inventory, tileEntity));
        this.tilentity = tileEntity;
        int i = 222;
        int j = 114;
        this.inventoryRows = inventory.getSizeInventory() / 6;
        this.upperPlacardInventory = inventory;
        this.lowerPlacardInventory = tileEntity;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String placardName = this.lowerPlacardInventory.getDisplayName().getUnformattedText();
        int xPos = this.fontRenderer.getStringWidth(placardName);
        this.fontRenderer.drawString(this.lowerPlacardInventory.getDisplayName().getUnformattedText(), xSize / 2 - xPos / 2, 6, 4210752);
        this.fontRenderer.drawString(this.upperPlacardInventory.getDisplayName().getUnformattedText(), 8,
                this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        //this.mc.getTextureManager().bindTexture(PLACARD);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i - 9, j - 10, 0, 0, this.xSize, this.inventoryRows * 18 + 30);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
