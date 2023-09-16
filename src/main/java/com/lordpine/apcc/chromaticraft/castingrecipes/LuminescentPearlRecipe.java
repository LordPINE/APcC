package com.lordpine.apcc.chromaticraft.castingrecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;

import com.lordpine.apcc.items.APcCItems;

import Reika.ChromatiCraft.Auxiliary.RecipeManagers.CastingRecipe.PylonCastingRecipe;
import Reika.ChromatiCraft.Magic.ElementTagCompound;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.ChromatiCraft.Registry.ChromaSounds;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.Render.Particle.EntityCCBlurFX;
import Reika.ChromatiCraft.Render.Particle.EntityRuneFX;
import Reika.ChromatiCraft.Render.Particle.EntitySparkleFX;
import Reika.ChromatiCraft.TileEntity.Recipe.TileEntityCastingTable;
import Reika.DragonAPI.Instantiable.Data.WeightedRandom;
import Reika.DragonAPI.Libraries.IO.ReikaSoundHelper;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.Rendering.ReikaColorAPI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.config.ConfigItems;

public class LuminescentPearlRecipe extends PylonCastingRecipe {

    private static final int[][] pedestalCoords = { { -4, -4 }, { -2, -4 }, { 0, -4 }, { 2, -4 }, { 4, -4 }, { 4, -2 },
        { 4, 0 }, { 4, 2 }, { 4, 4 }, { 2, 4 }, { 0, 4 }, { -2, 4 }, { -4, 4 }, { -4, 2 }, { -4, 0 }, { -4, -2 } };

    public LuminescentPearlRecipe() {
        super(new ItemStack(APcCItems.luminescent_pearl), new ItemStack(ConfigItems.itemEldritchObject, 1, 3));
        for (CrystalElement element : CrystalElement.values()) {
            this.addAuraRequirement(element, 2_000_000);
        }
        for (int i = 0; i < 16; i++) {
            this.addAuxItem(
                new ItemStack(APcCItems.ultimate_essence, 1, i),
                pedestalCoords[i][0],
                pedestalCoords[i][1]);
        }

        // Geode Gems
        this.addAuxItem(new ItemStack(ChromaItems.DIMGEN.getItemInstance(), 1, 5), 0, -2);
        this.addAuxItem(new ItemStack(ChromaItems.DIMGEN.getItemInstance(), 1, 5), 2, 0);
        this.addAuxItem(new ItemStack(ChromaItems.DIMGEN.getItemInstance(), 1, 5), -2, 0);
        this.addAuxItem(new ItemStack(ChromaItems.DIMGEN.getItemInstance(), 1, 5), 0, 2);

        this.addAuxItem(new ItemStack(APcCItems.material, 1, 4), -2, -2);
        this.addAuxItem(new ItemStack(APcCItems.material, 1, 4), 2, -2);
        this.addAuxItem(new ItemStack(APcCItems.material, 1, 4), -2, 2);
        this.addAuxItem(new ItemStack(APcCItems.material, 1, 4), 2, 2);
    }

    @Override
    public int getDuration() {
        return 10000;
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
        double px = te.xCoord + te.getRandom()
            .nextDouble();
        double pz = te.zCoord + te.getRandom()
            .nextDouble();
        double vy = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
        double vx = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
        double vz = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
        int t = dur - tick;
        for (int i = 0; i < 16; i++) {
            CrystalElement e = CrystalElement.elements[i];
            for (int j = 0; j < 3; j++) {
                px = x + te.getRandom()
                    .nextDouble() + pedestalCoords[i][0];
                pz = z + te.getRandom()
                    .nextDouble() + pedestalCoords[i][1];
                vx = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
                vz = ReikaRandomHelper.getRandomPlusMinus(0, 0.0625);
                float g = (float) ReikaRandomHelper.getRandomPlusMinus(0.125, 0.0625);
                int l = 20;
                float s = (float) ReikaRandomHelper.getRandomPlusMinus(2F, 1F);
                EntityFX fx = new EntityCCBlurFX(e, te.getWorldObj(), px, y + 2, pz, vx, vy, vz).setScale(s)
                    .setLife(l)
                    .setGravity(g);
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
        }
        if (t % 18 == 0) {
            ElementTagCompound tag = ElementTagCompound.getUniformTag(1);
            WeightedRandom<CrystalElement> w = tag.asWeightedRandom();
            for (int i = 0; i < 32; i++) {
                CrystalElement e = w.getRandomEntry();
                double ang = te.getRandom()
                    .nextDouble() * 360;
                double v = 0.125;
                vx = v * Math.cos(Math.toRadians(ang));
                vy = ReikaRandomHelper.getRandomPlusMinus(v, v);
                vz = v * Math.sin(Math.toRadians(ang));
                int c = ReikaColorAPI.mixColors(e.getColor(), 0xffffff, 0.5F);
                EntityFX fx = new EntitySparkleFX(te.getWorldObj(), x + 0.5, y + 0.5, z + 0.5, vx, vy, vz).setColor(c)
                    .setScale(1);
                fx.noClip = true;
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
            if (te.getRandom()
                .nextDouble() < 0.0625) {
                ReikaSoundHelper.playClientSound(ChromaSounds.PYLONFLASH, x + 0.5, y + 0.5, z + 0.5, 1.0F, 1.0F, false);
                CrystalElement e = w.getRandomEntry();
                for (int i = 0; i < 32; i++) {
                    px = x + te.getRandom()
                        .nextDouble() + pedestalCoords[e.ordinal()][0];
                    pz = z + te.getRandom()
                        .nextDouble() + pedestalCoords[e.ordinal()][1];
                    vx = ReikaRandomHelper.getRandomPlusMinus(0, 0.03125);
                    vy = ReikaRandomHelper.getRandomPlusMinus(0.125, 0.03125);
                    vz = ReikaRandomHelper.getRandomPlusMinus(0, 0.03125);
                    float g = (float) ReikaRandomHelper.getRandomPlusMinus(0.125, 0.0625);
                    int l = 120;
                    float s = (float) ReikaRandomHelper.getRandomPlusMinus(3F, 2F);
                    EntityFX fx = new EntityRuneFX(te.getWorldObj(), px, te.yCoord + 2, pz, vx, vy, vz, e).setScale(s)
                        .setLife(l)
                        .setGravity(g);
                    Minecraft.getMinecraft().effectRenderer.addEffect(fx);
                }
            }
        }
        if (t % 200 == 0) {
            ReikaSoundHelper
                .playClientSound(ChromaSounds.PYLONBOOSTRITUAL, x + 0.5, y + 0.5, z + 0.5, 0.75F, 1.0F, false);
        }
    }

}
