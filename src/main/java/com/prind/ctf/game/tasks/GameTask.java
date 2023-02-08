package com.prind.ctf.game.tasks;

import com.prind.ctf.game.Game;
import com.prind.ctf.game.Team;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final Game game;

    public GameTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (!game.isState(GameState.ACTIVE)) {
            cancel();
            return;
        }
        for (int i = 0; i < game.getTeams().size() - 1; i++) {
            Team teamOne = game.getTeams().get(i);
            Team teamTwo = game.getTeams().get(i + 1);

            if (teamOne.getFlags() > teamTwo.getFlags()) {
                if (!teamOne.hasWon()) return;
                game.endGame();

                teamOne.getPlayers().forEach(player -> {
                    ChatUtil.message(player, "&aYou're team has won!!");
                });
                teamTwo.getPlayers().forEach(player -> {
                    ChatUtil.message(player, "&cYou're team has lost!!");
                });
                cancel();

            } else if (teamTwo.getFlags() > teamOne.getFlags()) {
                if (!teamTwo.hasWon()) return;

                game.endGame();

                teamTwo.getPlayers().forEach(player -> {
                    ChatUtil.message(player, "&aYou're team has won!!");
                });
                teamOne.getPlayers().forEach(player -> {
                    ChatUtil.message(player, "&cYou're team has lost!!");
                });
                cancel();

            }
        }
        /*
        Powers ups
         */

    }
}
