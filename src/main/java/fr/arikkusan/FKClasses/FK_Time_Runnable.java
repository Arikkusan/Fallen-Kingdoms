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

public class FK_Time_Runnable {

    private static Map<String, Integer> TASKS = new HashMap<>();
    private final String uid;


    public FK_Time_Runnable(String uid) {
        this.uid = uid;
    }

    public void setId(int ID) {
        TASKS.put(uid, ID);
    }

    public int getId() {
        return TASKS.get(uid);
    }

    public boolean hasID() {
        return TASKS.containsKey(uid);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(TASKS.get(uid));
        TASKS.remove(uid);
    }
}

