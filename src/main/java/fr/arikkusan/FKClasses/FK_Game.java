package fr.arikkusan.FKClasses;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class FK_Game {

    private String FkTitle;
    private Scoreboard scoreboard;
    private ArrayList<FKEvent> gameEvents;
    private boolean started;
    private boolean paused;
    private boolean finished;

    public FK_Game(@NonNull String fkTitle) {
        this.FkTitle = fkTitle;
    }



}
