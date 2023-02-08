package com.prind.ctf.game.tasks;

import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private final Game game;
    private final FileConfiguration config = CTF.getInstance().getConfig();

    public CountdownTask(Game game) {
        this.game = game;
    }

    private long countdownTime = 10;

    @Override
    public void run() {
        countdownTime--;
        if (countdownTime <= 0){
            game.startGame();
            cancel();
            return;
        }

        for (Player player : game.getPlayers()) {
            ChatUtil.message(player, config.getString("messages.countdown").replace("%time%", String.valueOf(countdownTime)));
        }
    }
}
