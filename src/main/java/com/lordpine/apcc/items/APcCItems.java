package com.lordpine.apcc.items;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

public class APcCItems {

    public static final String[] GENERIC_ITEM_NAMES = {"chromatic_essence", "chromatic_distillate", "refined_corruption"};
    public static Item luminescent_pearl;
    public static Item material;

    public static void preInit() {
        luminescent_pearl = new ItemLuminescentPearl();
        GameRegistry.registerItem(luminescent_pearl, "luminescent_pearl");

        material = new ItemGeneric("material");
        GameRegistry.registerItem(material, "material");
    }
}
