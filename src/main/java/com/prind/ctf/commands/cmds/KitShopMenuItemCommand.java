package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.menus.items.KitShopMenuItem;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KitShopMenuItemCommand {

    @CommandMethod("ctf kitshopmenuitem")
    @CommandDescription("gets the kit shop menu item")
    public void KitShopMenuItemCommand(final @NonNull Player player) {
        player.getInventory().addItem(KitShopMenuItem.get());
    }
}
