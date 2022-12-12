package fr.arikkusan;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.command.CustomNameCmd;
import fr.arikkusan.command.FKTeamCommands;
import fr.arikkusan.command.FkGameCommands;
import fr.arikkusan.listeners.BlockPlacementListener;
import fr.arikkusan.listeners.ChatMessagesListener;
import fr.arikkusan.listeners.PlayerMovementListener;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class main extends JavaPlugin implements Listener {

    private FK_Game game;
    private FK_List teams;

    @Override
    public void onEnable() {

        game = new FK_Game();
        teams = game.getFkList();

        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Fallen Kindoms plugin launched with success");

        getServer().createBossBar("Fallen Kingdom 2023", BarColor.BLUE, BarStyle.SOLID).setVisible(true);

        getServer().getPluginManager().registerEvents(new PlayerMovementListener(game, teams), this);
        getServer().getPluginManager().registerEvents(new ChatMessagesListener(game, teams), this);
        getServer().getPluginManager().registerEvents(new BlockPlacementListener(teams), this);


        // FKTeam Command
        CustomNameCmd CustomNameCmd = new CustomNameCmd();
        FkGameCommands gameCmd = new FkGameCommands(game, teams);
        FKTeamCommands fkTeamCommands = new FKTeamCommands(teams);

        Objects.requireNonNull(getCommand("fkteam")).setTabCompleter(fkTeamCommands);
        Objects.requireNonNull(getCommand("fkteam")).setExecutor(fkTeamCommands);

        Objects.requireNonNull(getCommand("CustomName")).setTabCompleter(CustomNameCmd);
        Objects.requireNonNull(getCommand("CustomName")).setExecutor(CustomNameCmd);

        Objects.requireNonNull(getCommand("fk")).setTabCompleter(gameCmd);
        Objects.requireNonNull(getCommand("fk")).setExecutor(gameCmd);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Fallen Kindoms plugin stopped with success");
    }
}
