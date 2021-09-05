package com.mickmick102.furnituremod.init;

import com.mickmick102.furnituremod.objects.blocks.BlockBarre;
import com.mickmick102.furnituremod.objects.blocks.BlockBench;
import com.mickmick102.furnituremod.objects.blocks.BlockCase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    //BLOCKS
    public static final Block BENCH_BLOCK = new BlockBench("bench_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");

    public static final Block CASE_BLOCK = new BlockCase("case_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    // Barre block
    public static final Block OAK_BARRE_BLOCK = new BlockBarre("oak_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block SPRUCE_BARRE_BLOCK = new BlockBarre("spruce_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block BIRCH_BARRE_BLOCK = new BlockBarre("birch_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block JUNGLE_BARRE_BLOCK = new BlockBarre("jungle_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block ACACIA_BARRE_BLOCK = new BlockBarre("acacia_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block DARK_OAK_BARRE_BLOCK = new BlockBarre("dark_oak_barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
}
