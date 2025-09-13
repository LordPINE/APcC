package com.lordpine.apcc.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

import com.lordpine.apcc.Tags;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetalloakLeaves extends BlockLeaves {

    int[] decayValues;

    @SideOnly(Side.CLIENT)
    private IIcon opaqueIcon;

    @SideOnly(Side.CLIENT)
    private IIcon transparentIcon;

    public BlockMetalloakLeaves() {
        super();
        setLightOpacity(0);
        setHardness(0.4F);
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(APcCBlocks.metalloak_sapling);
    }

    /**
     * Chance of sapling dropping
     */
    @Override
    protected int func_150123_b(int p_150123_1_) {
        return 60;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
        return !Blocks.leaves.isOpaqueCube() || super.shouldSideBeRendered(iba, x, y, z, side);
    }

    @Override
    public boolean isOpaqueCube() {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return !Blocks.leaves.isOpaqueCube() ? transparentIcon : opaqueIcon;
    }

    @Override
    public String[] func_150125_e() {
        return new String[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        opaqueIcon = register.registerIcon(Tags.MODID + ":metalloak_leaves_opaque");
        transparentIcon = register.registerIcon(Tags.MODID + ":metalloak_leaves");
    }

    @Override
    protected void func_150124_c(World world, int x, int y, int z, int meta, int wat) {
        if (world.rand.nextInt(20) == 0) {
            this.dropBlockAsItem(world, x, y, z, new ItemStack(TFItems.steeleafIngot, 1, 0));
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);

            if ((meta & 8) != 0 && (meta & 4) == 0) {
                byte decay_update_radius = 4;
                int chunk_check_radius = decay_update_radius + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;

                if (this.decayValues == null) {
                    this.decayValues = new int[b1 * b1 * b1]; // Large array instead of 3D array
                }

                int i;

                if (world.checkChunksExist(
                    x - chunk_check_radius,
                    y - chunk_check_radius,
                    z - chunk_check_radius,
                    x + chunk_check_radius,
                    y + chunk_check_radius,
                    z + chunk_check_radius)) {
                    int j;
                    int k;

                    for (i = -decay_update_radius; i <= decay_update_radius; ++i) {
                        for (j = -decay_update_radius; j <= decay_update_radius; ++j) {
                            for (k = -decay_update_radius; k <= decay_update_radius; ++k) {
                                Block block = world.getBlock(x + i, y + j, z + k);

                                if (block != APcCBlocks.metalloak_log) {
                                    if (block.isLeaves(world, x + i, y + j, z + k)) {
                                        this.decayValues[(i + k1) * j1 + (j + k1) * b1 + k + k1] = -2;
                                    } else {
                                        this.decayValues[(i + k1) * j1 + (j + k1) * b1 + k + k1] = -1;
                                    }
                                } else {
                                    this.decayValues[(i + k1) * j1 + (j + k1) * b1 + k + k1] = 0;
                                }
                            }
                        }
                    }

                    for (i = 1; i <= 4; ++i) {
                        for (j = -decay_update_radius; j <= decay_update_radius; ++j) {
                            for (k = -decay_update_radius; k <= decay_update_radius; ++k) {
                                for (int k2 = -decay_update_radius; k2 <= decay_update_radius; ++k2) {
                                    if (this.decayValues[(j + k1) * j1 + (k + k1) * b1 + k2 + k1] == i - 1) {
                                        if (this.decayValues[(j + k1 - 1) * j1 + (k + k1) * b1 + k2 + k1] == -2) {
                                            this.decayValues[(j + k1 - 1) * j1 + (k + k1) * b1 + k2 + k1] = i;
                                        }

                                        if (this.decayValues[(j + k1 + 1) * j1 + (k + k1) * b1 + k2 + k1] == -2) {
                                            this.decayValues[(j + k1 + 1) * j1 + (k + k1) * b1 + k2 + k1] = i;
                                        }

                                        if (this.decayValues[(j + k1) * j1 + (k + k1 - 1) * b1 + k2 + k1] == -2) {
                                            this.decayValues[(j + k1) * j1 + (k + k1 - 1) * b1 + k2 + k1] = i;
                                        }

                                        if (this.decayValues[(j + k1) * j1 + (k + k1 + 1) * b1 + k2 + k1] == -2) {
                                            this.decayValues[(j + k1) * j1 + (k + k1 + 1) * b1 + k2 + k1] = i;
                                        }

                                        if (this.decayValues[(j + k1) * j1 + (k + k1) * b1 + (k2 + k1 - 1)] == -2) {
                                            this.decayValues[(j + k1) * j1 + (k + k1) * b1 + (k2 + k1 - 1)] = i;
                                        }

                                        if (this.decayValues[(j + k1) * j1 + (k + k1) * b1 + k2 + k1 + 1] == -2) {
                                            this.decayValues[(j + k1) * j1 + (k + k1) * b1 + k2 + k1 + 1] = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                i = this.decayValues[k1 * j1 + k1 * b1 + k1];

                if (i >= 0) {
                    world.setBlockMetadataWithNotify(x, y, z, meta & -9, 4);
                } else {
                    this.removeLeaves(world, x, y, z);
                }
            }
        }
    }

    private void removeLeaves(World world, int x, int y, int z) {
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return 0xFFFFFF;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderColor(int metadata) {
        return 0xFFFFFF;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
        return 0xFFFFFF;
    }

    @Override
    public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }
}
