package fr.arikkusan.FKClasses;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Objects;

public class FK_Game {

    private String FkTitle;
    // private Scoreboard scoreboard;
    // private ArrayList<FKEvent> gameEvents;
    private boolean started;
    private boolean paused;
    private boolean finished;

    private FK_List fkList;
    private ScoreboardManager gameScoreboard = Bukkit.getScoreboardManager();
    ;

    public FK_Game() {
        this.FkTitle = "Fallen Kingdoms";
        this.fkList = new FK_List();
    }

    public String getFkTitle() {
        return FkTitle;
    }

    public void setFkTitle(@NonNull String fkTitle) {
        FkTitle = fkTitle;
    }

    public FK_List getFkList() {
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

            FK_Team team = fkList.searchTeam(p);
            p.setGameMode(GameMode.ADVENTURE);
            p.closeInventory();
            p.getInventory().clear();
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

            if (team != null) {
                p.teleport(team.getCenterBase());
                p.setGameMode(GameMode.SURVIVAL);
            } else {
                p.setGameMode(GameMode.SPECTATOR);
            }

        }

        this.setStarted(true);


    }

    public void pauseGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.setPaused(true);

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            FK_Team team = fkList.searchTeam(p);

            if (team != null) {
                p.setGameMode(GameMode.SPECTATOR);

            }

        }

    }

    public void resumeGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        this.setPaused(false);


        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            FK_Team team = fkList.searchTeam(p);
            if (team != null)
                p.setGameMode(GameMode.SURVIVAL);

        }
    }

    public void stopGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);


        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            FK_Team team = fkList.searchTeam(p);

        }
    }

    public ScoreboardManager getGameScoreboard() {
        loadSb(gameScoreboard);
        return gameScoreboard;

    }

    public void loadSb(ScoreboardManager gameScoreboard) {
        Scoreboard sb = gameScoreboard.getNewScoreboard();
        Objective objective = sb.registerNewObjective(
                "fkName",
                "dummy",
                ChatColor.RED +
                        "" +
                        ChatColor.BOLD +
                        getFkTitle()
        );

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score emptyLine = objective.getScore("");

        // sheduler for tasks



    }
}
