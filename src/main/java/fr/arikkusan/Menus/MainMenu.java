/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.Menus;

import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.Menus.Teams.GameMenu;
import fr.arikkusan.Menus.Teams.TeamsMenu;
import fr.arikkusan.main;
import fr.arikkusan.tools.GuiBuilder;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenu implements GuiBuilder {
    @Override
    public String name() {
        return "Fallen Kingdoms";
    }

    @Override
    public int getSize() {
        return 3 * 9;
    }

    @Override
    public void contents(Player p, Inventory inv) {

        FK_Functions fct = new FK_Functions();


        ItemStack gameItem = new ItemStack(Material.BEACON);
        ItemStack teamItem = new ItemStack(Material.BANNER);
        ItemStack eventsItem = new ItemStack(Material.FURNACE);
        ItemStack cancelItem = new ItemStack(Material.BARRIER);

        fct.setName(gameItem, ChatColor.GOLD + "Game");
        fct.setLore(gameItem, "Allow Users to interract with the game");
        fct.setName(teamItem, ChatColor.GREEN + "Teams");
        fct.setLore(teamItem, "Allow Users to interract with the teams");
        fct.setName(eventsItem, ChatColor.AQUA + "Events");
        fct.setLore(eventsItem, "Allow User to add / remove or modify Events");
        fct.setName(cancelItem, ChatColor.RED + "Leave");
        fct.setLore(cancelItem, "Leave menu");


        inv.setItem(10, gameItem); // Game (Start, stop, ...)
        inv.setItem(12, teamItem); // Teams
        inv.setItem(14, eventsItem); // Events
        inv.setItem(16, cancelItem); // Cancel
    }

    @Override
    public void contents(Player p, Inventory inv, FK_Team team) {}

    @Override
    public void onClick(Player p, Inventory inv, ItemStack current, int slot) {

        if (!inv.getName().equalsIgnoreCase(name())) return;

        switch (current.getData().getItemType()) {

            case BEACON:
                p.closeInventory();
                main.getInstance().getGuiManager().open(p, GameMenu.class);
                break;
            case BANNER:
                p.closeInventory();
                if (inv.getName().equalsIgnoreCase(name()))
                main.getInstance().getGuiManager().open(p, TeamsMenu.class);
                break;
            case FURNACE:
                p.closeInventory();
                break;
            case BARRIER:
                p.closeInventory();
                break;
            default:
                break;

        }

    }
}
