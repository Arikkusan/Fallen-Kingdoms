package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FKList;
import fr.arikkusan.FKClasses.FkTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class CommandManager implements Listener {

    FKList teams;

    public CommandManager(FKList teams) {
        this.teams = teams;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        if (teams.contain(p)) {

            FkTeam team = teams.searchTeam(p);

            if (teams != null) {

                // get coordinates of placed block
                Block b = e.getBlockPlaced();
                Location locationBlock = b.getLocation();

                if (!team.inBaseArea(locationBlock)) {
                    // if not in base area
                    b.setType(Material.AIR);

                }

            }

        }

    }

}
