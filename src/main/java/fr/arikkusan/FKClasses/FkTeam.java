package fr.arikkusan.FKClasses;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

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
        this.basePlacement = new ArrayList<>();
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), 0, 0, 0));
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), 0, 0, 0));
    }

    public FkTeam(String teamName) {
        this.teamName = teamName;
        this.teamColor = DyeColor.BLUE;
        this.teamList = new ArrayList<>();
        this.centerBase = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        this.basePlacement = new ArrayList<>();
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), 0, 0, 0));
        this.basePlacement.add(new Location(Bukkit.getWorld("world"), 0, 0, 0));
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

    public boolean inBaseArea(Location block) {

        int x, y, z;
        int x1, y1, z1;
        int x2, y2, z2;

        // coordonnées du block paramètre
        x = block.getBlockX();
        y = block.getBlockY();
        z = block.getBlockZ();

        // coordonnées de la location de la base
        x1 = basePlacement.get(0).getBlockX();
        x2 = basePlacement.get(1).getBlockX();
        y1 = basePlacement.get(0).getBlockX();
        y2 = basePlacement.get(1).getBlockX();

        // vérification hors hauteur
        if (x1 < x2) {

            return contionBlock(x, y, x2, y1, x1, y2);

        } else {

            return contionBlock(x, y, x1, y1, x2, y2);

        }
    }

    private boolean contionBlock(int x, int y, int x1, int y1, int x2, int y2) {
        if (x >= x2 && x <= x1) {

            if (y1 < y2) {

                if (y >= y1 && y <= y2) {

                    return true;

                }
            } else {

                if (y >= y2 && y <= y1) {

                    return true;

                }

            }



        }
        return false;
    }
}

