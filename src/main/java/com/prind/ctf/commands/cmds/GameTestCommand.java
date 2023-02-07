package com.prind.ctf.commands.cmds;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.manager.GameManager;
import org.bukkit.entity.Player;

public class GameTestCommand {

    private GameManager gameManager = CTF.getInstance().getGameManager();

    @CommandMethod("game test <game> <team> <points>")
    public void onJoinCommand(Player player, @Argument("game")String gameName, @Argument("team")int id, @Argument("points")int amount) {
        Game game = gameManager.getGameByName(gameName);

        Team team = game.getTeamById(id);
        team.addFlags(amount);
        System.out.println("COmmand ran");
    }
}
