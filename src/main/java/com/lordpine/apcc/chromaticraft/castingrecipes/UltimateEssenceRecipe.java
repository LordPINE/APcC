package com.lordpine.apcc.chromaticraft.castingrecipes;

import net.minecraft.item.ItemStack;

import com.lordpine.apcc.items.APcCItems;

import Reika.ChromatiCraft.Auxiliary.RecipeManagers.CastingRecipe.PylonCastingRecipe;
import Reika.ChromatiCraft.Registry.CrystalElement;

public class UltimateEssenceRecipe extends PylonCastingRecipe {

    public UltimateEssenceRecipe(CrystalElement e, ItemStack dimCore) {
        super(new ItemStack(APcCItems.ultimate_essence, 1, e.ordinal()), dimCore);
        this.addAuraRequirement(e, 1_000_000);
    }
}
