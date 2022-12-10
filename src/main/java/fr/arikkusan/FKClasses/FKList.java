package fr.arikkusan.FKClasses;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FKList extends ArrayList<FkTeam> {


    private FkTeam searchTeam(String name) {

        for (FkTeam t : this) {
            if (t.getTeamName().equalsIgnoreCase(name)) {
                return t;
            }
        }

        return null;

    }

    public FkTeam searchTeam(Player player) {

        for (FkTeam t : this)
            for (Player p : t.getTeamList())
                if (p.getUniqueId().equals(player.getUniqueId())) return t;

        return null;

    }

    public boolean contain(Player p) {

        for (FkTeam t : this)
            for (Player player : t.getTeamList())
                if (player.getUniqueId().equals(p.getUniqueId())) return true;

        return false;
    }

    public void add(Player player, String TeamName) {

        FkTeam team = searchTeam(TeamName);

        if (team != null)
            team.addMember(player);
        else
            player.sendMessage(
                ChatColor.RED + "" +
                        ChatColor.BOLD + "(!) " +
                        ChatColor.RED +
                        "Vous ne pouvez pas rejoindre une équipe inexistante, veuillez réessayer avec un nom valide");

    }

    public boolean removeTeam(String teamName) {
        FkTeam team = searchTeam(teamName);

        if (team != null) {
            this.remove(team);
            return true;
        }

        return false;
    }
}
