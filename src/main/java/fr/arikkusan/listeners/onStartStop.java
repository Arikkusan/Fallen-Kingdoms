package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FKList;
import fr.arikkusan.FKClasses.FkTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.Collection;

public class onStartStop implements Listener {

    private final FKList teams;

    public onStartStop(FKList teams) {

        this.teams = teams;

    }

    @EventHandler
    public void team(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        FkTeam team = teams.searchTeam(p);

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
    public void team(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        FkTeam team = teams.searchTeam(p);

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
    public void lockPlayers(PlayerMoveEvent e) {

        Collection<? extends Player> list = Bukkit.getServer().getOnlinePlayers();

        for (Player p : list)
            if (!teams.contain(p))
                e.getPlayer().teleport(e.getFrom());

    }


}
