package com.lordpine.apcc.items;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

public class APcCItems {

    public static final String[] GENERIC_ITEM_NAMES = { "chromatic_essence", "chromatic_distillate",
        "refined_corruption", "corrupted_infusion", "aeon_steel", "dreamstone", "ancient_will", "rune_elements",
        "rune_seasons", "rune_sin", "thaumium_gear_inert", "magical_base_alloy", "orb_frame_1", "orb_frame_2",
        "orb_frame_3", "orb_frame_4", "orb_frame_5", "orb_frame_6", "chaos_ichor", "creative_node"};
    public static final String[] ULTIMATE_ESSENCE_NAMES = { "ultimate_essence_kuro", "ultimate_essence_karmir",
        "ultimate_essence_kijani", "ultimate_essence_ruskea", "ultimate_essence_nila", "ultimate_essence_zambarau",
        "ultimate_essence_vadali", "ultimate_essence_argia", "ultimate_essence_ykri", "ultimate_essence_ruzova",
        "ultimate_essence_asveste", "ultimate_essence_kitrino", "ultimate_essence_galazio", "ultimate_essence_kurauri",
        "ultimate_essence_portokali", "ultimate_essence_tahara" };
    public static final int[] CRC_COLOURS = { 0xFF000000, 0xFFD81515, 0xFF628A10, 0xFF7F4A19, 0xFF2149EC, 0xFFB200FF,
        0xFF2095A0, 0xFFC0C0C0, 0xFF6B6B6B, 0xFFFF8CB2, 0xFF00FF21, 0xFFFFF000, 0xFF7CCEF8, 0xFFE300F8, 0xFFFE8800,
        0xFFFFFFFF };
    public static int[] INTERPOLATED_COLOURS = new int[64];
    public static Item luminescent_pearl;
    public static Item material;
    public static Item ultimate_essence;

    public static void preInit() {
        luminescent_pearl = new ItemLuminescentPearl();
        GameRegistry.registerItem(luminescent_pearl, "luminescent_pearl");

        material = new ItemGeneric("material");
        GameRegistry.registerItem(material, "material");

        ultimate_essence = new ItemUltimateEssence();
        GameRegistry.registerItem(ultimate_essence, "ultimate_essence");

        interpolateColours();
    }

    private static void interpolateColours() {
        for (int i = 0; i < CRC_COLOURS.length; i++) {
            INTERPOLATED_COLOURS[4 * i] = CRC_COLOURS[i];
            INTERPOLATED_COLOURS[4 * i
                + 1] = (int) (CRC_COLOURS[i] * 0.75f + CRC_COLOURS[(i + 1) % CRC_COLOURS.length] * 0.25f);
            INTERPOLATED_COLOURS[4 * i
                + 2] = (int) (CRC_COLOURS[i] * 0.5f + CRC_COLOURS[(i + 1) % CRC_COLOURS.length] * 0.5f);
            INTERPOLATED_COLOURS[4 * i
                + 3] = (int) (CRC_COLOURS[i] * 0.25f + CRC_COLOURS[(i + 1) % CRC_COLOURS.length] * 0.75f);
        }
    }
}
