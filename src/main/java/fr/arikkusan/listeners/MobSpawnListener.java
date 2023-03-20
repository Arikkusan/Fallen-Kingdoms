/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Locale;

public class MobSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {

        LivingEntity mob = e.getEntity();

        if (mob.getType() == EntityType.CREEPER) {

            boolean empowered = Math.random() < 0.2;

            if (empowered) {

                e.setCancelled(true);
                Creeper creeper = (Creeper) (e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CREEPER));
                creeper.setPowered(true);

            }


        }

        if (mob.getType() == EntityType.ZOMBIE) {

            boolean toCreep = Math.random() < 0.4;

            if (toCreep) {

                e.setCancelled(true);
                Creeper creeper = (Creeper) (e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CREEPER));
                creeper.setPowered(Math.random() < 0.2);

            }


        }


    }

}
