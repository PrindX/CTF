package com.prind.ctf.commands.cmds.game;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.entity.Player;

public class GameLeaveCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();

    @CommandMethod("ctf leave")
    public void onJoinCommand(Player player) {
        Game game = gameManager.getGameByPlayer(player);
        if (game == null) {
            ChatUtil.message(player, "&cYou are not in a game!");
            return;
        }

        game.leaveGame(player);
    }
}
