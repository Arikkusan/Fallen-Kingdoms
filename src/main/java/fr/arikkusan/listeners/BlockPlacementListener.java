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
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
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

        ArrayList<FK_Team> fkTeamsCheckList = new ArrayList<>(teams);

        // if the player is currently into a team
        if (teams.contain(p) && !game.isStarted())
            e.setCancelled(true);

        if (teams.contain(p)) {
            FK_Team team = teams.searchTeam(p);
            if (team != null) {
                fkTeamsCheckList.remove(team);
                if (game.isStarted()) {

                    for (FK_Team teamBlock : fkTeamsCheckList) {
                        if (teamBlock.inBaseArea(e.getBlock().getLocation())) {
                            e.setCancelled(true);
                            break;
                        }
                    }

                }

            }

        }


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
                            b.getType() == Material.REDSTONE_TORCH_ON ||
                            b.getType() == Material.REDSTONE_TORCH_OFF ||
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



}
