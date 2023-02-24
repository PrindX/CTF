package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.menus.items.KitSelectionMenuItem;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KitSelectionMenuItemCommand {

    @CommandMethod("ctf kitselectionmenuitem")
    @CommandDescription("gets the kit selection menu item")
    public void onKitSelectionMenuItemCommand(final @NonNull Player player) {
        player.getInventory().addItem(KitSelectionMenuItem.get());
    }
}
