package com.lordpine.apcc.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class APcCItems {
    public static Item luminescent_pearl;

    public static void preInit() {
        luminescent_pearl = new ItemLuminescentPearl();
        GameRegistry.registerItem(luminescent_pearl, "luminescent_pearl");
    }
}
