package com.lordpine.apcc.witchery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

import net.minecraft.item.ItemStack;

import com.emoniph.witchery.brewing.AltarPower;
import com.emoniph.witchery.brewing.BrewItemKey;
import com.emoniph.witchery.brewing.WitcheryBrewRegistry;
import com.emoniph.witchery.brewing.action.BrewAction;
import com.emoniph.witchery.brewing.action.BrewActionModifier;
import com.emoniph.witchery.brewing.action.BrewActionRitualRecipe;
import com.emoniph.witchery.crafting.KettleRecipes;
import com.lordpine.apcc.APolyChromaticCore;

/**
 * Shamelessly copied over from the NewHorizonsCoreMod WitcheryBrewRegistryAccessor and WitcheryPlugin classes. All
 * credit goes to them.
 */
public class WitcheryHelper {

    static final Method methodRegister;
    static final Field fieldRecipes;
    static final Hashtable<BrewItemKey, BrewAction> ingredient;

    static {
        Hashtable<BrewItemKey, BrewAction> ingredient1;
        Method tmp;
        Field field, f2;
        try {
            final ClassLoader classLoader = WitcheryPlugin.class.getClassLoader();
            Class<?> clazz = Class.forName("com.emoniph.witchery.brewing.WitcheryBrewRegistry", false, classLoader);
            tmp = clazz.getDeclaredMethod("register", BrewAction.class);
            tmp.setAccessible(true);
            field = clazz.getDeclaredField("ingredients");
            field.setAccessible(true);
            ingredient1 = getIngredient(field);
            clazz = Class.forName("com.emoniph.witchery.brewing.action.BrewActionRitualRecipe", false, classLoader);
            f2 = clazz.getDeclaredField("recipes");
            f2.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            APolyChromaticCore.LOG
                .error("Cannot find Witchery brew registry stuff. Related functionality will have no effect!", e);
            tmp = null;
            ingredient1 = null;
            f2 = null;
        }
        ingredient = ingredient1;
        methodRegister = tmp;
        fieldRecipes = f2;
    }

    @SuppressWarnings("unchecked")
    private static Hashtable<BrewItemKey, BrewAction> getIngredient(Field field) throws IllegalAccessException {
        return (Hashtable<BrewItemKey, BrewAction>) field.get(WitcheryBrewRegistry.INSTANCE);
    }

    private static void registerBrewAction(BrewAction action) {
        if (methodRegister != null) {
            try {
                methodRegister.invoke(WitcheryBrewRegistry.INSTANCE, action);
            } catch (IllegalAccessException | InvocationTargetException e) {
                APolyChromaticCore.LOG.error("Error registering brew action", e);
            }
        }
    }

    private static void modifyBrewRecipe(BrewActionRitualRecipe ritualRecipe, BrewActionRitualRecipe.Recipe[] recipes) {
        removeAction(ritualRecipe);
        AltarPower power = new AltarPower(0);
        ritualRecipe.accumulatePower(power);
        registerBrewAction(new BrewActionRitualRecipe(ritualRecipe.ITEM_KEY, power, recipes));
    }

    private static void removeAction(BrewActionRitualRecipe action) {
        ingredient.remove(action.ITEM_KEY);
        WitcheryBrewRegistry.INSTANCE.getRecipes()
            .remove(action);
    }

    private static BrewActionRitualRecipe.Recipe[] getRecipes(BrewActionRitualRecipe ritualRecipe) {
        try {
            return (BrewActionRitualRecipe.Recipe[]) fieldRecipes.get(ritualRecipe);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private static boolean isCauldronRecipeMatch(BrewActionRitualRecipe.Recipe recipe, ItemStack lastItem,
        ItemStack[] items) {
        final ItemStack[] ingredients = recipe.ingredients;
        final int length = ingredients.length;
        if (length != items.length + 1) return false;
        boolean[] found = new boolean[length];
        for (ItemStack item : items) {
            boolean foundThisRound = false;
            int i;
            for (i = 0; i < length - 1; i++) {
                if (!found[i] && item.isItemEqual(ingredients[i])) {
                    found[i] = true;
                    foundThisRound = true;
                    break;
                }
            }
            if (!foundThisRound && !found[i] && lastItem.isItemEqual(ingredients[i])) {
                found[i] = true;
                foundThisRound = true;
            }
            if (!foundThisRound) return false;
        }
        // length check done already, no need to repeat
        return true;
    }

    /**
     * Add a brew action for given stack. This action will do nothing upon added beyond consuming alter power This is
     * necessary for an item to be able to tossed into the cauldron.
     *
     * @param item  The stack that should be tossed into the cauldron, stack size does not matter
     * @param power The power to consume. Can be 0.
     */
    public static void ensureItemHaveBrewAction(ItemStack item, int power) {
        if (item != null && WitcheryBrewRegistry.INSTANCE.getActionForItemStack(item) == null) {
            registerBrewAction(new EmptyBrewActionModifier(item, power));
        }
    }

    /**
     * Add a brew recipe matching given items.
     *
     * @param lastItem The last item in the recipe, i.e. the recipe you used to complete the brew
     * @param items    items in recipe. order does not matter. DOES NOT INCLUDE lastItem!
     */
    public static void addBrewRecipe(int power, ItemStack result, ItemStack lastItem, ItemStack... items) {
        if (ingredient == null) return;
        final BrewItemKey key = BrewItemKey.fromStack(lastItem);
        BrewAction action = ingredient.get(key);
        if (action instanceof EmptyBrewActionModifier) {
            action = null;
        }
        // last item should not have a default action
        for (ItemStack item : items) {
            ensureItemHaveBrewAction(item, 0);
        }
        if (action == null) {
            registerBrewAction(
                new BrewActionRitualRecipe(
                    key,
                    new AltarPower(power),
                    new BrewActionRitualRecipe.Recipe(result, items)));
        } else if (action instanceof BrewActionRitualRecipe) {
            final BrewActionRitualRecipe ritualRecipe = (BrewActionRitualRecipe) action;
            final BrewActionRitualRecipe.Recipe[] old = getRecipes(ritualRecipe);
            final BrewActionRitualRecipe.Recipe[] recipes = Arrays.copyOf(old, old.length + 1);
            recipes[recipes.length - 1] = new BrewActionRitualRecipe.Recipe(result, items);
            modifyBrewRecipe(ritualRecipe, recipes);
        } else {
            APolyChromaticCore.LOG.warn("Conflicting brew recipe! key: {}", lastItem);
        }
    }

    public static void removeAllBrewRecipes(ItemStack lastItem) {
        if (ingredient == null) return;
        final BrewItemKey key = BrewItemKey.fromStack(lastItem);
        final BrewAction action = ingredient.get(key);
        if (action == null) {
            APolyChromaticCore.LOG.warn("There is no brew using {} as last item", lastItem);
            return;
        }
        if (action instanceof BrewActionRitualRecipe) {
            removeAction((BrewActionRitualRecipe) action);
        } else {
            APolyChromaticCore.LOG.warn("There is no cauldron recipe matching using {} as last item:", lastItem);
        }
    }

    /**
     * Remove a brew recipe matching given items.
     *
     * @param lastItem The last item in the recipe, i.e. the recipe you used to complete the brew
     * @param items    items in recipe. order does not matter. DOES NOT INCLUDE lastItem!
     */
    public static void removeBrewRecipe(ItemStack lastItem, ItemStack... items) {
        if (ingredient == null) return;
        final BrewItemKey key = BrewItemKey.fromStack(lastItem);
        final BrewAction action = ingredient.get(key);
        if (action == null) {
            APolyChromaticCore.LOG.warn("There is no brew using {} as last item", lastItem);
            return;
        }
        if (action instanceof BrewActionRitualRecipe) {
            final BrewActionRitualRecipe ritualRecipe = (BrewActionRitualRecipe) action;
            for (BrewActionRitualRecipe.Recipe recipe : ritualRecipe.getExpandedRecipes()) {
                if (isCauldronRecipeMatch(recipe, lastItem, items)) {
                    if (ritualRecipe.getExpandedRecipes()
                        .size() > 1) {
                        // preserve other recipes
                        modifyBrewRecipe(
                            ritualRecipe,
                            ritualRecipe.getExpandedRecipes()
                                .stream()
                                .filter(r -> r != recipe)
                                .map(
                                    r -> new BrewActionRitualRecipe.Recipe(
                                        r.result,
                                        Arrays.copyOf(r.ingredients, r.ingredients.length - 1)))
                                .toArray(BrewActionRitualRecipe.Recipe[]::new));
                    } else {
                        removeAction(ritualRecipe);
                    }
                    return;
                }
            }
        }
        APolyChromaticCore.LOG.warn(
            "There is no cauldron recipe matching these items: last: {}, rest: " + Arrays.toString(items),
            lastItem);
    }

    public static void removeAllKettleRecipe(ItemStack output) {
        KettleRecipes.instance().recipes.removeIf(recipe -> recipe.output.isItemEqual(output));
    }

    public static void removeRecipe(KettleRecipes.KettleRecipe recipe) {
        if (!KettleRecipes.instance().recipes.remove(recipe))
            APolyChromaticCore.LOG.warn("Recipe already removed: {}", recipe);
    }

    private static class EmptyBrewActionModifier extends BrewActionModifier {

        public EmptyBrewActionModifier(ItemStack item, int power) {
            super(BrewItemKey.fromStack(item), null, new AltarPower(power));
        }
    }
}
