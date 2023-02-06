package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.manager.GameManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameJoinCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();

    @CommandMethod("game join <game>")
    public void onJoinCommand(CommandSender sender, Player player, @Argument("game")String gameName) {
        Game game = gameManager.getGameByName(gameName);
        game.joinGame(player);
        System.out.println("Game Join command has ran for Game: " + game.getDisplayName());
    }
}
