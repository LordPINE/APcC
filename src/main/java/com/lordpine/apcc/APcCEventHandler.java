package com.lordpine.apcc;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import com.lordpine.apcc.items.APcCItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import thaumcraft.common.config.ConfigBlocks;

public final class APcCEventHandler {

    @SubscribeEvent
    public void onEntityUpdate(WorldTickEvent event) {
        if (event.phase == Phase.END) {
            List<Entity> entities = new ArrayList<Entity>(event.world.loadedEntityList);
            for (Entity entity : entities) {
                if (entity instanceof EntityItem) {
                    if (event.world
                        .getBlock(
                            (int) Math.floor(entity.posX),
                            (int) Math.floor(entity.posY),
                            (int) Math.floor(entity.posZ))
                        .equals(ConfigBlocks.FLUIDDEATH.getBlock())) {
                        EntityItem entityItem = (EntityItem) entity;
                        if (matchesId(entityItem, "ExtraUtilities", "mini-soul")) {
                            List<Entity> itemList = event.world.getEntitiesWithinAABBExcludingEntity(
                                entityItem,
                                AxisAlignedBB.getBoundingBox(
                                    Math.floor(entity.posX),
                                    Math.floor(entity.posY),
                                    Math.floor(entity.posZ),
                                    Math.ceil(entity.posX),
                                    Math.ceil(entity.posY),
                                    Math.ceil(entity.posZ)));
                            for (Entity entity2 : itemList) {
                                if (entity2 instanceof EntityItem) {
                                    EntityItem item = (EntityItem) entity2;
                                    if (matchesId(item, "arsmagica2", "flickerJar")) {
                                        item.setEntityItemStack(new ItemStack(APcCItems.material, 1, 21));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean matchesId(EntityItem item, String modId, String name) {
        UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(
            item.getEntityItem()
                .getItem());
        return (identifier.modId.equals(modId) && identifier.name.equals(name));
    }

}
