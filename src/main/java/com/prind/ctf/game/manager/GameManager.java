package com.prind.ctf.game.manager;

import com.prind.ctf.CTF;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Getter
public class GameManager {

    private final CTF instance;
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
            ChatUtil.log("&cCannot register: " + game.getDisplayName() + " game limit is reached");
            return;
        }

        games.add(game);
        ChatUtil.log("&aGame: " + game.getDisplayName() + " has been registered.");
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
