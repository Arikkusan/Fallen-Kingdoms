package fr.arikkusan;

import fr.arikkusan.FKClasses.FKList;
import fr.arikkusan.command.FKTeamCommands;
import fr.arikkusan.listeners.onStartStop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.List;

public final class fallenkingdoms extends JavaPlugin implements Listener {

    FKList teams;

    @Override
    public void onEnable() {

        teams = new FKList();

        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Fallen Kindoms plugin launched with success");

        getServer().createBossBar("Fallen Kingdom 2023", BarColor.BLUE, BarStyle.SOLID).setVisible(true);

        getServer().getPluginManager().registerEvents(new onStartStop(teams), this);


        // FKTeam Command
        FKTeamCommands fkTeamCommands = new FKTeamCommands(teams);

        getCommand("fkteam").setTabCompleter(fkTeamCommands);
        getCommand("fkteam").setExecutor(fkTeamCommands);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
