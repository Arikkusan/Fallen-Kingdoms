/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;

public class PlayerMovementListener implements Listener {


    private final FK_Game game;
    private final FK_List teams;

    public PlayerMovementListener(FK_Game game, FK_List teams) {
        this.game = game;
        this.teams = teams;
    }

    @EventHandler
    public void lockPlayers(PlayerMoveEvent e) {

        Collection<? extends Player> list = Bukkit.getServer().getOnlinePlayers();

        if (game.isStarted() && game.isPaused() && !game.isFinished())
            for (Player p : list)
                if (!teams.contain(p)) // && game.
                    e.getPlayer().teleport(e.getFrom());

    }
}
