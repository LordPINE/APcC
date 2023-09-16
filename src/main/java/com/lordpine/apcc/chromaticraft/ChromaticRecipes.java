package com.lordpine.apcc.chromaticraft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.lordpine.apcc.chromaticraft.castingrecipes.LuminescentPearlRecipe;
import com.lordpine.apcc.chromaticraft.castingrecipes.UltimateEssenceRecipe;

import Reika.ChromatiCraft.Auxiliary.RecipeManagers.RecipesCastingTable;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.ChromatiCraft.Registry.CrystalElement;

public class ChromaticRecipes {

    public static void addCastingRecipes() {
        RecipesCastingTable.instance.addModdedRecipe(new LuminescentPearlRecipe());
        ItemStack dimCore = new ItemStack(ChromaItems.PLACER.getItemInstance(), 1, 43);
        for (CrystalElement e : CrystalElement.elements) {
            NBTTagCompound dimCoreTags = new NBTTagCompound();
            dimCoreTags.setInteger("color", e.ordinal());
            dimCore.setTagCompound(dimCoreTags);
            RecipesCastingTable.instance.addModdedRecipe(new UltimateEssenceRecipe(e, dimCore.copy()));
        }
    }
}
