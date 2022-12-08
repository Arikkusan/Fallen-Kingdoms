package fr.arikkusan.FKClasses;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FkTeam {

    private String teamName;
    private DyeColor teamColor;
    private ArrayList<Player> teamList;

    private Location centerBase;
    private ArrayList<Location> basePlacement;

    public FkTeam(String teamName, DyeColor teamColor) {
        this.teamName = teamName;
        this.teamColor = teamColor;
        this.teamList = new ArrayList<>();
        this.centerBase = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    }

    public FkTeam(String teamName) {
        this.teamName = teamName;
        this.teamColor = DyeColor.BLUE;
        this.teamList = new ArrayList<>();
        this.centerBase = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public DyeColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(DyeColor teamColor) {
        this.teamColor = teamColor;
    }

    public ArrayList<Player> getTeamList() {
        return teamList;
    }

    public void addMember(Player p) {
        if (!teamList.contains(p))
            this.teamList.add(p);
        else {
            p.sendMessage(ChatColor.GREEN + "Vous êtes déjà présent dans la team " + ChatColor.BOLD + this.teamName);
        }
    }

    public void removeMember(Player p) {

        if (teamList.contains(p)) {
            this.teamList.remove(p);
        }

    }

    public Location getCenterBase() {
        return centerBase;
    }

    public void setCenterBase(Location centerBase) {
        this.centerBase = centerBase;
    }

    public ArrayList<Location> getBasePlacement() {
        return basePlacement;
    }

    public void setBasePlacement(ArrayList<Location> basePlacement) {
        this.basePlacement = basePlacement;
    }
}

