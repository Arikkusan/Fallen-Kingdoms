/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.Items;

import fr.arikkusan.utils.FK_Functions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class ItemsManager {

    private static ItemStack menu;
    public static ItemStack mainMenu;
    public static ItemStack playerMenu;
    public static ItemStack baseCompass;
    private static ArrayList<String> lore;

    public static void init() {
        FK_Functions f = new FK_Functions();

        initCompass(f);
        initMenu(f);
    }

    private static void initCompass(FK_Functions f) {
        menu = new ItemStack(Material.COMPASS);
        f.setName(menu, ChatColor.GOLD + "Base");
        f.setLore(menu, ChatColor.GOLD + "Pointing to your base !");

        ItemMeta meta = menu.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        menu.setItemMeta(meta);

        baseCompass = menu;

    }

    private static void initMenu(FK_Functions f) {
        lore = new ArrayList<>();

        menu = new ItemStack(Material.COMPASS);
        f.setName(menu, ChatColor.GOLD + "MENU");
        lore.add(ChatColor.GOLD + "Item used to access");
        lore.add(ChatColor.GOLD + "menu of the fallen Kingdoms");
        lore.add(ChatColor.GOLD + "plugin !");
        lore.add(ChatColor.GOLD + "              ");
        lore.add(ChatColor.GOLD + "by Arikkusan");
        f.setLore(menu, lore);

        ItemMeta meta = menu.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        menu.setItemMeta(meta);

        mainMenu = menu;

    }

    private static void initPlayerMenu(FK_Functions f) {
        lore = new ArrayList<>();

        menu = new ItemStack(Material.SKULL_ITEM);
        f.setName(menu, ChatColor.GOLD + "Player Menu");
        lore.add(ChatColor.GOLD + "Item used to control");
        lore.add(ChatColor.GOLD + "players of the game and");
        lore.add(ChatColor.GOLD + "to teleport to them !");
        lore.add(ChatColor.GOLD + "               ");
        lore.add(ChatColor.GOLD + "by Arikkusan ");
        f.setLore(menu, lore);

        ItemMeta meta = menu.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        menu.setItemMeta(meta);

    }

}
