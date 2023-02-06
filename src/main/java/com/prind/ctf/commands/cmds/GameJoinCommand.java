package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameJoinCommand {

    @CommandMethod("game join <game>")
    public void onJoinCommand(CommandSender sender, Player player, @Argument("game")String gameName) {

    }
}
