package com.prind.ctf.kits.listeners;

import com.prind.ctf.kits.enums.ItemEnum;
import com.prind.ctf.kits.utils.ItemBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SwordOfDeathListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        ItemStack item = event.getItem();
        if (!(ItemBuilder.isCustomItem(item, ItemEnum.SWORD_OF_DEATH))) return;

        System.out.println("SWORD_OF_DEATH RIGHT CLICK");
    }
}
