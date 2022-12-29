/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.listeners;

import fr.arikkusan.Items.ItemsManager;
import fr.arikkusan.Menus.MainMenu;
import fr.arikkusan.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PluginItemUseListener implements Listener {

    @EventHandler
    public void onCustomItemClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (p.getItemInHand() == null) return;

        if (p.getItemInHand().getItemMeta().equals(ItemsManager.mainMenu.getItemMeta()))
            main.getInstance().getGuiManager().open(p, MainMenu.class);


    }

}
