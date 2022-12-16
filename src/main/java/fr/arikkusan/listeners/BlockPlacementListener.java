package fr.arikkusan.listeners;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Team;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;
import java.util.Objects;

public class BlockPlacementListener implements Listener {

    private final FK_Game game;
    FK_List teams;

    public BlockPlacementListener(FK_Game game, FK_List teams) {
        this.game = game;
        this.teams = teams;
    }

    @EventHandler
    // Breaking block is disabled when you're not in a team
    public void onBlockBreak(BlockBreakEvent e) {

        // get the player who placed the block
        Player p = e.getPlayer();

        // if the player is currently into a team
        if (!teams.contain(p) && !game.isStarted())
            e.setCancelled(true);

    }

    @EventHandler
    // Cancel the block placement if not in base area or allowed block
    public void onBlockPlaced(BlockPlaceEvent e) {

        // get the player who placed the block
        Player p = e.getPlayer();

        // if the player is currently into a team
        if (teams.contain(p)) {

            // the var team = to the team of the player (thanks to searchTeam(p))
            FK_Team team = teams.searchTeam(p);

            // if the player's team isn't null then
            if (teams != null) {

                // get coordinates of placed block
                Block b = e.getBlockPlaced();
                Location locationBlock = b.getLocation();
                World w = b.getWorld();

                // on vérifie le type de block

                if (w == Bukkit.getWorld("world")) {
                    if (!(b.getType() == Material.WATER ||
                            b.getType() == Material.TORCH ||
                            b.getType() == Material.SOUL_TORCH ||
                            b.getType() == Material.REDSTONE_TORCH ||
                            b.getType() == Material.TNT ||
                            b.getType() == Material.FIRE ||
                            b.getType() == Material.LAVA)) {

                        if (!team.inBaseArea(locationBlock)) {

                            // if not in base area
                            e.setCancelled(true);

                        }

                    }

                }



            }

        } else if (!game.isStarted()) {
            e.setCancelled(true);
        }


    }

    @EventHandler
    // tell the distance to our base / if we're in our base
    public void onMoved(PlayerMoveEvent e) {

        // get the player who placed the block
        Player p = e.getPlayer();

        World worldLocation = p.getLocation().getWorld();

        if (worldLocation == Bukkit.getWorld("world")) {

            // if the player is currently into a team
            if (teams.contain(p)) {

                // the var team = to the team of the player (thanks to searchTeam(p))
                FK_Team team = teams.searchTeam(p);

                // if the player's team isn't null then
                if (teams != null) {

                    // get coordinates of placed block
                    Location locationBlock = p.getLocation();
                    String msg;
                    if (!team.inBaseArea(locationBlock)) {
                        // if not in base area
                        msg = ChatColor.GOLD + "Tu es à " + distancePoints(team.getCenterBase(), p.getLocation()) + " blocks de ta base";

                    } else {
                        msg = ChatColor.GOLD + "Tu es dans ta base";
                    }

                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));

                }

            }

        }


    }


    private int distancePoints(Location location1, Location location2) {

        // return the distance between 2 location in nb of blocks

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
        if (a < b)
            return b - a;
        else
            return a - b;
    }




}
