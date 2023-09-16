package com.lordpine.apcc.chromaticraft;

import com.lordpine.apcc.chromaticraft.castingrecipes.LuminescentPearlRecipe;

import Reika.ChromatiCraft.Auxiliary.RecipeManagers.RecipesCastingTable;

public class ChromaticRecipes {

    public static void addCastingRecipes() {
        RecipesCastingTable.instance.addModdedRecipe(new LuminescentPearlRecipe());
    }
}
