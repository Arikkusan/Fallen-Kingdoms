package fr.arikkusan.FKClasses;

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

    public FkTeam searchTeam(Player p) {

        for (FkTeam t : this)
            if (t.getTeamList().contains(p))
                return t;


        return null;

    }

    public boolean contain(Player p) {

        for (FkTeam t : this)
            if (t.getTeamList().contains(p)) return true;

        return false;
    }

    public void add(Player player, String TeamName) {

        FkTeam team = searchTeam(TeamName);

        if (team != null)
            team.addMember(player);


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
