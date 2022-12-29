/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.Menus.Teams;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.main;
import fr.arikkusan.tools.GuiBuilder;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.util.ArrayList;

public class NewTeamMenu implements GuiBuilder {
    private final FK_Game game;
    private final ArrayList<DyeColor> colors;

    public NewTeamMenu(FK_Game game) {
        this.game = game;
        this.colors = new ArrayList<>();
        colors.add(DyeColor.BLUE);
        colors.add(DyeColor.WHITE);
        colors.add(DyeColor.RED);
        colors.add(DyeColor.GREEN);
        colors.add(DyeColor.BLACK);
        colors.add(DyeColor.GRAY);
        colors.add(DyeColor.YELLOW);
        // colors.add(DyeColor.ORANGE);
        // colors.add(DyeColor.LIME);
        // colors.add(DyeColor.PURPLE);
        // colors.add(DyeColor.LIGHT_BLUE);
        // colors.add(DyeColor.BROWN);
        // colors.add(DyeColor.CYAN);
        // colors.add(DyeColor.MAGENTA);
        // colors.add(DyeColor.SILVER);
        // colors.add(DyeColor.PINK);
    }

    @Override
    public String name() {
        return "Fallen Kingdoms - Add a Team";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player p, Inventory inv) {
        FK_Functions fct = new FK_Functions();

        ItemStack cancelItem = new ItemStack(Material.BARRIER);
        fct.setName(cancelItem, ChatColor.RED + "Cancel");
        fct.setLore(cancelItem, "Go back to main menu");

        inv.setItem(0, cancelItem);

        int i = 2;

        for (DyeColor color : colors) {

            ItemStack teamColor = new Wool(color).toItemStack();
            teamColor.setAmount(1);
            fct.setName(teamColor, ChatColor.GOLD + color.toString());
            fct.setLore(teamColor, "You're new team Color");

            inv.setItem(i, teamColor);

            i++;

        }

    }

    @Override
    public void contents(Player p, Inventory inv, FK_Team team) {
    }

    @Override
    public void onClick(Player p, Inventory inv, ItemStack current, int slot) {

        if (!inv.getName().equalsIgnoreCase(name())) return;
        if (current == null) return;
        if (current.getItemMeta() == null) return;

        String val = ChatColor.stripColor(current.getItemMeta().getDisplayName().toLowerCase());

        if (val == null) return;

        if (val.equalsIgnoreCase("cancel")) {
            p.closeInventory();
            main.getInstance().getGuiManager().open(p, TeamsMenu.class);
            return;
        }

        ArrayList<String> list = new ArrayList<>();
        list.add("BLUE");
        list.add("WHITE");
        list.add("RED");
        list.add("GREEN");
        list.add("BLACK");
        list.add("GRAY");
        list.add("YELLOW");

        val = val.toUpperCase();

        if (list.contains(val)) {

            int i = game.getFkList().size();
            i++;

            p.closeInventory();

            FK_Team team = new FK_Team(i + "_Team_" + val, DyeColor.valueOf(val));
            FK_List teams = game.getFkList();
            teams.add(team);



        }

    }
}
