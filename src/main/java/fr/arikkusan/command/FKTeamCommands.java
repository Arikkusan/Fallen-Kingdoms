package fr.arikkusan.command;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_List;
import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.Menus.MainMenu;
import fr.arikkusan.main;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FKTeamCommands implements CommandExecutor, TabCompleter {

    FK_List teamList;

    ArrayList<String> colors;

    public FKTeamCommands(FK_List teams) {
        this.teamList = teams;
        this.colors = new ArrayList<>();
        colors.add("GRAY");
        colors.add("BLUE");
        colors.add("GREEN");
        colors.add("RED");
        colors.add("YELLOW");
        colors.add("WHITE");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        FK_Functions fct = new FK_Functions();

        if (args.length == 0) {

            main.getInstance().getGuiManager().open((Player) sender, MainMenu.class);


        } else {



            if (args[0].equalsIgnoreCase("create") && args.length == 2) {
                sender.sendMessage(
                        ChatColor.RED + "" +
                                ChatColor.BOLD + "(!) " +
                                ChatColor.RED +
                                "Vous ne pouvez pas créer d'équipe sans renseigner de couleur, utilisez /FkTeam create <Nom_Equipe> <Couleur>");


            }

            if (args[0].equalsIgnoreCase("create") && args.length >= 3) {
                ArrayList<String> teamNames = new ArrayList<>();

                for (FK_Team t : teamList) teamNames.add(t.getTeamName());

                if (!teamNames.contains(args[1]) && args[2] != null) {
                    FK_Team team;

                    if (colors.contains(args[2])) {
                        team = new FK_Team(args[1], DyeColor.valueOf(args[2]));
                        teamList.add(team);
                        sender.sendMessage(
                                ChatColor.GREEN + "" +
                                        ChatColor.BOLD + "(OK) " +
                                        ChatColor.GREEN +
                                        "Equipe " + args[1] + " crée avec succès !");
                    } else
                        sender.sendMessage(
                                ChatColor.RED + "" +
                                        ChatColor.RED + "(!) " +
                                        ChatColor.RED +
                                        "Erreur, couleur invalide renseignée !");


                } else {
                    sender.sendMessage(
                            ChatColor.RED + "" +
                                    ChatColor.BOLD + "(!) " +
                                    ChatColor.RED +
                                    "Vous ne pouvez pas créer cette équipe, une autre de ce même nom existe déjà"
                    );
                }


            }

            if (args[0].equalsIgnoreCase("list")) {

                if (teamList.size() == 0) {
                    sender.sendMessage(ChatColor.RED + "Aucune équipe n'existe pour le moment, utilisez /fkt pour plus d'information");
                } else {

                    sender.sendMessage(ChatColor.YELLOW + "Les différentes équipes sont :");
                    for (FK_Team t : teamList) {
                        sender.sendMessage(ChatColor.valueOf(t.getTeamColor().name()) + "  - " + t.getTeamName());
                        ArrayList<Player> Members = t.getTeamList();

                        if (Members != null) {
                            for (Player p : Members)
                                sender.sendMessage(ChatColor.valueOf(t.getTeamColor().name()) + "        " + p.getDisplayName());
                        }

                    }

                }

            }

            if (args[0].equalsIgnoreCase("join") && args.length == 2) {
                Player p = (Player) sender;

                if (teamList.contain(p))
                    teamList.searchTeam(p).removeMember(p);
                teamList.add(p, args[1]);
            }

            if (args[0].equalsIgnoreCase("join") && args.length == 3) {
                Player p = findPlayer(args[2]);
                if (p != null) {
                    if (teamList.contain(p))
                        teamList.searchTeam(p).removeMember(p);
                    teamList.add(p, args[1]);
                }
                else
                    sender.sendMessage(
                            ChatColor.RED + "" +
                                    ChatColor.BOLD + "(!) " +
                                    ChatColor.RED +
                                    "Vous ne pouvez pas ajouter une personne innexistante à une Team"
                    );
            }

            if (args[0].equalsIgnoreCase("quit")) {
                for (FK_Team t : teamList)
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

            if (args[0].equalsIgnoreCase("center")) {
                FK_Team team = teamList.searchTeam((Player) sender);
                if (team != null)
                    team.setCenterBase(((Player) sender).getLocation());
                else
                    sender.sendMessage(
                            ChatColor.RED + "" +
                                    ChatColor.BOLD + "(!)" +
                                    ChatColor.RED + " Veuillez vous mettre dans une équipe pour pouvoir la centrer"
                    );
            }

        }

        return true;
    }

    private Player findPlayer(String name) {

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player p : players)
            if (p.getName().equalsIgnoreCase(name))
                return p;

        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();

        // first command argument
        if (args.length == 1) {

            list.add("create");

            if (teamList.size() > 0) {
                list.add("list");
                list.add("join");
                list.add("quit");
                list.add("center");
                list.add("delete");
            }


        }

        // when the user want to add a player to a Team
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            // for each team, we add the name of the team on the list
            for (FK_Team team : teamList)
                list.add(team.getTeamName());
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("join")) {
            // for each team, we add the name of the team on the list
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player p : players)
                list.add(p.getName());
        }


        // if the user want to delete a specific team, then, we give him the names of the teams
        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {

            for (FK_Team team : teamList)
                list.add(team.getTeamName());

        }

        // if the user want to create a new team we tell us to give a specific name
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            list.add("Team_Name");
        }

        // if the user want to create a new team we tell us to give a specific color
        if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            list.addAll(colors);
        }


        // we return to our main section the list of strings to show to the user when using the command
        return list;
    }
}
