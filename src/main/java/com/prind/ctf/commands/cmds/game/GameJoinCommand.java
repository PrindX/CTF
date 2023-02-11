package com.prind.ctf.commands.cmds.game;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.entity.Player;

public class GameJoinCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();

    @CommandMethod("ctf join <game>")
    public void onJoinCommand(Player player, @Argument("game")String gameName) {
        Game game = gameManager.getGameByName(gameName);
        if (game == null) {
            ChatUtil.log("&c&lSomeone tried to join: Game - " + gameName + " but it doesn't exist");
            return;
        }

        game.joinGame(player);
    }
}
