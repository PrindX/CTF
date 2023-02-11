package com.prind.ctf.commands.cmds.game;

import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GameListCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();
    private FileConfiguration config = CTF.getInstance().getConfig();

    @CommandMethod("game list")
    public void onJoinCommand(Player player) {
        for (String s : config.getStringList("messages.game-display")) {
            if (!s.startsWith("{GAME-DISPLAY}")) {
                ChatUtil.message(player, s);
                continue;
            }

            String rawContent = s.replace("{GAME-DISPLAY} ", "");
            for (Game game : gameManager.getGames()) {
                ChatUtil.message(player, rawContent
                        .replace("{gameName}", game.getDisplayName())
                        .replace("{size}", String.valueOf(game.getPlayers().size())));
            }
        }
    }
}
