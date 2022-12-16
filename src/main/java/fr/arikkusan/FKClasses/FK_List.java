package fr.arikkusan.FKClasses;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FK_List extends ArrayList<FK_Team> {


    private FK_Team searchTeam(String name) {

        for (FK_Team t : this) {
            if (t.getTeamName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;

    }

    public FK_Team searchTeam(Player player) {

        for (FK_Team t : this)
            for (Player p : t.getTeamList())
                if (p.getUniqueId().equals(player.getUniqueId())) return t;

        return null;

    }

    public boolean contain(Player p) {

        for (FK_Team t : this)
            for (Player player : t.getTeamList())
                if (player.getUniqueId().equals(p.getUniqueId())) return true;

        return false;
    }

    public void add(Player player, String TeamName) {

        FK_Team team = searchTeam(TeamName);

        if (team != null) {
            player.setPlayerListName(ChatColor.valueOf(String.valueOf(team.getTeamColor())) + "[" + team.getTeamName() + "] " + player.getCustomName());
            team.addMember(player);
        }
        else
            player.sendMessage(
                ChatColor.RED + "" +
                        ChatColor.BOLD + "(!) " +
                        ChatColor.RED +
                        "Vous ne pouvez pas rejoindre une équipe inexistante, veuillez réessayer avec un nom valide"
            );

    }

    public boolean removeTeam(String teamName) {
        FK_Team team = searchTeam(teamName);

        if (team != null) {

            for (Player player: team.getTeamList()) {
                player.setPlayerListName(ChatColor.GOLD + "[Punk à chien] " + player.getCustomName());
            }

            this.remove(team);
            return true;
        }

        return false;
    }
}
