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

public class TeamNameCommand implements CommandExecutor, TabCompleter {

    private final FK_Game game;

    public TeamNameCommand(FK_Game game) {
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        FK_List teams = game.getFkList();
        FK_Team team;

        if (args.length >= 2) {
            team = teams.searchTeam(args[1]);

            if (team == null) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) Warning, null team choosen");
                return true;
            }

            team.setTeamName(args[0]);
            sender.sendMessage(ChatColor.GREEN + "La team " + team.getTeamColor() + " à bien été renommée");
            return true;

        }

        if (args.length == 1) {
            team = teams.searchTeam((Player) sender);

            if (team == null) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) Warning, null team choosen");
                return true;
            }

            team.setTeamName(args[0]);
            sender.sendMessage(ChatColor.GREEN + "La team " + team.getTeamColor() + " à bien été renommée");
            return true;

        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();


        if (args.length == 1) {
            list.add("CustomTeamName");
            return list;
        }

        if (args.length == 2) {
            for (FK_Team team: game.getFkList())
                list.add(team.getTeamName());
            return list;
        }

        return new ArrayList<>();
    }
}
