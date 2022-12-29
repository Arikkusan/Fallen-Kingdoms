/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.utils.FK_Functions;
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

        FK_Functions fkFunctions = new FK_Functions(game);

        Player p = (Player) sender;

        if (args.length == 2)
            fkFunctions.setCustomName(args[0], args[1]);

        else if (args.length == 1)
            fkFunctions.setCustomName(p, args[0]);
        else
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
