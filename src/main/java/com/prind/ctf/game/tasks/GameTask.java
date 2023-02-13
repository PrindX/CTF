package com.prind.ctf.game.tasks;

import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final Game game;
    private final FileConfiguration config = CTF.getInstance().getConfig();

    public GameTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (!game.isState(GameState.ACTIVE) || game.getPlayers().isEmpty()) {
            cancel();
            return;
        }
        for (int i = 0; i < game.getTeams().size() - 1; i++) {
            Team teamOne = game.getTeams().get(i);
            Team teamTwo = game.getTeams().get(i + 1);

            if (teamOne.getFlags() > teamTwo.getFlags()) {
                if (!teamOne.hasWon()) return;
                gameOverMessage(teamOne, config.getString("messages.team-won"));
                gameOverMessage(teamTwo, config.getString("messages.team-lost"));
                game.endGame();
                cancel();

            } else if (teamTwo.getFlags() > teamOne.getFlags()) {
                if (!teamTwo.hasWon()) return;

                gameOverMessage(teamTwo, config.getString("messages.team-won"));
                gameOverMessage(teamOne, config.getString("messages.team-lost"));
                game.endGame();
                cancel();

            }
        }
        /*
        Powers ups
         */

    }

    private void gameOverMessage(Team team, String s) {
        team.getPlayers().forEach(player -> {
            ChatUtil.message(player, s);
        });
    }
}
