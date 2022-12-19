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
import fr.arikkusan.utils.Fct_Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomNameCommands implements CommandExecutor, TabCompleter {

    private final FK_Game game;

    public CustomNameCommands(FK_Game game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        if (args.length == 2) {

            Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
            ArrayList<String> playersName = new ArrayList<>();

            for (Player player : players)
                playersName.add(player.getName());

            if (playersName.contains(args[1])) {
                for (Player player : players) {
                    if (player.getName().equalsIgnoreCase(args[1])) {
                        player.setCustomName(args[0]);
                        player.setDisplayName(p.getCustomName());

                        new Fct_Utils().setListName(game, player);

                        p.sendMessage(
                                ChatColor.GOLD +
                                        "Le surnom de " + player.getName() + " a été défini en tant que " +
                                        ChatColor.GOLD +
                                        player.getCustomName()
                        );
                    }
                }

            }



        } else if (args.length == 1) {
            p.setCustomName(args[0]);
            p.setDisplayName(p.getCustomName());
            p.sendMessage(
                    ChatColor.GOLD +
                            "Votre surnom a été défini en tant que " +
                            ChatColor.GOLD +
                            p.getCustomName()
            );

            new Fct_Utils().setListName(game, p);

        } else
            p.sendMessage(
                    ChatColor.RED +
                            "Pour obtenir un nom custom utilise la commande /CustomName <NomCustom>"
            );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletion = new ArrayList<>();

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();

        if (args.length == 1)
            tabCompletion.add("CustomName");
        else
            for (Player p: players)
                tabCompletion.add(p.getName());


        return tabCompletion;
    }
}
