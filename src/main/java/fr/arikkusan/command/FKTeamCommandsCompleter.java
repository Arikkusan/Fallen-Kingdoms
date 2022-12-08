package fr.arikkusan.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FKTeamCommandsCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();

        // first command argument
        if (args.length == 1) {

            list.add("create");
            list.add("delete");


        }

        // first command argument
        if (args.length == 2) {
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];

            for (Player p: players) {
                list.add(p.getCustomName());
            }


        }

        return list;
    }
}
