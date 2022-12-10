package fr.arikkusan.FKClasses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FK_Game {

    private String FkTitle;
    // private Scoreboard scoreboard;
    // private ArrayList<FKEvent> gameEvents;
    private boolean started;
    private boolean paused;
    private boolean finished;

    private FKList fkList;

    public FK_Game() {
        this.FkTitle = "Fallen Kingdoms";
        this.fkList = new FKList();
    }

    public String getFkTitle() {
        return FkTitle;
    }

    public void setFkTitle(@NonNull String fkTitle) {
        FkTitle = fkTitle;
    }

    public FKList getFkList() {
        return fkList;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void startGame() {

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setTime(0);


        for (Player p : players) {

            FkTeam team = fkList.searchTeam(p);

            if (team != null) {

                p.closeInventory();
                p.getInventory().clear();
                p.setDisplayName(p.getCustomName());
                p.setFoodLevel(20);
                p.setSaturation(20);
                p.setHealth(20);
                p.setLevel(0);
                p.sendTitle(
                        ChatColor.GOLD + "Début du Fallen Kingdoms !",
                        ChatColor.GOLD + "Démarrage en cours...",
                        10,
                        40,
                        10
                );
                p.teleport(team.getCenterBase());

            }

        }


    }

    public void pauseGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    public void resumeGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
    }

    public void stopGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }
}
