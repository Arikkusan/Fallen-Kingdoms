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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobKillListener implements Listener {

    @EventHandler
    public void mobKilled(EntityDeathEvent e) {

        switch (e.getEntityType()) {
            case COW:
                e.getDrops().clear();
                e.getDrops().add(
                        new ItemStack(
                                Material.COOKED_BEEF,
                                (int) (1 + (Math.random() * 2))
                        )
                );
                e.getDrops().add(
                        new ItemStack(
                                Material.LEATHER,
                                (int) (1 + (Math.random() * 3))
                        )
                );
                break;

            case CREEPER:
                e.getDrops().clear();
                e.getDrops().add(
                        new ItemStack(
                                Material.SULPHUR,               // Here gunpowder is called SULPHUR (in 1.8.8 at least)
                                (int) (1 + (Math.random() * 3))
                        )
                );

                // tnt logic
                int tntCount;
                if (((Creeper) e.getEntity()).isPowered())
                    tntCount = 1;
                else
                    tntCount = (Math.random()) < 0.15 ? 1 : 0;


                e.getDrops().add(
                        new ItemStack(
                                Material.TNT,
                                tntCount
                        )
                );
                break;

            case PIG:
                e.getDrops().clear();
                e.getDrops().add(
                        new ItemStack(
                                Material.GRILLED_PORK,
                                (int) (1 + (Math.random() * 2))
                        )
                );
                e.getDrops().add(
                        new ItemStack(
                                Material.LEATHER,
                                (int) (Math.random() * 2)
                        )
                );
                break;

            case CHICKEN:
                e.getDrops().clear();
                e.getDrops().add(
                        new ItemStack(
                                Material.COOKED_CHICKEN,
                                (int) (1 + (Math.random() * 2))
                        )
                );
                e.getDrops().add(
                        new ItemStack(
                                Material.FEATHER,
                                (int) (1 + (Math.random() * 3))
                        )
                );
                break;

            case SHEEP:
                e.getDrops().clear();
                e.getDrops().add(
                        new ItemStack(
                                Material.COOKED_MUTTON,
                                (int) (1 + (Math.random() * 4))
                        )
                );
                e.getDrops().add(
                        new ItemStack(
                                Material.WOOL,
                                (int) (Math.random() * 3)
                        )
                );
                break;


        }

    }

}
