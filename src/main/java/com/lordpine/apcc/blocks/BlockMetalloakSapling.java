package com.lordpine.apcc.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.lordpine.apcc.Tags;
import com.lordpine.apcc.world.WorldGenMetalloakTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetalloakSapling extends BlockSapling {

    private IIcon icon;

    public BlockMetalloakSapling() {
        super();
        this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            // this.checkFlowerChange(par1World, x, y, z);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(7) == 0) {
                this.func_149878_d(world, x, y, z, rand);
            }
        }
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    @Override
    public void func_149878_d(World world, int x, int y, int z, Random rand) {
        int meta = world.getBlockMetadata(x, y, z);
        WorldGenerator treeGenerator = new WorldGenMetalloakTree(true);
        boolean largeTree = false;

        if (largeTree) {
            world.setBlock(x, y, z, Blocks.air, 0, 4);
            world.setBlock(x + 1, y, z, Blocks.air, 0, 4);
            world.setBlock(x, y, z + 1, Blocks.air, 0, 4);
            world.setBlock(x + 1, y, z + 1, Blocks.air, 0, 4);
        } else {
            world.setBlock(x, y, z, Blocks.air, 0, 4);
        }

        if (!treeGenerator.generate(world, rand, x, y, z)) {
            if (largeTree) {
                world.setBlock(x, y, z, this, meta, 4);
                world.setBlock(x + 1, y, z, this, meta, 4);
                world.setBlock(x, y, z + 1, this, meta, 4);
                world.setBlock(x + 1, y, z + 1, this, meta, 4);
            } else {
                world.setBlock(x, y, z, this, meta, 4);
            }
        }
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<net.minecraft.item.ItemStack> list) {
        list.add(new ItemStack(itemIn, 1, 0));
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icon = reg.registerIcon(Tags.MODID + ":metalloak_sapling");
    }

}
