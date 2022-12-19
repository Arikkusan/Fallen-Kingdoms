package fr.arikkusan;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.command.CustomNameCommands;
import fr.arikkusan.command.FKTeamCommands;
import fr.arikkusan.command.FkGameCommands;
import fr.arikkusan.command.gmCommands;
import fr.arikkusan.listeners.BlockPlacementListener;
import fr.arikkusan.listeners.ChatJoinQuitListener;
import fr.arikkusan.listeners.MobKillListener;
import fr.arikkusan.listeners.PlayerMovementListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import sun.text.resources.CollationData;

import java.util.Collection;
import java.util.Objects;

public final class main extends JavaPlugin implements Listener {

    private FK_Game game;
    private FK_List teams;

    @Override
    public void onEnable() {

        game = new FK_Game(this);
        teams = game.getFkList();

        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Fallen Kindoms plugin launched with success");

        getServer().getPluginManager().registerEvents(new PlayerMovementListener(game, teams), this);
        getServer().getPluginManager().registerEvents(new ChatJoinQuitListener(this, game, teams), this);
        getServer().getPluginManager().registerEvents(new BlockPlacementListener(game, teams), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(), this);


        // FKTeam Command
        CustomNameCommands CustomNameCmd = new CustomNameCommands(game);
        FkGameCommands gameCmd = new FkGameCommands(game, teams);
        FKTeamCommands fkTeamCmd = new FKTeamCommands(teams);
        gmCommands gmCmd = new gmCommands();

        Objects.requireNonNull(getCommand("fkteam")).setTabCompleter(fkTeamCmd);
        Objects.requireNonNull(getCommand("fkteam")).setExecutor(fkTeamCmd);

        Objects.requireNonNull(getCommand("CustomName")).setTabCompleter(CustomNameCmd);
        Objects.requireNonNull(getCommand("CustomName")).setExecutor(CustomNameCmd);

        Objects.requireNonNull(getCommand("fk")).setTabCompleter(gameCmd);
        Objects.requireNonNull(getCommand("fk")).setExecutor(gameCmd);

        Objects.requireNonNull(getCommand("gm")).setTabCompleter(gmCmd);
        Objects.requireNonNull(getCommand("gm")).setExecutor(gmCmd);


    }

    @Override
    public void onLoad() {
        super.onLoad();

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (Player p: players)
            p.kickPlayer(ChatColor.AQUA + "Déconnexion nécessaire pour reload du plugin, vous pouvez désormais vous reconnecter !");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Fallen Kindoms plugin stopped with success");
    }
}
