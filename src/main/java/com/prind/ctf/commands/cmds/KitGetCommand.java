package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.kits.KitManager;
import com.prind.ctf.kits.enums.KitEnum;
import com.prind.ctf.stats.PlayerStats;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KitGetCommand {

    @CommandMethod("kit get <kit>")
    @CommandDescription("Get the kit you want")
    public void onKitGetCommand(
        final @NonNull Player player,
        final @NonNull @Argument("kit")KitEnum kitEnum
        ) {
        PlayerStats stats = CTF.getInstance().getStatsManager().get(player.getUniqueId());
        stats.addKit(KitManager.getKitByEnum(kitEnum));
    }
}
