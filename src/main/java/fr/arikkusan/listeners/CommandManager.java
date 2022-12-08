package fr.arikkusan.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class CommandManager implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        Player player = e.getPlayer();
        String message = e.getMessage();
        String[] args = message.split(" ");
        String command = args[0];

        if (command.equalsIgnoreCase("/fkTeam")) {

            player.sendMessage(ChatColor.DARK_BLUE + "Commande de team utilisée");
            // new CmdTeamManager(player, message, args);

        }

        if (command.equalsIgnoreCase("/fk")) {

            if (args[1].equalsIgnoreCase("pause")) {

                Collection<? extends Player> playerList = player.getServer().getOnlinePlayers();

                for (Player p : playerList) {

                    player.setVelocity(new Vector().zero());
                    player.sendTitle(
                            "Pause du Fallen Kindgdom",
                            "Veuillez patienter",
                            1,
                            5,
                            1);
                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.BOLD + "Pause du Fallen Kingdom");
                    player.sendMessage(ChatColor.RED + "Veuillez patienter");

                }

            }

        }

    }

}
