package com.prind.ctf.menus.listeners;

import com.prind.ctf.menus.KitSelectionMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitSelectionMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitSelectionMenu menu)) return;
        if (event.getCurrentItem() == null) return;

        menu.selectKit(event.getCurrentItem());
        event.setCancelled(true);
    }

}
