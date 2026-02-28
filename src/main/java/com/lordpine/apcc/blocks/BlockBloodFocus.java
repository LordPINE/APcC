package com.lordpine.apcc.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodFocus extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon icon;

    protected BlockBloodFocus() {
        super(Material.rock);
        this.setHardness(1.5f);
        this.setResistance(6.0f);
        this.setStepSound(stepSound);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.icon = reg.registerIcon(Tags.MODID + ":blood_focus");
    }

}
