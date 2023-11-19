package com.lordpine.apcc.chromaticraft.castingrecipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;

import com.lordpine.apcc.items.APcCItems;

import Reika.ChromatiCraft.Auxiliary.ChromaStacks;
import Reika.ChromatiCraft.Auxiliary.RecipeManagers.CastingRecipe.PylonCastingRecipe;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.Render.Particle.EntityRuneFX;
import Reika.ChromatiCraft.Render.Particle.EntitySparkleFX;
import Reika.ChromatiCraft.TileEntity.Recipe.TileEntityCastingTable;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.LudicrousItems;

public class UltimateEssenceRecipe extends PylonCastingRecipe {

    private static final int[][] element_specific_item_positions = { { 2, 2 }, { 2, -2 }, { -2, 2 }, { -2, -2 },
        { 0, -4 }, { 4, 0 }, { 0, 4 }, { -4, 0 } };
    private static final Map<CrystalElement, List<ItemStack>> element_specific_items = new HashMap<>();

    CrystalElement element;

    public UltimateEssenceRecipe(CrystalElement e, ItemStack dimCore) {
        super(new ItemStack(APcCItems.ultimate_essence, 1, e.ordinal()), dimCore);
        this.element = e;
        if (element_specific_items.size() == 0) initializeItemMap();

        // Colored Combs
        this.addAuxItem(ChromaItems.COLOREDMOD.getStackOf(e), 0, 2);
        this.addAuxItem(ChromaItems.COLOREDMOD.getStackOf(e), 0, -2);
        this.addAuxItem(ChromaItems.COLOREDMOD.getStackOf(e), 2, 0);
        this.addAuxItem(ChromaItems.COLOREDMOD.getStackOf(e), -2, 0);

        this.addAuxItem(ChromaStacks.getChargedShard(e), 2, 4);
        this.addAuxItem(ChromaStacks.getChargedShard(e), -2, 4);
        this.addAuxItem(ChromaStacks.getChargedShard(e), 4, 2);
        this.addAuxItem(ChromaStacks.getChargedShard(e), 4, -2);
        this.addAuxItem(ChromaStacks.getChargedShard(e), 2, -4);
        this.addAuxItem(ChromaStacks.getChargedShard(e), -2, -4);
        this.addAuxItem(ChromaStacks.getChargedShard(e), -4, 2);
        this.addAuxItem(ChromaStacks.getChargedShard(e), -4, -2);

        this.addAuxItem(new ItemStack(LudicrousItems.resource, 1, 4), 4, 4);
        this.addAuxItem(new ItemStack(LudicrousItems.resource, 1, 4), -4, 4);
        this.addAuxItem(new ItemStack(LudicrousItems.resource, 1, 4), 4, -4);
        this.addAuxItem(new ItemStack(LudicrousItems.resource, 1, 4), -4, -4);

        List<ItemStack> element_items = element_specific_items.get(e);
        for (int i = 0; i < element_items.size(); i++) {
            if (element_items.get(i) != null) {
                this.addAuxItem(
                    element_items.get(i)
                        .copy(),
                    element_specific_item_positions[i][0],
                    element_specific_item_positions[i][1]);
            }
        }

        this.addAuraRequirement(e, 1_000_000);
    }

    @Override
    public int getDuration() {
        return 1000;
    }

    @Override
    public void onRecipeTick(TileEntityCastingTable te) {
        if (te.getWorldObj().isRemote) {
            this.doParticleFX(te, te.getCraftingTick(), te.getRecipeTickDuration(this));
        } else {

        }
    }

    @SideOnly(Side.CLIENT)
    private void doParticleFX(TileEntityCastingTable te, int tick, int dur) {
        int x = te.xCoord;
        int y = te.yCoord;
        int z = te.zCoord;
        int t = dur - tick;
        doRunes(te, x, y, z);
        if (t % 18 == 0) {
            doTableParticles(x, y, z, te);

        }
    }

    private void doRunes(TileEntityCastingTable te, int x, int y, int z) {
        for (int j = 0; j < 3; j++) {
            double px = ReikaRandomHelper.getRandomPlusMinus(x + 0.5, 6);
            double py = ReikaRandomHelper.getRandomPlusMinus(y + 2, 4);
            double pz = ReikaRandomHelper.getRandomPlusMinus(z + 0.5, 6);
            double vx = 0;
            double vy = 0;
            double vz = 0;
            float g = 0.0625F;
            int l = 60;
            float s = (float) ReikaRandomHelper.getRandomPlusMinus(1F, 0.5F);
            EntityFX fx = new EntityRuneFX(te.getWorldObj(), px, py, pz, vx, vy, vz, this.element).setScale(s)
                .setLife(l)
                .setGravity(g);
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    private void doTableParticles(int x, int y, int z, TileEntityCastingTable te) {
        for (int i = 0; i < 32; i++) {
            double ang = te.getRandom()
                .nextDouble() * 360;
            double v = 0.125;
            double vx = v * Math.cos(Math.toRadians(ang));
            double vy = ReikaRandomHelper.getRandomPlusMinus(v, v);
            double vz = v * Math.sin(Math.toRadians(ang));
            int c = ReikaColorAPI.mixColors(this.element.getColor(), 0xffffff, 0.5F);
            EntityFX fx = new EntitySparkleFX(te.getWorldObj(), x + 0.5, y + 0.5, z + 0.5, vx, vy, vz).setColor(c)
                .setScale(1);
            fx.noClip = true;
            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }
    }

    private static void initializeItemMap() {
        List<ItemStack> items;
        ItemStack corner_item = ReikaItemHelper.lookupItem("TaintedMagic:ItemMaterial:6"); // Shadowmetal
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            new ItemStack(APcCItems.material, 1, 7), // Rune of Elements
            ReikaItemHelper.lookupItem("witchery:ingredient:121"),
            ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseAlchemyItems:4"),
            ReikaItemHelper.lookupItem("witchery:ingredient:121"));
        element_specific_items.put(CrystalElement.BLACK, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:20");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("arsmagica2:lifeWard"),
            ReikaItemHelper.lookupItem("witchery:poppet:6"),
            ReikaItemHelper.lookupItem("Thaumcraft:FocusWarding"),
            ReikaItemHelper.lookupItem("witchery:poppet:6"));
        element_specific_items.put(CrystalElement.RED, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:21");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("arsmagica2:natureScythe"),
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug"),
            new ItemStack(APcCItems.material, 1, 6), // Will of the Ancients
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug"));
        element_specific_items.put(CrystalElement.GREEN, items);
        items = Arrays.asList(
            ReikaItemHelper.lookupItem("WitchingGadgets:item.WG_Cluster:1"),
            ReikaItemHelper.lookupItem("WitchingGadgets:item.WG_Cluster:2"),
            ReikaItemHelper.lookupItem("aobd:clusterRutile"),
            ReikaItemHelper.lookupItem("aobd:clusterDraconium"),
            ReikaItemHelper.lookupItem("arsmagica2:earth_armor"),
            ReikaItemHelper.lookupItem("clockworkphase:extractorEarth"),
            ReikaItemHelper.lookupItem("ForgottenRelics:ItemAdvancedMiningCharm"),
            ReikaItemHelper.lookupItem("clockworkphase:extractorEarth"));
        element_specific_items.put(CrystalElement.BROWN, items);
        corner_item = ReikaItemHelper.lookupItem("ChromatiCraft:chromaticraft_item_bucket:4"); // Luma Bucket
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("witchery:ingredient:42"), // Soul of the Light
            ReikaItemHelper.lookupItem("ExtraUtilities:magnumTorch"),
            ReikaItemHelper.lookupItem("ThaumicTinkerer:brightNitor"),
            ReikaItemHelper.lookupItem("ExtraUtilities:magnumTorch"));
        element_specific_items.put(CrystalElement.BLUE, items);
        corner_item = ReikaItemHelper.lookupItem("Automagy:blockBookshelfEnchanted:1");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("thaumicbases:fociExperience"),
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug:2"),
            ReikaItemHelper.lookupItem("AWWayofTime:AlchemicalWizardrytile.blockSpellEnhancement:13"),
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug:2"));
        element_specific_items.put(CrystalElement.PURPLE, items);
        corner_item = ReikaItemHelper.lookupItem("Mariculture:pearls:8");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("arsmagica2:water_orbs"),
            ReikaItemHelper.lookupItem("witchery:ingredient:77"),
            ReikaItemHelper.lookupItem("ThaumicTinkerer:fireWater"),
            ReikaItemHelper.lookupItem("witchery:ingredient:77"));
        element_specific_items.put(CrystalElement.CYAN, items);
        corner_item = ReikaItemHelper.lookupItem("witchery:dupgrenade");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("witchery:spectralstone:19"),
            ReikaItemHelper.lookupItem("arsmagica2:illusionBlock:1"),
            ReikaItemHelper.lookupItem("witchery:kobolditehelm"),
            ReikaItemHelper.lookupItem("arsmagica2:illusionBlock:1"));
        element_specific_items.put(CrystalElement.LIGHTGRAY, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:demonicTelepositionFocus");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("witchery:ingredient:125"),
            ReikaItemHelper.lookupItem("Thaumcraft:FocusTrade"),
            ReikaItemHelper.lookupItem("witchery:ingredient:164"),
            ReikaItemHelper.lookupItem("Thaumcraft:FocusTrade"));
        element_specific_items.put(CrystalElement.GRAY, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:19");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("Sanguimancy:corruptedSword"),
            ReikaItemHelper.lookupItem("arsmagica2:cowHorn"),
            ReikaItemHelper.lookupItem("ForbiddenMagic:SkullAxe"),
            ReikaItemHelper.lookupItem("arsmagica2:cowHorn"));
        element_specific_items.put(CrystalElement.PINK, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:12");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("ThaumicTinkerer:focusRecall"),
            ReikaItemHelper.lookupItem("aura:magicRoad"),
            ReikaItemHelper.lookupItem("TaintedMagic:ItemVoidwalkerBoots"),
            ReikaItemHelper.lookupItem("aura:magicRoad"));
        element_specific_items.put(CrystalElement.LIME, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:22");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("arsmagica2:lightningCharm"),
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug:1"),
            ReikaItemHelper.lookupItem("DraconicEvolution:draconiumEnergyCore:1"),
            ReikaItemHelper.lookupItem("Satisforestry:satisforestry_block_slug:1"));
        element_specific_items.put(CrystalElement.YELLOW, items);
        corner_item = ReikaItemHelper.lookupItem("clockworkphase:gearTemporal");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("Automagy:blockHourglassMagic"),
            ReikaItemHelper.lookupItem("MagicBees:miscResources:9"),
            ReikaItemHelper.lookupItem("AWWayofTime:sigilOfHaste"),
            ReikaItemHelper.lookupItem("MagicBees:miscResources:9"));
        element_specific_items.put(CrystalElement.LIGHTBLUE, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:31");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("ExtraUtilities:defoliageAxe"),
            ReikaItemHelper.lookupItem("Mariculture:crafting:20"),
            ReikaItemHelper.lookupItem("TConstruct:heartCanister:6"),
            ReikaItemHelper.lookupItem("Mariculture:crafting:20"));
        element_specific_items.put(CrystalElement.MAGENTA, items);
        corner_item = ReikaItemHelper.lookupItem("AWWayofTime:bloodMagicBaseItems:10");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("arsmagica2:fire_ears"),
            ReikaItemHelper.lookupItem("ThaumicTinkerer:fireFire"),
            ReikaItemHelper.lookupItem("witchery:ingredient:139"),
            ReikaItemHelper.lookupItem("ThaumicTinkerer:fireFire"));
        element_specific_items.put(CrystalElement.ORANGE, items);
        corner_item = ReikaItemHelper.lookupItem("ExtraUtilities:greenscreen");
        items = Arrays.asList(
            corner_item,
            corner_item,
            corner_item,
            corner_item,
            ReikaItemHelper.lookupItem("Thaumcraft:ItemBathSalts"),
            ReikaItemHelper.lookupItem("Mariculture:upgrade:20"),
            ReikaItemHelper.lookupItem("arsmagica2:essence:10"),
            ReikaItemHelper.lookupItem("Mariculture:upgrade:20"));
        element_specific_items.put(CrystalElement.WHITE, items);
    }
}
