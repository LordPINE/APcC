package com.lordpine.apcc.witchery;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.emoniph.witchery.blocks.BlockCircle.TileEntityCircle.ActivatedRitual;
import com.emoniph.witchery.ritual.Rite;
import com.emoniph.witchery.ritual.RitualStep;

import Reika.VoidMonster.Entity.EntityVoidMonster;

public class VoidMonsterRite extends Rite {

    @Override
    public void addSteps(ArrayList<RitualStep> steps, int initialStage) {
        steps.add(new StepSummonVoidMonster());
    }

    private static class StepSummonVoidMonster extends RitualStep {

        protected StepSummonVoidMonster() {
            super(false);
        }

        @Override
        public Result process(World world, int posX, int posY, int posZ, long ticks, ActivatedRitual ritual) {
            if (ticks % 20L != 0L) {
                return RitualStep.Result.STARTING;
            } else {
                Entity voidmonster = new EntityVoidMonster(world);
                voidmonster.forceSpawn = true;
                voidmonster.setLocationAndAngles(posX, posY + 3, posZ, 0, 0);
                world.spawnEntityInWorld(voidmonster);
                return RitualStep.Result.COMPLETED;
            }
        }
    }

}
