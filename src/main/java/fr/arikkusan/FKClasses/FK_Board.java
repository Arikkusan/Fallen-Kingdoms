/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.FKClasses;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FK_Board {

    private static Map<UUID, Integer> TASKS = new HashMap<>();
    private final UUID uuid;


    public FK_Board(UUID uuid) {
        this.uuid = uuid;
    }

    public void setId(int ID) {
        TASKS.put(uuid, ID);
    }

    public int getId() {
        return TASKS.get(uuid);
    }

    public boolean hasID() {
        return TASKS.containsKey(uuid);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(TASKS.get(uuid));
        TASKS.remove(uuid);
    }
}
