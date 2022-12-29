package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.*;
import fr.arikkusan.Items.ItemsManager;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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

        if (p.getCustomName() == null)
            p.setCustomName(p.getName());

        if (!p.getInventory().contains(ItemsManager.mainMenu) && !game.isStarted())
            p.getInventory().addItem(ItemsManager.mainMenu);

        /*

        if (p.getInventory().contains(ItemsManager.playerMenu) && game.isStarted())
            p.getInventory().addItem(ItemsManager.playerMenu);

        */

        new FK_Functions().setListName(game, p);

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
        if (p.getCustomName() == null)
            p.setCustomName(p.getName());

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
                                t.getTeamName() + "] " + e.getPlayer().getCustomName() + " - " +
                                ChatColor.YELLOW +
                                e.getMessage()
                );
            } else {
                p.sendMessage(
                        ChatColor.GOLD +
                                e.getPlayer().getCustomName() + " - " +
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
                "dummy"
        );

        objective.setDisplayName(
                ChatColor.RED +
                        "" +
                        ChatColor.BOLD +
                        game.getFkTitle()
        );

        // tasks
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective
                .getScore(ChatColor.YELLOW + "Events :")
                .setScore(15);
        objective
                .getScore(getColorEvent(game.getEvents().get(0)) + " - Jour " + game.getEvents().get(0).getStartDate() + " : " + game.getEvents().get(0).getName())
                .setScore(14);
        objective
                .getScore(getColorEvent(game.getEvents().get(1)) + " - Jour " + game.getEvents().get(1).getStartDate() + " : " + game.getEvents().get(1).getName())
                .setScore(13);
        objective
                .getScore(getColorEvent(game.getEvents().get(2)) + " - Jour " + game.getEvents().get(2).getStartDate() + " : " + game.getEvents().get(2).getName())
                .setScore(12);
        objective
                .getScore(getColorEvent(game.getEvents().get(3)) + " - Jour " + game.getEvents().get(2).getStartDate() + " : " + game.getEvents().get(3).getName())
                .setScore(11);
        objective
                .getScore("  ")
                .setScore(10);
        objective
                .getScore(ChatColor.RED + "Kills : " + p.getStatistic(Statistic.PLAYER_KILLS))
                .setScore(9);
        objective
                .getScore(ChatColor.RED + "Morts : " + p.getStatistic(Statistic.DEATHS))
                .setScore(8);
        objective
                .getScore("    ")
                .setScore(7);
        objective
                .getScore(ChatColor.YELLOW + "Temps joué : " + game.getDate().getGameDuration())
                .setScore(6);
        objective
                .getScore("     ")
                .setScore(5);
        objective
                .getScore(ChatColor.YELLOW + "Timer : " + game.getDate().getDayDuration())
                .setScore(4);
        objective
                .getScore(ChatColor.YELLOW + "Jour " + game.getDate().getDay())
                .setScore(3);
        objective
                .getScore("      ")
                .setScore(2);
        objective
                .getScore(getDistanceAndArrow(p))
                .setScore(1);


        p.setScoreboard(sb);

    }

    @EventHandler
    public void deathMessages(PlayerDeathEvent e) {

        Player p = e.getEntity();
        FK_Team team = teams.searchTeam(p);

        if (team != null) {
            e.setDeathMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            ChatColor.valueOf(String.valueOf(team.getTeamColor())) +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            Objects.requireNonNull(e.getDeathMessage()).substring(e.getDeathMessage().indexOf(" "))
            );
        } else {
            e.setDeathMessage(
                    ChatColor.GOLD +
                            "FK - " +
                            p.getCustomName() +
                            ChatColor.GOLD +
                            Objects.requireNonNull(e.getDeathMessage()).substring(e.getDeathMessage().indexOf(" "))
            );
        }

    }

    private ChatColor getColorEvent(FK_Event event) {
        return event.isActivated() ? ChatColor.GREEN : ChatColor.MAGIC;
    }

    private String getDistanceAndArrow(Player p) {
        // tell the distance to our base / if we're in our base

        FK_Team team = teams.contain(p) ? teams.searchTeam(p) : null;

        if (team == null) return ChatColor.GRAY + "SPECTATEUR";

        String valueToReturn;

        if (p.getLocation().getWorld() == Bukkit.getWorld("world")) {

            if (team.inBaseArea(p.getLocation()))
                valueToReturn = "Vous êtes dans la base";
            else
                valueToReturn = ChatColor.valueOf(String.valueOf(team.getTeamColor())) + "" + ChatColor.BOLD + distancePoints(team.getCenterBase(), p.getLocation()) + " Blocks";

        } else
            valueToReturn = ChatColor.MAGIC + "On m'appelle l'Ovni poupoupoupou";

        return valueToReturn;

    }

    private int distancePoints(Location location1, Location location2) {

        // return the distance between 2 location in nb of blocks

        int x1 = location1.getBlockX();
        int z1 = location1.getBlockZ();

        int x2 = location2.getBlockX();
        int z2 = location2.getBlockZ();

        int distanceX = dist(x1, x2),
                distanceZ = dist(z1, z2);

        return (int) (Math.sqrt(distanceX * distanceX + distanceZ * distanceZ) - 16);
    }

    private int dist(int a, int b) {
        if (a < b)
            return b - a;
        else
            return a - b;
    }

}
