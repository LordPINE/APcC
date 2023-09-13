package com.lordpine.apcc.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGeneric extends Item {

    final int types = APcCItems.GENERIC_ITEM_NAMES.length;
    IIcon[] icons;

    public ItemGeneric(String unlocalizedName) {
        this.setMaxDamage(15);
        this.setUnlocalizedName(unlocalizedName);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[types];
        for (int i = 0; i < icons.length; i++)
            icons[i] = iconRegister.registerIcon(Tags.MODID + ":" + APcCItems.GENERIC_ITEM_NAMES[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return icons[Math.min(icons.length - 1, par1)];
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item." + APcCItems.GENERIC_ITEM_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
    }
}
