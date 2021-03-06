package com.mickmick102.furnituremod.util.handlers;

import com.mickmick102.furnituremod.FurnitureMod;
import com.mickmick102.furnituremod.init.BlockInit;
import com.mickmick102.furnituremod.init.ItemInit;
import com.mickmick102.furnituremod.objects.tileEntity.TileEntityCase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item: ItemInit.ITEMS) {
            FurnitureMod.proxy.registerItemRenderer(item, 0, "inventory");
        }

        for (Block block: BlockInit.BLOCKS) {
            FurnitureMod.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityCase.class, "furnituremod:tile_inventory_case");

        NetworkRegistry.INSTANCE.registerGuiHandler(FurnitureMod.INSTANCE, GuiHandlerRegistry.getInstance());
        GuiHandlerRegistry.getInstance().registerGuiHandler(new GuiHandler(), GuiHandler.getGuiID());
    }

    public static void preInitRegistries(FMLPreInitializationEvent event) {
    }

    public static void initRegistries(FMLInitializationEvent event) {
        FurnitureMod.proxy.render();
    }

    public static void postInitRegistries(FMLPostInitializationEvent event) {

    }

    public static void serverInit(FMLServerStartingEvent event) {

    }
}
