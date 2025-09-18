package com.lordpine.apcc.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import vazkii.botania.api.recipe.IFlowerComponent;

public class ItemGeneric extends Item implements IHaloRenderItem, IFlowerComponent {

    final int types = APcCItems.GENERIC_ITEM_NAMES.length;
    IIcon[] icons;

    public ItemGeneric(String unlocalizedName) {
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < types; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean drawHalo(ItemStack stack) {
        return (stack.getItemDamage() == 20);
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
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getHaloColour(ItemStack stack) {
        return 0x33FCFC3A;
    }

    @Override
    public boolean canFit(ItemStack stack, IInventory apothecary) {
        int meta = stack.getItemDamage();
        return (meta == 0 || meta == 1 || meta == 7 || meta == 8 || meta == 9);
    }

    @Override
    public int getParticleColor(ItemStack stack) {
        return 0xFFFFFF;
    }
}
