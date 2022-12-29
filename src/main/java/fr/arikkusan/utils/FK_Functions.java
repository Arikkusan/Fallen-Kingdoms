/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.utils;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_Team;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import sun.jvm.hotspot.oops.Metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FK_Functions {

    private FK_Game game;

    public FK_Functions() {

    }
    public FK_Functions(FK_Game game) {
        this.game = game;
    }


    public void setCustomName(String username, String pseudo) {

        Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
        ArrayList<String> playersName = new ArrayList<>();

        for (Player player : players)
            playersName.add(player.getName());

        if (playersName.contains(username)) {
            for (Player player : players) {
                if (player.getName().equalsIgnoreCase(username)) {
                    player.setCustomName(pseudo);
                    player.setDisplayName(player.getCustomName());

                    new FK_Functions().setListName(game, player);

                    player.sendMessage(
                            ChatColor.GOLD +
                                    "Votre surnom a été défini en tant que " +
                                    ChatColor.GOLD +
                                    player.getCustomName()
                    );
                }
            }

        }

    }

    public void setCustomName(Player p, String pseudo) {

        p.setCustomName(pseudo);
        p.setDisplayName(p.getCustomName());
        p.sendMessage(
                ChatColor.GOLD +
                        "Votre surnom a été défini en tant que " +
                        ChatColor.GOLD +
                        p.getCustomName()
        );

        new FK_Functions().setListName(game, p);


    }

    public void sendFKTHelp(Player sender) {
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
    }

    public void setListName(FK_Game game, Player p) {

        if (game.getFkList().size() > 0) {

            if (game.getFkList().contain(p)) {

                FK_Team team = game.getFkList().searchTeam(p);

                if (team != null)
                    p.setPlayerListName(ChatColor.valueOf(String.valueOf(team.getTeamColor())) + "[" + team.getTeamName() + "] " + p.getCustomName());
                else
                    p.setPlayerListName(ChatColor.GRAY + "" + ChatColor.BOLD + "[Maitre de partie] " + p.getDisplayName());

            }

        }
    }
    public void giveCompass(Player p, FK_Team team) {



    }

    public void setName(ItemStack itemStack, String customName) {

        ItemMeta data = itemStack.getItemMeta();
        data.setDisplayName(ChatColor.BOLD + customName);
        itemStack.setItemMeta(data);


    }

    public void setLore(ItemStack itemStack, String lore) {

        ItemMeta data = itemStack.getItemMeta();
        data.setLore(Collections.singletonList(ChatColor.GRAY + lore));
        itemStack.setItemMeta(data);

    }

    public void setLore(ItemStack itemStack, List<String> lore) {

        ItemMeta data = itemStack.getItemMeta();
        data.setLore(lore);
        itemStack.setItemMeta(data);

    }
}
