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
import org.bukkit.event.player.PlayerMoveEvent;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;

public class BlockPlacementListener implements Listener {

    FKList teams;

    public BlockPlacementListener(FKList teams) {
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
                if (!((b.getType() == Material.WATER ||
                        b.getType() == Material.TORCH ||
                        b.getType() == Material.REDSTONE_TORCH ||
                        b.getType() == Material.TNT ||
                        b.getType() == Material.FIRE ||
                        b.getType() == Material.LAVA) &&
                        b.getLocation().getWorld() == Bukkit.getWorld("world")))

                    if (!team.inBaseArea(locationBlock)) {
                        // if not in base area
                        e.setCancelled(true);
                    }

            }

        } else {
            e.getPlayer().sendMessage("Block supprimé");
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onMoved(PlayerMoveEvent e) {


        // get the player who placed the block
        Player p = e.getPlayer();

        // if the player is currently into a team
        if (teams.contain(p)) {

            // the var team = to the team of the player (thanks to searchTeam(p))
            FkTeam team = teams.searchTeam(p);

            // if the player's team isn't null then
            if (teams != null) {

                // get coordinates of placed block
                Location locationBlock = p.getLocation();

                if (!team.inBaseArea(locationBlock)) {
                    // if not in base area

                    p.sendMessage("Tu es à " + distancePoints(team.getCenterBase(), p.getLocation()) + " blocks de ta base");
                } else {
                    p.sendMessage("Tu es dans ta base");
                }

            }

        }
    }

    private int distancePoints(Location location1, Location location2) {

        int x1 = location1.getBlockX();
        int y1 = location1.getBlockY();
        int z1 = location1.getBlockZ();

        int x2 = location2.getBlockX();
        int y2 = location2.getBlockY();
        int z2 = location2.getBlockZ();

        int distanceX = dist(x1, x2),
                distanceY = dist(y1, y2),
                distanceZ = dist(z1, z2);

        return (int) (Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ) - 16);
    }

    private int dist(int a, int b) {

        int distance;

        if (a < b)
            return b - a;
        else
            return a - b;

    }


/*

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

 */

}
