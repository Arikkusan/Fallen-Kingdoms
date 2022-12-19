/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobKillListener implements Listener {

    @EventHandler
    public void mobKilled(EntityDeathEvent e) {

        if (e.getEntityType() == EntityType.CREEPER) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(
                            Material.GUNPOWDER,
                            (int) (1 + (Math.random() * 3))
                    )
            );
        }

        if (e.getEntityType() == EntityType.COW) {
            e.getDrops().add(new ItemStack(
                            Material.COOKED_BEEF,
                            (int) (1 + (Math.random() * 2))
                    )
            );
            e.getDrops().add(new ItemStack(
                            Material.LEATHER,
                            (int) (1 + (Math.random() * 3))
                    )
            );
        }

        if (e.getEntityType() == EntityType.PIG) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(
                            Material.COOKED_PORKCHOP,
                            (int) (1 + (Math.random() * 2))
                    )
            );
            e.getDrops().add(new ItemStack(
                            Material.LEATHER,
                            (int) (Math.random() * 3)
                    )
            );
        }

        if (e.getEntityType() == EntityType.CHICKEN) {
            e.getDrops().clear();
            e.getDrops().add(new ItemStack(
                            Material.COOKED_CHICKEN,
                            (int) (1 + (Math.random() * 2))
                    )
            );
            e.getDrops().add(new ItemStack(
                            Material.FEATHER,
                            (int) (1 + (Math.random() * 3))
                    )
            );
        }

    }

}
