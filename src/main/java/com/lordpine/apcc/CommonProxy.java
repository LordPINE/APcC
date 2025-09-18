package com.lordpine.apcc;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.lordpine.apcc.blocks.APcCBlocks;
import com.lordpine.apcc.items.APcCItems;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import twilightforest.item.TFItems;
import vazkii.botania.api.recipe.IFlowerComponent;
import vazkii.botania.common.CustomBotaniaAPI;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        APcCBlocks.preInit();
        APcCItems.preInit();

        APolyChromaticCore.LOG.info("I am " + Tags.MODNAME + " at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance()
            .bus()
            .register(new APcCEventHandler());

        CustomBotaniaAPI.extraFlowerComponents.put(TFItems.steeleafIngot, new IFlowerComponent() {

            public boolean canFit(ItemStack stack, IInventory apothecary) {
                return true;
            }

            public int getParticleColor(ItemStack stack) {
                return 0x06441D;
            }
        });
        APolyChromaticCore.LOG.info("Done initializing APcC!");
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    public void loadSpecialRendering() {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
