package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FKList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        p.sendTitle("Salut " + p.getDisplayName(), "t trop bg", 10, 50, 10);
        p.setLevel(69);
        p.setOp(true);

    }


    @EventHandler
    public void lockPlayers(PlayerMoveEvent e) {


        Collection<? extends Player> list = Bukkit.getServer().getOnlinePlayers();

        for (Player p : list)
            if (!teams.contain(p))
                e.getPlayer().teleport(e.getFrom());

    }


}
