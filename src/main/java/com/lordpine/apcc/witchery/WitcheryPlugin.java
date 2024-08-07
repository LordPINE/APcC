package com.lordpine.apcc.witchery;

import java.util.EnumSet;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.ritual.Circle;
import com.emoniph.witchery.ritual.RiteRegistry;
import com.emoniph.witchery.ritual.RitualTraits;
import com.emoniph.witchery.ritual.SacrificeItem;
import com.emoniph.witchery.ritual.SacrificeMultiple;
import com.emoniph.witchery.ritual.SacrificePower;
import com.emoniph.witchery.ritual.rites.RiteInfusePlayers;
import com.lordpine.apcc.items.APcCItems;

import Reika.ChromatiCraft.Auxiliary.ChromaStacks;
import Reika.ChromatiCraft.Registry.ChromaBlocks;
import Reika.ChromatiCraft.Registry.ChromaItems;
import alkalus.main.api.RecipeManager;
import alkalus.main.api.plugin.base.BasePluginWitchery;
import thaumcraft.common.config.ConfigBlocks;

public class WitcheryPlugin extends BasePluginWitchery {

    Infusion infusionCorrupted;

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
        registerRites();
        registerCauldronRecipes();
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

        // Rainbow Sapling -> Chromatic Essence
        RecipeManager.WitchesOven.addRecipe(
            new ItemStack(ChromaBlocks.RAINBOWSAPLING.getItem(), 1),
            null,
            1,
            Witchery.Items.GENERIC.itemAshWood.createStack(),
            1,
            new ItemStack(APcCItems.material, 1, 0),
            1);
    }

    private void registerDistilleryRecipes() {
        // Chromatic Distillate
        RecipeManager.Distillery.addRecipe(
            ChromaStacks.chromaDust,
            new ItemStack(APcCItems.material, 1, 0),
            3,
            new ItemStack(APcCItems.material, 1, 1),
            new ItemStack(APcCItems.material, 1, 1),
            Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(),
            null);

        // Refined Corruption
        RecipeManager.Distillery.addRecipe(
            Witchery.Items.GENERIC.itemMellifluousHunger.createStack(),
            ChromaStacks.voidmonsterEssence,
            2,
            new ItemStack(APcCItems.material, 1, 2),
            new ItemStack(APcCItems.material, 1, 2),
            Witchery.Items.GENERIC.itemRefinedEvil.createStack(),
            null);
    }

    private void registerRites() {
        int lastID = RecipeManager.RitesAndRituals.getLastUsedRitualID();
        RecipeManager.RitesAndRituals.add(
            ++lastID,
            lastID + 100,
            new VoidMonsterRite(),
            new SacrificeMultiple(
                new SacrificeItem(
                    new ItemStack(APcCItems.material, 1, 2),
                    ChromaStacks.voidCoreHigh,
                    ChromaStacks.spaceDust),
                new SacrificePower(8000, 20)),
            EnumSet.noneOf(RitualTraits.class),
            new Circle[] { new Circle(0, 16, 0), new Circle(0, 28, 0), new Circle(0, 40, 0) });
        RiteRegistry.instance()
            .getRitual((byte) lastID)
            .setUnlocalizedName("apcc.rite.summon_voidmonster");
        this.infusionCorrupted = new CorruptedInfusion(5);
        RecipeManager.Infusions.add(infusionCorrupted);
        RecipeManager.RitesAndRituals.add(
            ++lastID,
            lastID + 100,
            new RiteInfusePlayers(this.infusionCorrupted, 200, 4),
            new SacrificeMultiple(
                new SacrificeItem(new ItemStack(APcCItems.material, 1, 3)),
                new SacrificePower(4000, 20)),
            EnumSet.noneOf(RitualTraits.class),
            new Circle[] { new Circle(0, 16, 0), new Circle(0, 28, 0) });
        RiteRegistry.instance()
            .getRitual((byte) lastID)
            .setUnlocalizedName("apcc.rite.infusion_corrupted");
    }

    private void registerCauldronRecipes() {
        WitcheryHelper.removeAllBrewRecipes(new ItemStack(Witchery.Items.CHALK_RITUAL));
        WitcheryHelper.addBrewRecipe(                1000, new ItemStack(Witchery.Items.CHALK_GOLDEN),
        new ItemStack(Witchery.Items.CHALK_RITUAL),
        ChromaItems.CRAFTING.getCraftedMetadataProduct(1, 28), // Energetic Essence
        new ItemStack(APcCItems.material, 1, 1)
        );

        WitcheryHelper.addBrewRecipe(                1000, new ItemStack(Witchery.Items.CHALK_INFERNAL),
        new ItemStack(Witchery.Items.CHALK_RITUAL),
        ChromaItems.TIERED.getCraftedMetadataProduct(1, 10), // Firaxite
        new ItemStack(Items.blaze_powder)
        );

        WitcheryHelper.addBrewRecipe(                1000, new ItemStack(Witchery.Items.CHALK_OTHERWHERE),
        new ItemStack(Witchery.Items.CHALK_RITUAL),
        ChromaItems.TIERED.getCraftedMetadataProduct(1, 19), // Spatial Rifting Powder
        new ItemStack(Witchery.Items.GENERIC, 1, 67) // Ender Dew
        );
    }

}
