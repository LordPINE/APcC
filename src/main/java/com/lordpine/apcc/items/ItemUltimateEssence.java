package com.lordpine.apcc.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;

public class ItemUltimateEssence extends Item implements IHaloRenderItem {

    final int types = 16;
    IIcon[] icons;

    public ItemUltimateEssence() {
        this.setUnlocalizedName("ultimate_essence");
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item." + APcCItems.ULTIMATE_ESSENCE_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean drawHalo(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getHaloTexture(ItemStack stack) {
        return ((ItemResource) LudicrousItems.resource).halo[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getHaloSize(ItemStack stack) {
        return 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean drawPulseEffect(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getHaloColour(ItemStack stack) {
        return stack.getItemDamage() < 16 ? APcCItems.CRC_COLOURS[stack.getItemDamage()] : 0xFF000000;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[types];
        for (int i = 0; i < icons.length; i++)
            icons[i] = iconRegister.registerIcon(Tags.MODID + ":" + APcCItems.ULTIMATE_ESSENCE_NAMES[i]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return icons[Math.min(icons.length - 1, par1)];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < types; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
