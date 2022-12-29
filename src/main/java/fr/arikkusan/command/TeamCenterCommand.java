/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCenterCommand implements CommandExecutor, TabCompleter {

    private final FK_Game game;

    public TeamCenterCommand(FK_Game game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        FK_List teams = game.getFkList();
        FK_Team team;

        if (args.length >= 1) {
            team = teams.searchTeam(args[0]);

            if (team == null) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) Warning, null team choosen");
                return true;
            }

            team.setCenterBase((((Player) sender).getLocation()));
            sender.sendMessage(ChatColor.GREEN + "La team " + team.getTeamName() + " a bien été centré à vos coordonnées");
            return true;

        }

        team = teams.searchTeam((Player) sender);

        if (team == null) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) Warning, null team choosen");
            return true;
        }

        team.setCenterBase((((Player) sender).getLocation()));
        sender.sendMessage(ChatColor.GREEN + "Votre team à bien été centré à vos coordonnées");
        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        for (FK_Team team: game.getFkList())
            list.add(team.getTeamName());

        if (args.length == 1)
            return list;

        return new ArrayList<>();
    }
}
