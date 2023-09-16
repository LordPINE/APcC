package com.lordpine.apcc.witchery;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.emoniph.witchery.infusion.Infusion;

import Reika.VoidMonster.Auxiliary.VoidMonsterDamage;

public class CorruptedInfusion extends Infusion {

    public CorruptedInfusion(int infusionID) {
        super(infusionID);
    }

    public IIcon getPowerBarIcon(EntityPlayer player, int index) {
        return Blocks.bedrock.getIcon(0, 0);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public void onUsingItemTick(ItemStack itemstack, World world, EntityPlayer player, int countdown) {
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        if (!world.isRemote) {
            List<EntityLiving> entitiesWithinAABB = world.getEntitiesWithinAABB(
                EntityLiving.class,
                AxisAlignedBB.getBoundingBox(x - 5.0D, y - 5.0D, z - 5.0D, x + 5.0D, y + 5.0D, z + 5.0D));
            for (EntityLiving entity : entitiesWithinAABB) {
                double dx = x - entity.posX;
                double dy = y - entity.posY;
                double dz = z - entity.posZ;
                entity.motionX += Math.signum(dx) * (1 / (Math.pow(dx, 2) + 2.0F));
                entity.motionY += Math.signum(dy) * (1 / (Math.pow(dy, 2) + 2.0F));
                entity.motionZ += Math.signum(dz) * (1 / (Math.pow(dz, 2) + 2.0F));
                entity.attackEntityFrom(new VoidMonsterDamage(null), 1.0F);
            }
        }
    }
}
