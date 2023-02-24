package com.prind.ctf.menus.listeners;

import com.prind.ctf.menus.KitSelectionMenu;
import com.prind.ctf.menus.enums.ItemMenuEnum;
import com.prind.ctf.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KitSelectionMenuItemListener implements Listener {

    @EventHandler
    public void onKitSelectionMenuItem(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null) return;

        ItemStack item = event.getItem();
        if (!(ItemUtil.isCustom(item, ItemMenuEnum.KitSelectionMenu))) return;

        new KitSelectionMenu(event.getPlayer());

        event.setCancelled(true);
    }
}
