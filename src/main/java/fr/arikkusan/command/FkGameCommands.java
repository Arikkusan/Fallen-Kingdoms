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
import fr.arikkusan.Menus.Teams.GameMenu;
import fr.arikkusan.main;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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

        if (args.length == 0) {
            main.getInstance().getGuiManager().open((Player) sender, GameMenu.class);
            return true;
        }

            switch (args[0].toLowerCase()) {
                case "nom":
                    StringBuilder title = new StringBuilder();
                    for (int i = 1; i < args.length; i++)
                        title.append(args[i]).append(" ");

                    game.setFkTitle(String.valueOf(title));
                    break;
                case "start":
                    game.startGame();
                    break;
                case "pause":
                    game.pauseGame();
                    break;
                case "resume":
                    game.resumeGame();
                    break;
                case "stop":
                    game.stopGame();
                    break;
                case "activatedragon":
                    game.activateDragon();
                    break;
            }




        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabCompletion = new ArrayList<>();

        // first command argument
        if (args.length == 1) {

            if (!game.isStarted()) {
                tabCompletion.add("Start");
                tabCompletion.add("Nom");
            }

            if (game.isStarted() && !game.isPaused() && !game.isFinished()) {
                tabCompletion.add("Pause");
                if (!game.getEvents().get(3).isActivated())
                    tabCompletion.add("ActivateDragon");
            }

            if (game.isStarted() && game.isPaused() && !game.isFinished())
                tabCompletion.add("Resume");

            if (game.isStarted() && !game.isPaused() && !game.isFinished())
                tabCompletion.add("Stop");

        }

        return tabCompletion;
    }
}
