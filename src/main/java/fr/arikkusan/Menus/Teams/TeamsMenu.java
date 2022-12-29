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
import fr.arikkusan.Menus.MainMenu;
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
import java.util.List;

public class TeamsMenu implements GuiBuilder {

    private final FK_Game game;

    public TeamsMenu(FK_Game game) {
        this.game = game;
    }

    @Override
    public String name() {
        return "Fallen Kingdoms - Teams";
    }

    @Override
    public int getSize() {
        return 3 * 9;
    }

    @Override
    public void contents(Player p, Inventory inv) {
        FK_Functions fct = new FK_Functions();

        ItemStack cancelItem = new ItemStack(Material.BARRIER);
        fct.setName(cancelItem, ChatColor.RED + "Cancel");
        fct.setLore(cancelItem, "Go back to main menu");

        ItemStack leaveTeamItem = new ItemStack(Material.IRON_DOOR);
        fct.setName(leaveTeamItem, ChatColor.RED + "Quit team");
        fct.setLore(leaveTeamItem, "Leave teams you're in");

        ItemStack addItem = new Wool(DyeColor.GREEN).toItemStack();
        addItem.setAmount(1);
        fct.setName(addItem, ChatColor.GREEN + "Add a Team");
        fct.setLore(addItem, "Create a new empty team with the color of your choice");

        FK_List teams = game.getFkList();

        inv.setItem(0, cancelItem);

        if (game.getFkList().contain(p)) inv.setItem(4, leaveTeamItem);


        if (teams.size() < 12) inv.setItem(8, addItem);
        if (teams.size() == 0) return;


        int i = 10;

        for (FK_Team team : teams) {

            ItemStack teamItem = new Wool(team.getTeamColor()).toItemStack();
            teamItem.setAmount(1);
            fct.setName(teamItem, ChatColor.valueOf(String.valueOf(team.getTeamColor())) + team.getTeamName());
            fct.setLore(teamItem, getLore(team));

            inv.setItem(i, teamItem);

            if (i == 16) i = 20;
            else i++;

        }


    }

    @Override
    public void contents(Player p, Inventory inv, FK_Team team) {
    }

    private List<String> getLore(FK_Team team) {
        List<String> list = new ArrayList<>();

        if (team.getTeamList().size() == 0) {
            list.add("No one is in this team !");
            return list;
        }

        String color = ChatColor.valueOf(String.valueOf(team.getTeamColor())) + "";
        list.add(color + "Members :");

        for (Player p : team.getTeamList()) {
            list.add(color + "  - " + p.getDisplayName());
        }

        return list;
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
            main.getInstance().getGuiManager().open(p, MainMenu.class);
            return;

        }

        if (val.equalsIgnoreCase("quit team")) {
            for (FK_Team t : game.getFkList())
                t.removeMember(p);
            p.closeInventory();
            p.sendMessage(ChatColor.GOLD + "You're sucessfully alone !");
            return;

        }

        if (val.equalsIgnoreCase("add a team")) {
            p.closeInventory();
            main.getInstance().getGuiManager().open(p, NewTeamMenu.class);
            return;

        }

        for (FK_Team team : game.getFkList()) {
            if (team.getTeamName().equalsIgnoreCase(val)) {
                p.closeInventory();
                main.getInstance().getGuiManager().open(p, TeamMenu.class, team);
                break;
            }
        }

    }
}
