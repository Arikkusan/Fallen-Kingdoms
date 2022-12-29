/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Collection;

public class PlayerSpawnListener implements Listener {

    private final FK_Game game;

    public PlayerSpawnListener(FK_Game game) {

        this.game = game;

    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent e) {
        FK_Functions fkFunctions = new FK_Functions();

        FK_List teams = game.getFkList();
        Player p = e.getPlayer();

        if (!game.isStarted()) return;

        if (teams.size() == 0) return;

        FK_Team team = teams.searchTeam(p);

        if (team == null) return;

        fkFunctions.giveCompass(p, team);



    }
}
