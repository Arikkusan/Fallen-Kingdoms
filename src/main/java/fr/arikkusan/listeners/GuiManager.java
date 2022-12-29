/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.Menus.Teams.TeamMenu;
import fr.arikkusan.main;
import fr.arikkusan.tools.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack current = e.getCurrentItem();

        if (e.getCurrentItem() == null) return;

        main.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> inv.getName().equalsIgnoreCase(menu.name()) || inv.getName().contains("Fallen Kingdoms - "))
                .forEach(
                        menu -> {
                            if (p.getItemOnCursor() != null) {
                                p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
                            }

                            menu.onClick(p, inv, current, e.getSlot());
                            e.setCancelled(true);
                        }
                );

    }

    public void addMenu(GuiBuilder m) {
        main.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player p, Class<? extends GuiBuilder> gClass) {

        if (!main.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = main.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(p, inv);

        p.openInventory(inv);

    }

    public void open(Player p, Class<TeamMenu> gClass, FK_Team team) {

        if (!main.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = main.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), "Fallen Kingdoms - " + team.getTeamName());
        menu.contents(p, inv, team);

        p.openInventory(inv);
    }
}
