package com.lordpine.apcc;

import net.minecraftforge.client.MinecraftForgeClient;

import com.lordpine.apcc.items.APcCItems;

import fox.spiteful.avaritia.render.FancyHaloRenderer;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    @Override
    public void loadSpecialRendering() {
        MinecraftForgeClient.registerItemRenderer(APcCItems.luminescent_pearl, new FancyHaloRenderer());
    }
}
