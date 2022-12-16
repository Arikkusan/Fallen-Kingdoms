package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.Collection;
import java.util.Objects;

public class ChatJoinQuitListener implements Listener {

    private final FK_Game game;
    private final FK_List teams;
    private final Plugin plugin;
    private int taskID;

    public ChatJoinQuitListener(Plugin plugin, FK_Game game, FK_List teams) {
        this.plugin = plugin;
        this.game = game;
        this.teams = teams;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        start(p);

        FK_Team team = teams.searchTeam(p);

        if (team != null) {
            e.setJoinMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            ChatColor.valueOf(String.valueOf(team.getTeamColor())) +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            " a rejoint la partie."
            );
        } else {
            e.setJoinMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            " a rejoint la partie."
            );
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        FK_Team team = teams.searchTeam(p);

        if (team != null) {
            e.setQuitMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            ChatColor.valueOf(String.valueOf(team.getTeamColor())) +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            " a quitté la partie."
            );
        } else {
            e.setQuitMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            " a quitté la partie."
            );
        }

        FK_Board board = new FK_Board(p.getUniqueId());

        if (board.hasID())
            board.stop();

    }

    public void start(Player p) {

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                plugin,
                new Runnable() {

                    FK_Board board = new FK_Board(p.getUniqueId());

                    @Override
                    public void run() {
                        if (!board.hasID())
                            board.setId(taskID);
                        loadSb(p);
                    }
                },
                0,
                5L
        );

    }

    @EventHandler
    public void chatMessages(AsyncPlayerChatEvent e) {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        FK_Team t = teams.searchTeam(e.getPlayer());
        e.setCancelled(true);

        for (Player p : players) {
            if (t != null) {
                p.sendMessage(
                        ChatColor.GOLD + "" +
                                ChatColor.valueOf(String.valueOf(t.getTeamColor())) + "[" +
                                e.getPlayer().getCustomName() + "] - " +
                                ChatColor.YELLOW +
                                e.getMessage()
                );
            } else {
                p.sendMessage(
                        ChatColor.GOLD +
                                "[" +
                                e.getPlayer().getCustomName() + "] - " +
                                ChatColor.YELLOW +
                                e.getMessage()
                );
            }
        }

    }

    public void loadSb(Player p) {
        Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = sb.registerNewObjective(
                "fkName",
                "dummy",
                ChatColor.RED +
                        "" +
                        ChatColor.BOLD +
                        game.getFkTitle()
        );


        // tasks
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective
                .getScore(ChatColor.YELLOW + " ")
                .setScore(15);
        objective
                .getScore(ChatColor.YELLOW + "Events :")
                .setScore(14);
        objective
                .getScore(getColorEvent(game.getEvents().get(0)) + " - Jour " + game.getEvents().get(0).getStartDate() + " : " + game.getEvents().get(0).getName())
                .setScore(13);
        objective
                .getScore(getColorEvent(game.getEvents().get(1)) + " - Jour " + game.getEvents().get(1).getStartDate() + " : " + game.getEvents().get(1).getName())
                .setScore(12);
        objective
                .getScore(getColorEvent(game.getEvents().get(2)) + " - Jour " + game.getEvents().get(2).getStartDate() + " : " + game.getEvents().get(2).getName())
                .setScore(11);
        objective
                .getScore(getColorEvent(game.getEvents().get(3)) + " - 3V3 : " + game.getEvents().get(3).getName())
                .setScore(10);
        objective
                .getScore(ChatColor.YELLOW + "  ")
                .setScore(9);
        objective
                .getScore(ChatColor.RED + "Kills : " + p.getStatistic(Statistic.PLAYER_KILLS))
                .setScore(8);
        objective
                .getScore(ChatColor.YELLOW + "   ")
                .setScore(7);
        objective
                .getScore(ChatColor.RED + "Morts : " + p.getStatistic(Statistic.DEATHS))
                .setScore(6);
        objective
                .getScore(ChatColor.YELLOW + "    ")
                .setScore(5);

        objective
                .getScore(
                        ChatColor.YELLOW + game.getDate().getGameDuration() +
                                " <-> " +
                                ChatColor.YELLOW + "JOUR " + game.getDate().getDay()
                ).setScore(1);


        p.setScoreboard(sb);

    }

    private ChatColor getColorEvent(FK_Event event) {
        return event.isActivated() ? ChatColor.GREEN : ChatColor.MAGIC;
    }


}
