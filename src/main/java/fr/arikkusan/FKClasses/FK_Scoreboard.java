/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.FKClasses;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class FK_Scoreboard implements ScoreboardManager {

    @Override
    public Scoreboard getMainScoreboard() {
        return null;
    }

    @Override
    public Scoreboard getNewScoreboard() {
        return null;
    }
}
