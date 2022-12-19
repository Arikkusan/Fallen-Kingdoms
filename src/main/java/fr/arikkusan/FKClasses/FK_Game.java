package fr.arikkusan.FKClasses;

import fr.arikkusan.listeners.ChatJoinQuitListener;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class FK_Game {

    private String FK_TIME_RUNNABLE_ID = "FK_TIME_RUNNABLE";
    private final Plugin plugin;
    private int taskID;
    private String FkTitle;
    private boolean started;
    private boolean paused;
    private boolean finished;
    private final FK_List fkList;

    private FK_Date day;
    private ScoreboardManager gameScoreboard = Bukkit.getScoreboardManager();

    private ArrayList<FK_Event> events = new ArrayList<>();


    public FK_Game(Plugin plugin) {
        this.plugin = plugin;
        this.FkTitle = "Fallen Kingdoms";
        this.fkList = new FK_List();
        setEvents();
        day = new FK_Date(1, 0);
    }

    public String getFkTitle() {
        return FkTitle;
    }

    public void setFkTitle(@NonNull String fkTitle) {
        FkTitle = fkTitle;
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        //for (Player p: players)
          //  p.kickPlayer(ChatColor.AQUA + "Reload du Scoreboard, merci de vous reconnecter !");

    }


    public FK_Date getDate() {
        return day;
    }


    public FK_List getFkList() {
        return fkList;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void startGame() {
        day = new FK_Date(1, 0);

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setTime(0);


        for (Player p : players) {

            p.resetPlayerTime();

            FK_Team team = fkList.searchTeam(p);
            p.setGameMode(GameMode.ADVENTURE);
            p.closeInventory();
            p.getInventory().clear();
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.setHealth(20);
            p.setLevel(0);
            p.sendTitle(
                    ChatColor.GOLD + "Début du Fallen Kingdoms !",
                    ChatColor.GOLD + "Démarrage en cours...",
                    10,
                    40,
                    10
            );

            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));

            if (team != null) {
                p.teleport(team.getCenterBase());
                p.setGameMode(GameMode.SURVIVAL);
            } else {
                p.setGameMode(GameMode.SPECTATOR);
            }

        }

        setStarted(true);
        setFinished(false);
        setPaused(false);
        startRunnable(players);


    }

    private void startRunnable(Collection<? extends Player> players) {


        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                plugin,
                new Runnable() {

                    FK_Time_Runnable board = new FK_Time_Runnable(FK_TIME_RUNNABLE_ID);

                    @Override
                    public void run() {
                        if (!board.hasID())
                            board.setId(taskID);
                        getDate().next();


                        if (getDate().changed()) {
                            FK_Event event = FK_Event.searchEventOfDay(events, getDate().getDay());
                            String message = "";
                            if (event != null) {
                                message = ChatColor.GREEN + "" + ChatColor.BOLD + event.getDesc();
                                event.setActivated(true);
                            }

                            for (Player p : players) {

                                if (event != null)
                                    p.sendMessage(message);

                                p.sendTitle(
                                        ChatColor.GOLD + "" + ChatColor.BOLD + getFkTitle() + " : " + "Jour " + getDate().getDay(),
                                        message,
                                        10,
                                        20,
                                        10
                                );
                            }
                        }

                    }
                },
                0,
                20L
        );

    }

    public void pauseGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        setPaused(true);

        FK_Time_Runnable board = new FK_Time_Runnable(FK_TIME_RUNNABLE_ID);
        if (board.hasID())
            board.stop();

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            FK_Team team = fkList.searchTeam(p);

            if (team != null) {
                p.setGameMode(GameMode.SPECTATOR);

            }

        }

    }

    public void resumeGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        setPaused(false);


        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            FK_Team team = fkList.searchTeam(p);
            if (team != null)
                p.setGameMode(GameMode.SURVIVAL);

        }

        startRunnable(players);

    }

    public void stopGame() {
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setTime(0);
        setFinished(true);

        FK_Time_Runnable board = new FK_Time_Runnable(FK_TIME_RUNNABLE_ID);
        if (board.hasID())
            board.stop();


        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players) {

            p.setHealth(20);
            p.setSaturation(20);
            p.setFoodLevel(20);
            p.setGameMode(GameMode.CREATIVE);

            p.sendTitle(
                    ChatColor.GOLD + "" + ChatColor.BOLD + getFkTitle(),
                    ChatColor.GOLD + "Fin de partie",
                    10,
                    80,
                    10
            );

        }
    }


    public ArrayList<FK_Event> getEvents() {
        return events;
    }

    public void setEvents() {
        ArrayList<FK_Event> eventsList = new ArrayList<>();

        eventsList.add(
                new FK_Event(
                        "PVP",
                        "Activiation du PVP !",
                        3
                )
        );
        eventsList.add(
                new FK_Event(
                        "Nether",
                        "Activation du Nether !",
                        5
                )
        );
        eventsList.add(
                new FK_Event(
                        "Dragon",
                        "Activation de l'end, Le dragon est accessible !",
                        7
                )
        );
        eventsList.add(
                new FK_Event(
                        "Assauts",
                        "Les assauts sont désormais possibles !",
                        8
                )
        );

        this.events = eventsList;
    }

    public void activateDragon() {
        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        for (FK_Event event : getEvents()) {
            if (event.getName().equalsIgnoreCase("dragon")) {

                event.setActivated(true);
                for (Player p: players) {
                    p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Le Dragon a été activé, veuillez contacter Arikkusan pour plus d'informations !");
                    p.sendTitle(
                            ChatColor.GREEN + "" + ChatColor.BOLD + "Activation de l'Ender Dragon",
                            ChatColor.GREEN + "Plus d'information auprès d'Arikkusan !",
                            10,
                            80,
                            10
                    );

                    p.playSound(
                            p.getLocation(),
                            Sound.ENTITY_ENDER_DRAGON_GROWL,
                            100,
                            10
                    );
                }
                break;
            }
        }

        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-35, 112, 92, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-35, 112, 93, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-35, 112, 94, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-36, 112, 92, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-36, 112, 93, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-36, 112, 94, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-37, 112, 92, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-37, 112, 93, Material.AIR.createBlockData());
        // Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setBlockData(-37, 112, 94, Material.AIR.createBlockData());
    }
}
