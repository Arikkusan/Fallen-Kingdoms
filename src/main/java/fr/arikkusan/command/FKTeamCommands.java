package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FKList;
import fr.arikkusan.FKClasses.FkTeam;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FKTeamCommands implements CommandExecutor, TabCompleter {

    FKList teamList;

    public FKTeamCommands(FKList teams) {
        this.teamList = teams;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            sender.sendMessage(
                    ChatColor.DARK_GREEN +
                            "La commande FKTeam ou FKT permet de gérer les différentes teams pour la partie de Fallen Kingdom."
            );
            sender.sendMessage(
                    ChatColor.DARK_GREEN +
                            "  Grâce à celle-ci vous pourrez créer / modifier / supprimer / rejoindre et quitter des groupes"
            );
            sender.sendMessage(
                    ChatColor.DARK_GREEN +
                            "  L'auto-complétion de la commande vous guidera tout au long de votre entrée de commande !"

            );
            sender.sendMessage(
                    ChatColor.GRAY +
                            "" +
                    ChatColor.ITALIC +
                            "  La gestion de groupe sera désactivée lorsque la partie sera lancée !"

            );

        } else {

            if (args[0].equalsIgnoreCase("create") && args.length >= 2) {
                ArrayList<String> teamNames = new ArrayList<>();

                for (FkTeam t : teamList) teamNames.add(t.getTeamName());

                if (!teamNames.contains(args[1])) {
                    FkTeam team = new FkTeam(args[1]);
                    teamList.add(team);
                    sender.sendMessage(
                            ChatColor.GREEN + "" +
                                    ChatColor.BOLD + "(OK) " +
                                    ChatColor.GREEN +
                                    "Equipe " + args[1] + " crée avec succès !");
                } else {
                    sender.sendMessage(
                            ChatColor.RED + "" +
                                    ChatColor.BOLD + "(!) " +
                                    ChatColor.RED +
                                    "Vous ne pouvez pas créer cette équipe, une autre de ce même nom existe déjà");
                }


            }

            if (args[0].equalsIgnoreCase("list")) {

                if (teamList.size() == 0) {
                    sender.sendMessage(ChatColor.RED + "Aucune équipe n'existe pour le moment, utilisez /fkt pour plus d'information");
                } else {

                    for (FkTeam t : teamList) {
                        sender.sendMessage(ChatColor.YELLOW + "Les différentes équipes sont :");
                        sender.sendMessage(ChatColor.valueOf(t.getTeamColor().name()) + "  - " + t.getTeamName());
                        ArrayList<Player> Members = t.getTeamList();

                        if (Members != null) {
                            for (Player p : Members)
                                sender.sendMessage(ChatColor.valueOf(t.getTeamColor().name()) + "      * " + p.getCustomName());
                        }

                    }

                }

            }

            if (args[0].equalsIgnoreCase("join")) {
                teamList.add((Player) sender, args[1]);
            }

            if (args[0].equalsIgnoreCase("quit")) {
                for (FkTeam t : teamList)
                    t.removeMember((Player) sender);
            }

            if (args[0].equalsIgnoreCase("delete")) {
                if (teamList.removeTeam(args[1])) {
                    sender.sendMessage(
                            ChatColor.DARK_GRAY + "" +
                                    ChatColor.BOLD +
                                    "La team " + args[1] + " a été supprimée avec succès"
                    );

                }
            }

        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();

        // first command argument
        if (args.length == 1) {

            list.add("create");
            list.add("list");

            if (teamList.size() > 0) {
                list.add("join");
                list.add("quit");
                list.add("update");
                list.add("delete");
            }


        }

        // when the user want to add a player to a Team
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            // for each team, we add the name of the team on the list
            for (FkTeam team : teamList)
                list.add(team.getTeamName());
        }


        // if the user want to delete a specific team, then, we give him the names of the teams
        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {

            for (FkTeam team : teamList)
                list.add(team.getTeamName());

        }

        // if the user want to create a new team we tell us to give a specific name
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            list.add("Team_Name");
        }

        // if the user want to create a new team we tell us to give a specific color
        if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            list.add("Blue");
            list.add("Red");
            list.add("Green");
            list.add("Cyan");
            list.add("Purple");
        }


        // we return to our main section the list of strings to show to the user when using the command
        return list;
    }
}
