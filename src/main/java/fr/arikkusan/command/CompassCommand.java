/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.Items.ItemsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompassCommand implements CommandExecutor {

    private final FK_Game game;

    public CompassCommand(FK_Game game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!game.isStarted()) return true;
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;
        FK_Team team = game.getFkList().searchTeam(p);

        if (team == null) return true;

        if (p.getInventory().contains(ItemsManager.baseCompass)) {
            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
            return true;
        }

        p.getInventory().addItem(ItemsManager.baseCompass);
        p.setCompassTarget(team.getCenterBase());

        p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Vous avez reçu une boussole pointant vers votre base !");
        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1F, 1F);


        return true;
    }
}
