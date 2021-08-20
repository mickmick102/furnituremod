package com.mickmick102.furnituremod.init;

import com.mickmick102.furnituremod.objects.blocks.FurnitureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    //ITEMS
    public static final Block BENCH_BLOCK = new FurnitureBlock("bench_block", Material.WOOD, 10.0f, 10.0f, 2, "axe");
}
