/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class FkGameCommands implements CommandExecutor, TabCompleter {

    FK_Game game;
    FK_List teams;

    public FkGameCommands(FK_Game game, FK_List teamList) {
        this.game = game;
        this.teams = teamList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "start":
                    game.startGame();
                case "pause":
                    game.pauseGame();
                case "resume":
                    game.resumeGame();
                case "stop":
                    game.stopGame();
            }


        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabCompletion = new ArrayList<>();

        // first command argument
        if (args.length == 1) {

            if (!game.isStarted())
                tabCompletion.add("Start");

            if (game.isStarted() && !game.isPaused() && !game.isFinished())
                tabCompletion.add("Pause");

            if (game.isStarted() && game.isPaused() && !game.isFinished())
                tabCompletion.add("Resume");

            if (game.isStarted() && !game.isPaused() && !game.isFinished())
                tabCompletion.add("Stop");

        }

        return tabCompletion;
    }
}
