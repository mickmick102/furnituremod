package com.mickmick102.furnituremod.init;

import com.mickmick102.furnituremod.objects.blocks.BlockBarre;
import com.mickmick102.furnituremod.objects.blocks.BlockBench;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    //BLOCKS
    public static final Block BENCH_BLOCK = new BlockBench("bench_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
    public static final Block BARRE_BLOCK = new BlockBarre("barre_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
}
