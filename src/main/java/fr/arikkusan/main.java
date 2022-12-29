package fr.arikkusan;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.Items.ItemsManager;
import fr.arikkusan.Menus.MainMenu;
import fr.arikkusan.Menus.Teams.GameMenu;
import fr.arikkusan.Menus.Teams.NewTeamMenu;
import fr.arikkusan.Menus.Teams.TeamMenu;
import fr.arikkusan.Menus.Teams.TeamsMenu;
import fr.arikkusan.command.*;
import fr.arikkusan.listeners.*;
import fr.arikkusan.tools.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class main extends JavaPlugin implements Listener {
    private static main instance;

    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
    private GuiManager guiManager;

    private FK_Game game;
    private FK_List teams;
    ChatJoinQuitListener logListener;

    @Override
    public void onEnable() {
        instance = this;

        ItemsManager.init();

        game = new FK_Game(this);
        teams = game.getFkList();

        logListener = new ChatJoinQuitListener(this, game, teams);

        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Fallen Kindoms plugin launched with success");

        getServer().getPluginManager().registerEvents(new PlayerMovementListener(game, teams), this);
        getServer().getPluginManager().registerEvents(logListener, this);
        getServer().getPluginManager().registerEvents(new BlockPlacementListener(game, teams), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(), this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new PluginItemUseListener(), this);

        loadGUI();

        getServer().getPluginManager().registerEvents(getGuiManager(), this);

        // FKTeam Command
        TeamNameCommand TeamNameCmd = new TeamNameCommand(game);
        TeamCenterCommand TeamCenterCmd = new TeamCenterCommand(game);
        CustomNameCommands CustomNameCmd = new CustomNameCommands(game);
        FkGameCommands gameCmd = new FkGameCommands(game, teams);
        FKTeamCommands fkTeamCmd = new FKTeamCommands(teams);
        gmCommands gmCmd = new gmCommands();

        Objects.requireNonNull(getCommand("fkteam")).setTabCompleter(fkTeamCmd);
        Objects.requireNonNull(getCommand("fkteam")).setExecutor(fkTeamCmd);

        Objects.requireNonNull(getCommand("CustomName")).setTabCompleter(CustomNameCmd);
        Objects.requireNonNull(getCommand("CustomName")).setExecutor(CustomNameCmd);

        Objects.requireNonNull(getCommand("fallenkingdoms")).setTabCompleter(gameCmd);
        Objects.requireNonNull(getCommand("fallenkingdoms")).setExecutor(gameCmd);

        Objects.requireNonNull(getCommand("teamName")).setTabCompleter(TeamNameCmd);
        Objects.requireNonNull(getCommand("teamName")).setExecutor(TeamNameCmd);

        Objects.requireNonNull(getCommand("teamCenter")).setTabCompleter(TeamCenterCmd);
        Objects.requireNonNull(getCommand("teamCenter")).setExecutor(TeamCenterCmd);

        Objects.requireNonNull(getCommand("gm")).setTabCompleter(gmCmd);
        Objects.requireNonNull(getCommand("gm")).setExecutor(gmCmd);

        Objects.requireNonNull(getCommand("base")).setExecutor(new CompassCommand(game));



        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();

        for (Player p : players)
            new ChatJoinQuitListener(this, game, teams).start(p);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Fallen Kindoms plugin stopped with success");
    }

    public static main getInstance() {
        return instance;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public void loadGUI() {

        guiManager = new GuiManager();
        registeredMenus = new HashMap<>();

        guiManager.addMenu(new MainMenu());
        guiManager.addMenu(new NewTeamMenu(game));
        guiManager.addMenu(new TeamsMenu(game));
        guiManager.addMenu(new TeamMenu(game));
        guiManager.addMenu(new GameMenu(game));





    }



}

