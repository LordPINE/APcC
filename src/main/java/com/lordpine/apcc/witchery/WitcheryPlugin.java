package com.lordpine.apcc.witchery;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.Witchery;
import com.lordpine.apcc.items.APcCItems;

import Reika.ChromatiCraft.Auxiliary.ChromaStacks;
import Reika.ChromatiCraft.Registry.ChromaItems;
import alkalus.main.api.RecipeManager;
import alkalus.main.api.plugin.base.BasePluginWitchery;
import thaumcraft.common.config.ConfigBlocks;

public class WitcheryPlugin extends BasePluginWitchery {

    public WitcheryPlugin() {
        super(new LoadPhase[] { LoadPhase.INIT, LoadPhase.POSTINIT });
    }

    @Override
    public String getPluginName() {
        return "APcC_Plugin";
    }

    @Override
    public boolean preInit() {
        return false;
    }

    @Override
    public boolean init() {
        registerOvenRecipes();
        registerDistilleryRecipes();
        return true;
    }

    @Override
    public boolean postInit() {
        return true;
    }

    private void registerOvenRecipes() {
        // Greatwood and Silverwood -> Whiff of Magic
        RecipeManager.WitchesOven.addRecipe(
            new ItemStack(ConfigBlocks.blockCustomPlant, 1),
            null,
            1,
            Witchery.Items.GENERIC.itemAshWood.createStack(),
            1,
            Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(),
            1);
        RecipeManager.WitchesOven.addRecipe(
            new ItemStack(ConfigBlocks.blockCustomPlant, 1, 1),
            null,
            1,
            Witchery.Items.GENERIC.itemAshWood.createStack(),
            1,
            Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(),
            1);
    }

    private void registerDistilleryRecipes() {
        // Chromatic Distillate
        RecipeManager.Distillery.addRecipe(ChromaStacks.chromaDust, new ItemStack(APcCItems.material, 1,0), 3, new ItemStack(APcCItems.material, 1, 1), new ItemStack(APcCItems.material, 1, 1), Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(), null);

        // Refined Corruption
        RecipeManager.Distillery.addRecipe(Witchery.Items.GENERIC.itemMellifluousHunger.createStack(), ChromaStacks.voidmonsterEssence, 2, new ItemStack(APcCItems.material, 1, 2), new ItemStack(APcCItems.material, 1, 2), Witchery.Items.GENERIC.itemRefinedEvil.createStack(), null);
    }

}
