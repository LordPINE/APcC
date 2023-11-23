package com.lordpine.apcc;

import net.minecraftforge.client.MinecraftForgeClient;

import com.lordpine.apcc.items.APcCItems;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import fox.spiteful.avaritia.render.FancyHaloRenderer;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    @Override
    public void loadSpecialRendering() {
        MinecraftForgeClient.registerItemRenderer(APcCItems.material, new FancyHaloRenderer());
        MinecraftForgeClient.registerItemRenderer(APcCItems.luminescent_pearl, new FancyHaloRenderer());
        MinecraftForgeClient.registerItemRenderer(APcCItems.ultimate_essence, new FancyHaloRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {}
}
