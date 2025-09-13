package com.lordpine.apcc.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetalloakLog extends BlockLog {

    public static IIcon sprMetalloakTop;
    public static IIcon sprMetalloakSide;

    public BlockMetalloakLog() {
        super();
        this.setHardness(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        int orient = meta & 12;
        // int woodType = meta & 3;

        return orient == 0 && (side == 1 || side == 0) ? sprMetalloakTop
            : (orient == 4 && (side == 5 || side == 4) ? sprMetalloakTop
                : (orient == 8 && (side == 2 || side == 3) ? sprMetalloakTop : sprMetalloakSide));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        BlockMetalloakLog.sprMetalloakSide = iconRegister.registerIcon(Tags.MODID + ":metalloak_side");
        BlockMetalloakLog.sprMetalloakTop = iconRegister.registerIcon(Tags.MODID + ":metalloak_top");
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(APcCBlocks.metalloak_log);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
        itemList.add(new ItemStack(item, 1, 0));
    }
}
