package com.prind.ctf.menus.listeners;

import com.prind.ctf.menus.KitSelectionMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public class KitSelectionMenuListener implements Listener {

    private List<Player> hasKitOn = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitSelectionMenu menu)) return;
        if (event.getCurrentItem() == null) return;

        menu.selectKit(event.getCurrentItem());
        hasKitOn.add((Player) event.getWhoClicked());
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        
    }
}
