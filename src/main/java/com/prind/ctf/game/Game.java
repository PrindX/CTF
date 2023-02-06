package com.prind.ctf.game;

import com.prind.ctf.CTF;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.util.ChatUtil;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Game {

    private final FileConfiguration gameConfig = CTF.getInstance().getGameConfig().getConfiguration();
    private GameState gameState = GameState.LOBBY;

    // Basic information
    private final String displayName;
    private final int maxPlayers;
    private final int minPlayers;

    // Live Information
    private final List<Player> players = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();

    public Game(String gameName) {
        this.displayName = gameConfig.getString("games." + gameName + ".displayName");
        this.maxPlayers = gameConfig.getInt("games." + gameName + ".maxPlayers");
        this.minPlayers = gameConfig.getInt("games." + gameName + ".minPlayers");

        createTeams();
    }

    public void createTeams() {
        teams.add(new Team(1));
        teams.add(new Team(2));
    }

    public Team getTeamById(int id) {
        for (Team team : teams) {
            if (team.getId() == id) return team;
        }
        return null;
    }

    public void assignTeams() {
        for (int i = 0; i < players.size(); i++) {
            int teamId = (i % 2) + 1;
            Team team = getTeamById(teamId);

            if (team.getPlayers().contains(players.get(i))) continue;
            team.addPlayer(players.get(i));

            int rand = ThreadLocalRandom.current().nextInt(team.getPlayers().size());
            Player player = team.getPlayers().get(rand);
            team.addKing(player);
        }
    }

    public void spawnLocations() {
        for (Team team : teams) {
            String world = gameConfig.getString("games." + getDisplayName() + ".teams." + team.getId() + ".world");
            double x = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".x");
            double y = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".y");
            double z = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".z");

            team.sendPlayers(world, x, y, z);
        }
    }

    public void joinGame(Player player) {
        if (isState(GameState.LOBBY) || isState(GameState.STARTING)) {
            System.out.println("JoinGame method beginning");
            if (players.size() >= maxPlayers) {
                ChatUtil.message(player, "&cGame is full, Cannot Join!");
                return;
            }

            if (players.contains(player)) {
                ChatUtil.message(player, "&cYou are already in this game!");
                return;
            }
            players.add(player);

        /*
        Implement kits (clear invs, give kit items, blah blah)
         */

            if (players.size() >= minPlayers && !isState(GameState.STARTING)) {
                setGameState(GameState.STARTING);
                startCountdown();
            }

            CTF.getInstance().getGameManager().setGame(player, this);
        }
    }

    public void startCountdown() {
        new BukkitRunnable() {
            int time = 6;
            @Override
            public void run() {
                time--;
                if (time <= 0) {
                    cancel();
                    setGameState(GameState.ACTIVE);;
                    assignTeams();
                    spawnLocations();
                    return;
                }

                for (Player player : players) {
                    ChatUtil.message(player, gameConfig.getString("countdown").replace("%time%", String.valueOf(time)));
                }
            }
        }.runTaskTimer(CTF.getInstance(), 0, 20);
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public boolean isState(GameState state) {
        return getGameState() == state;
    }
}
