package fr.arikkusan.FKClasses;



import fr.arikkusan.Items.ItemsManager;
import org.bukkit.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;
import java.util.Collection;

import java.util.Objects;

public class FK_Game {

    private static final String DAYLIGHT_CYCLE = "doDaylightCycle";
    private final String FK_TIME_RUNNABLE_ID = "FK_TIME_RUNNABLE";
    private final Plugin plugin;
    private int taskID;
    private String FkTitle;
    private boolean started;
    private boolean paused;
    private boolean finished;
    private final FK_List fkList;

    private FK_Date day;

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

    public void setFkTitle(String fkTitle) {
        FkTitle = fkTitle;
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

        World world = Bukkit.getServer().getWorld("world");

        world.setGameRuleValue(DAYLIGHT_CYCLE, "true");
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
                    ChatColor.GOLD + "Démarrage en cours..."
            );


            if (team != null) {
                p.teleport(team.getCenterBase());
                p.setGameMode(GameMode.SURVIVAL);
                p.setCompassTarget(team.getCenterBase());
                p.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, 16));
                p.getInventory().setItem(7, ItemsManager.baseCompass);
            } else {
                p.setGameMode(GameMode.SPECTATOR);
                p.getInventory().setItem(8, ItemsManager.playerMenu);
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

                    final FK_Time_Runnable board = new FK_Time_Runnable(FK_TIME_RUNNABLE_ID);

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
                                        message
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
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRuleValue(DAYLIGHT_CYCLE, "false");
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
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRuleValue(DAYLIGHT_CYCLE, "true");
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
        Objects.requireNonNull(Bukkit.getServer().getWorld("world")).setGameRuleValue(DAYLIGHT_CYCLE, "true");
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
                    ChatColor.GOLD + "Fin de partie"
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
                            ChatColor.GREEN + "Plus d'information auprès d'Arikkusan !"
                    );

                    p.playSound(
                            p.getLocation(),
                            Sound.ENDERDRAGON_GROWL,
                            100,
                            10
                    );
                }
                break;
            }
        }
    }
}
