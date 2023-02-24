package com.prind.ctf.menus.listeners;

import com.prind.ctf.menus.KitShopMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitShopMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof KitShopMenu menu)) return;
        if (event.getCurrentItem() == null) return;

        menu.buyKit(event.getCurrentItem());
        event.setCancelled(true);
    }

}
