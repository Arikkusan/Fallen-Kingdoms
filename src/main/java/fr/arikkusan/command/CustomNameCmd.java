/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CustomNameCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            ((Player) sender).setCustomName(args[0]);
            sender.sendMessage(
                    ChatColor.GOLD +
                            "Votre surnom a été défini en tant que " +
                            ChatColor.GOLD +
                            ((Player) sender).getCustomName()
            );
        }
        else
            sender.sendMessage(
                    ChatColor.RED +
                    "Pour obtenir un nom custom utilise la commande /CustomName <nomCustom>"
            );

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletion = new ArrayList<>();
        tabCompletion.add(((Player) sender).getDisplayName());
        return tabCompletion;
    }
}
