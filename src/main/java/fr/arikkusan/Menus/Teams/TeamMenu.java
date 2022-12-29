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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class TeamMenu implements GuiBuilder {

    private final FK_Game game;
    private FK_Team team;

    public TeamMenu(FK_Game game) {
        this.game = game;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player p, Inventory inv) {
    }

    @Override
    public void contents(Player p, Inventory inv, FK_Team team) {
        this.team = team;
        FK_Functions fct = new FK_Functions();

        ItemStack cancelItem = new ItemStack(Material.BARRIER);
        fct.setName(cancelItem, ChatColor.RED + "Cancel");
        fct.setLore(cancelItem, "Go back to Teams menu");

        ItemStack deleteItem = new Wool(DyeColor.RED).toItemStack();
        deleteItem.setAmount(1);
        fct.setName(deleteItem, ChatColor.RED + "Delete this Team");
        fct.setLore(deleteItem, "Delete the team " + team.getTeamName());

        ItemStack joinItem = new Wool(DyeColor.GREEN).toItemStack();
        joinItem.setAmount(1);
        fct.setName(joinItem, ChatColor.GREEN + "Join this Team");
        fct.setLore(joinItem, "Join the team " + team.getTeamName());

        inv.setItem(2, cancelItem);
        inv.setItem(4, deleteItem);
        inv.setItem(6, joinItem);

    }

    @Override
    public void onClick(Player p, Inventory inv, ItemStack current, int slot) {

        switch (ChatColor.stripColor(current.getItemMeta().getDisplayName()).toLowerCase()) {

            case "cancel":
                main.getInstance().getGuiManager().open(p, TeamsMenu.class);
                break;

            case "delete this team":
                if (!game.getFkList().removeTeam(team.getTeamName())) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                    break;
                }

                p.sendMessage(
                        ChatColor.DARK_GRAY + "" +
                                ChatColor.BOLD +
                                "La team " + team.getTeamName() + " a été supprimée avec succès"
                );
                main.getInstance().getGuiManager().open(p, TeamsMenu.class);
                break;

            case "join this team":
                p.closeInventory();
                FK_List teams = game.getFkList();
                if (teams.contain(p))
                    teams.searchTeam(p).removeMember(p);
                teams.add(p, team.getTeamName());
                break;


        }


    }
}
