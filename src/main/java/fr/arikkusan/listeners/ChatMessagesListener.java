package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.Collection;

public class ChatMessagesListener implements Listener {

    private final FK_Game game;
    private final FK_List teams;

    public ChatMessagesListener(FK_Game game, FK_List teams) {
        this.game = game;
        this.teams = teams;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

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
        if (t != null) {

        }


    }


}
