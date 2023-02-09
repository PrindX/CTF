package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.menus.KitSelectionMenu;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KitCommand {

    @CommandMethod("ctf kit")
    @CommandDescription("Opens the kit selection menu")
    public void onKitCommand(
        final @NonNull Player player
        ) {
        new KitSelectionMenu(player);
    }
}
