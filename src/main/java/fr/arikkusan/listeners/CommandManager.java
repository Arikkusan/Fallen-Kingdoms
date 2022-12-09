package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FKList;
import fr.arikkusan.FKClasses.FkTeam;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.Collection;
import java.util.Objects;

public class CommandManager implements Listener {

    FKList teams;

    public CommandManager(FKList teams) {
        this.teams = teams;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e) {

        // get the player who placed the block
        Player p = e.getPlayer();

        // if the player is currently into a team
        if (teams.contain(p)) {

            // the var team = to the team of the player (thanks to searchTeam(p))
            FkTeam team = teams.searchTeam(p);

            // if the player's team isn't null then
            if (teams != null) {

                // get coordinates of placed block
                Block b = e.getBlockPlaced();
                Location locationBlock = b.getLocation();

                // on vérifie le type de block
                if ((b.getType() == Material.WATER ||
                        b.getType() == Material.TORCH ||
                        b.getType() == Material.REDSTONE_TORCH ||
                        b.getType() == Material.TNT ||
                        b.getType() == Material.FIRE ||
                        b.getType() == Material.LAVA) &&
                        b.getLocation().getWorld() == Bukkit.getWorld("world"))

                    if (!team.inBaseArea(locationBlock)) {
                        // if not in base area
                        b.setType(Material.AIR);
                    }

            }

        } else
            e.getBlockPlaced().setType(Material.AIR);

    }

    @EventHandler
    public void onDropDragonGrowl(PlayerDropItemEvent e) {

        e.setCancelled(true);

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p: players)
            Objects.requireNonNull(Bukkit.getWorld("world"))
                    .playSound(
                            p.getLocation(),
                            Sound.ENTITY_ENDER_DRAGON_GROWL,
                            100,
                            0
                    );

    }

}
