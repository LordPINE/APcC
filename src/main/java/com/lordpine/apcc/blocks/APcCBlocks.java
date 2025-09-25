package com.lordpine.apcc.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;

public class APcCBlocks {

    public static Block metalloak_log;
    public static Block metalloak_sapling;
    public static Block metalloak_leaves;
    public static Block blood_focus;

    public static void preInit() {
        metalloak_log = new BlockMetalloakLog().setBlockName("metalloak_log");
        GameRegistry.registerBlock(metalloak_log, metalloak_log.getUnlocalizedName());
        Blocks.fire.setFireInfo(metalloak_log, 3, 1);

        metalloak_leaves = new BlockMetalloakLeaves().setBlockName("metalloak_leaves");
        GameRegistry.registerBlock(metalloak_leaves, metalloak_leaves.getUnlocalizedName());
        Blocks.fire.setFireInfo(metalloak_leaves, 18, 12);

        metalloak_sapling = new BlockMetalloakSapling().setBlockName("metalloak_sapling");
        GameRegistry.registerBlock(metalloak_sapling, metalloak_sapling.getUnlocalizedName());

        blood_focus = new BlockBloodFocus().setBlockName("blood_focus");
        GameRegistry.registerBlock(blood_focus, blood_focus.getUnlocalizedName());
    }
}
