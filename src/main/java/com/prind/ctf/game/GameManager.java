package com.prind.ctf.game;

import com.prind.ctf.CTF;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.game.tasks.CountdownTask;
import com.prind.ctf.util.ChatUtil;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
public class GameManager {

    private final CTF instance;private final FileConfiguration config = CTF.getInstance().getConfig();

    private final HashSet<Game> games;
    private final HashMap<Player, Game> gameMap;

    private final int gameLimit = 100;

    public GameManager(CTF instance) {
        this.instance = instance;
        this.games = new HashSet<>();
        this.gameMap = new HashMap<>();
    }

    public void registerGames(Game game) {
        if (games.size() == gameLimit) {
            ChatUtil.log(config.getString("messages.game-limit-reached")
                    .replace("{game}", game.getDisplayName()));
            return;
        }

        games.add(game);
        ChatUtil.log(config.getString("messages.game-registered")
                .replace("{game}", game.getDisplayName()));
    }

    public void joinGame(Game game, Player player) {
        if (game.isState(GameState.LOBBY) || game.isState(GameState.STARTING)) {

            int current_size = game.getPlayers().size();
            if (current_size >= game.getMaxPlayers()) {
                ChatUtil.message(player, config.getString("messages.game-is-full"));
                return;
            }

            if (game.getPlayers().contains(player)) {
                ChatUtil.message(player, config.getString("messages.cannot-join-game"));
                return;
            }

            game.getPlayers().add(player);
            game.sendToWaiting(player);

            if (current_size >= game.getMinPlayers() && !game.isState(GameState.STARTING)) {
                game.setGameState(GameState.STARTING);
                CountdownTask countdownTask = new CountdownTask(game);
                countdownTask.runTaskTimer(CTF.getInstance(), 0, 20);
            } else {
                int players_needed = game.getMinPlayers() - current_size;
                game.getPlayers().forEach(p -> {
                    p.sendActionBar(ChatUtil.translate(config.getString("messages.players-needed")
                            .replace("{amount}", String.valueOf(players_needed))));
                });
            }

            setGame(player, game);
        }
    }

    public Game getGameByPlayer(Player player) {
        for (Game game : games) {
            if (game.getPlayers().contains(player)) {
                return game;
            }
        }

        return null;
    }

    public Game getGameByName(String gameName) {
        for (Game game : games) {
            if (game.getDisplayName().equalsIgnoreCase(gameName)) {
                return game;
            }
        }
        return null;
    }

    public void setGame(Player player, Game game) {
        if (game != null) {
            this.gameMap.put(player, game);
        } else this.gameMap.remove(player);
    }

    public Set<Game> getGames() {
        return games;
    }
}
