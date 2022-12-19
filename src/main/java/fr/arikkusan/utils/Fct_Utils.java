/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.utils;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Fct_Utils {


    public void setListName(FK_Game game, Player p) {

        if (game.getFkList().size() > 0) {

            if (game.getFkList().contain(p)) {

                FK_Team team = game.getFkList().searchTeam(p);

                if (team != null)
                    p.setPlayerListName(ChatColor.valueOf(String.valueOf(team.getTeamColor())) + "[" + team.getTeamName() + "] " + p.getCustomName());
                else
                    p.setPlayerListName(ChatColor.GRAY + "" + ChatColor.BOLD + "[Maitre de partie] " + p.getDisplayName());

            }

        }
    }

}
