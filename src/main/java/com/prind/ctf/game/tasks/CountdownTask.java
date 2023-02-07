package com.prind.ctf.game.tasks;

import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import com.prind.ctf.util.TimeUtil;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private final Game game;
    private final FileConfiguration config = CTF.getInstance().getConfig();

    public CountdownTask(Game game) {
        this.game = game;
    }

    private int countdownTime = 6;

    @Override
    public void run() {
        countdownTime--;
        if (countdownTime <= 0){
            cancel();
            game.setGameState(GameState.ACTIVE);

            game.assignTeams();
            game.spawnLocations();
            return;
        }

        for (Player player : game.getPlayers()) {
            ChatUtil.message(player, config.getString("countdown").replace("%time%", TimeUtil.formatDurationLong(countdownTime)));
        }
    }
}
