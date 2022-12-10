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
        this.centerBase = new Location(Bukkit.getWorld("world"), 0, 83, 0);
        this.basePlacement = new ArrayList<>();
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), centerBase.getBlockX() - 16, centerBase.getBlockY(), centerBase.getBlockZ() - 16));
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), centerBase.getBlockX() + 16, centerBase.getBlockY(), centerBase.getBlockZ() + 16));
    }

    public String getTeamName() {
        return teamName;
    }

    public DyeColor getTeamColor() {
        return teamColor;
    }

    public ArrayList<Player> getTeamList() {
        return teamList;
    }

    public void addMember(Player p) {
        if (!teamList.contains(p)) {
            this.teamList.add(p);
            p.sendMessage(ChatColor.GOLD + "Vous avez rejoint la team " + ChatColor.BOLD + "" + ChatColor.valueOf(String.valueOf(this.teamColor)) + this.teamName);
        }
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
        this.basePlacement.set(0, new Location(Bukkit.getWorld("world"), centerBase.getBlockX() - 16, centerBase.getBlockY(), centerBase.getBlockZ() - 16));
        this.basePlacement.set(1, new Location(Bukkit.getWorld("world"), centerBase.getBlockX() + 16, centerBase.getBlockY(), centerBase.getBlockZ() + 16));
    }

    public boolean inBaseArea(Location block) {

        // x équivaut au X de la map
        // z équivaut au Z de la map

        int x, z;

        int x1, z1;
        int x2, z2;

        // coordonnées du block paramètre
        x = block.getBlockX();
        z = block.getBlockZ();

        // coordonnées de la location de la base
        x1 = basePlacement.get(0).getBlockX();
        x2 = basePlacement.get(1).getBlockX();

        z1 = basePlacement.get(0).getBlockZ();
        z2 = basePlacement.get(1).getBlockZ();

        return ((x >= x1 && x <= x2) && (z >= z1 && z <= z2));
    }

}

