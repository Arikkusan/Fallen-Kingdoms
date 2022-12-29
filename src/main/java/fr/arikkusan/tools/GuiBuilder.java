/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 */

package fr.arikkusan.tools;

import fr.arikkusan.FKClasses.FK_Team;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

// Interface from "Alexis Dalle - Creation d'un plugin spigot 1.8 #23"

public interface GuiBuilder {
    String name();
    int getSize();
    void contents(Player p, Inventory inv);
    void contents(Player p, Inventory inv, FK_Team team);
    void onClick(Player p, Inventory inv, ItemStack current, int slot);
}
