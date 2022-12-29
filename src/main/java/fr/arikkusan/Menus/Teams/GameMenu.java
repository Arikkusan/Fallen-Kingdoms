/*
 * Copyright (c) 2022.
 * Developper: Alix JAUGEY
 * Nickname: Arikkusan
 * Contact: jaugey.alix@gmail.com
 * In Collaboration with:
 */

package fr.arikkusan.Menus.Teams;

import fr.arikkusan.FKClasses.FK_Game;
import fr.arikkusan.FKClasses.FK_Team;
import fr.arikkusan.Menus.MainMenu;
import fr.arikkusan.main;
import fr.arikkusan.tools.GuiBuilder;
import fr.arikkusan.utils.FK_Functions;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class GameMenu implements GuiBuilder {

    private final FK_Game game;

    public GameMenu(FK_Game game) {
        this.game = game;
    }

    @Override
    public String name() {
        return "Fallen Kingdoms - Game";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player p, Inventory inv) {
        FK_Functions fct = new FK_Functions();

        ItemStack cancelItem = new ItemStack(Material.BARRIER);
        fct.setName(cancelItem, ChatColor.RED + "Cancel");
        fct.setLore(cancelItem, "Go back to main menu");

        inv.setItem(2, cancelItem);

        if (!game.isStarted()) {

            ItemStack startItem = new Wool(DyeColor.GREEN).toItemStack();
            startItem.setAmount(1);
            fct.setName(startItem, ChatColor.GREEN + "Start the game");
            fct.setLore(startItem, "Teleport everyone at their base and start the game");

            inv.setItem(6, startItem);


        } else if (!game.isFinished()) {


            ItemStack PauseItem = new Wool(DyeColor.ORANGE).toItemStack();
            PauseItem.setAmount(1);
            fct.setName(PauseItem, ChatColor.RED + "Pause the game");
            fct.setLore(PauseItem, "Lock every players and only allow spectators / God to move");


            ItemStack ResumeItem = new Wool(DyeColor.GREEN).toItemStack();
            ResumeItem.setAmount(1);
            fct.setName(ResumeItem, ChatColor.GREEN + "Resume the game");
            fct.setLore(ResumeItem, "Unlock every players and make time continue");


            if (!game.isPaused()) inv.setItem(4, PauseItem);
            else inv.setItem(4, ResumeItem);


            ItemStack finishItem = new Wool(DyeColor.RED).toItemStack();
            finishItem.setAmount(1);
            fct.setName(finishItem, ChatColor.GRAY + "Finish the game");
            fct.setLore(finishItem, "Finish the game");

            inv.setItem(6, finishItem);


        } else {

            ItemStack nothingItem = new Wool(DyeColor.BLACK).toItemStack();
            nothingItem.setAmount(1);
            fct.setName(nothingItem, ChatColor.GRAY + "Nothing to do here");
            fct.setLore(nothingItem, "Reload the plugin to factory reset everything");

            inv.setItem(6, nothingItem);


        }


    }

    @Override
    public void contents(Player p, Inventory inv, FK_Team team) {}

    @Override
    public void onClick(Player p, Inventory inv, ItemStack current, int slot) {

        if (!inv.getName().equalsIgnoreCase(name())) return;
        if (current == null) return;
        if (current.getItemMeta() == null) return;

        String val = ChatColor.stripColor(current.getItemMeta().getDisplayName().toLowerCase());

        if (val == null) return;

        switch (val) {

            case "cancel":
                p.closeInventory();
                main.getInstance().getGuiManager().open(p, MainMenu.class);
                break;
            case "start the game":
                p.closeInventory();
                game.startGame();
                break;
            case "pause the game":
                p.closeInventory();
                game.pauseGame();
                break;
            case "resume the game":
                p.closeInventory();
                game.resumeGame();
                break;
            case "finish the game":
                p.closeInventory();
                game.stopGame();
                break;
            case "nothing to do here":
                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                break;


        }

    }
}
