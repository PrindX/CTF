package com.prind.ctf.commands.cmds.game;

import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GameLeaveCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();
    private FileConfiguration config = CTF.getInstance().getConfig();

    @CommandMethod("ctf leave")
    public void onLeaveCommand(Player player) {
        Game game = gameManager.getGameByPlayer(player);
        if (game == null) {
            ChatUtil.message(player, config.getString("messages.cannot-leave-game"));
            return;
        }

        game.leaveGame(player);
    }
}
