package com.prind.ctf.game;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.prind.ctf.CTF;
import com.prind.ctf.game.enums.GameState;
import com.prind.ctf.game.tasks.CountdownTask;
import com.prind.ctf.game.tasks.GameTask;
import com.prind.ctf.util.ChatUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Game {

    private final FileConfiguration gameConfig = CTF.getInstance().getGameConfig().getConfiguration();
    private final FileConfiguration config = CTF.getInstance().getConfig();

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

    public void startGame() {
        if (!isState(GameState.STARTING)) return;
        setGameState(GameState.ACTIVE);

        assignTeams();
        sendToArena();

        GameTask gameTask = new GameTask(this);
        gameTask.runTaskTimer(CTF.getInstance(), 0, 20);
    }

    public void endGame() {
        if (!isState(GameState.ACTIVE)) {
            return;
        }
        setGameState(GameState.LOBBY);
        sendToSpawn();

        for (Player player : players) {
            players.remove(player);
        }

        // Add stats to player
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

            if (team.getPlayers().contains(players.get(i))) return;
            team.addPlayer(players.get(i));

            int rand = ThreadLocalRandom.current().nextInt(team.getPlayers().size());
            Player player = team.getPlayers().get(rand);
            player.sendActionBar(ChatUtil.translate(config.getString("messages.king-selected")));
            team.addKing(player);
        }
    }

    public void joinGame(Player player) {
        if (isState(GameState.LOBBY) || isState(GameState.STARTING)) {
            if (players.size() >= maxPlayers) {
                ChatUtil.message(player, config.getString("messages.game-is-full"));
                return;
            }

            if (players.contains(player)) {
                ChatUtil.message(player, config.getString("messages.cannot-join-game"));
                return;
            }

            players.add(player);
            sendToWaiting(player);

            if (players.size() >= minPlayers && !isState(GameState.STARTING)) {
                setGameState(GameState.STARTING);
                CountdownTask countdownTask = new CountdownTask(this);
                countdownTask.runTaskTimer(CTF.getInstance(), 0, 20);
            } else {
                int players_needed = minPlayers - players.size();
                players.forEach(p -> {
                    p.sendActionBar(ChatUtil.translate(config.getString("messages.players-needed")
                            .replace("{amount}", String.valueOf(players_needed))));
                });
            }

            CTF.getInstance().getGameManager().setGame(player, this);
        }
    }

    public void sendToWaiting(Player player) {
        MVWorldManager worldManager = CTF.getInstance().getMultiverseCore().getMVWorldManager();

        String world = config.getString("locations.waiting-area.world");
        double x = config.getDouble("locations.waiting-area.x");
        double y = config.getDouble("locations.waiting-area.y");
        double z = config.getDouble("locations.waiting-area.z");

        player.teleport(new Location(worldManager.getMVWorld(world).getCBWorld(), x, y, z));
    }

    public void sendToSpawn() {
        for (Team team : teams) {
            String world = config.getString("locations.spawn.world");
            double x = config.getDouble("locations.spawn.x");
            double y = config.getDouble("locations.spawn.y");
            double z = config.getDouble("locations.spawn.z");

            team.sendPlayers(world, x, y, z);
        }
    }

    public void sendToArena() {
        for (Team team : teams) {
            String world = gameConfig.getString("games." + getDisplayName() + ".teams." + team.getId() + ".world");
            double x = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".x");
            double y = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".y");
            double z = gameConfig.getDouble("games." + getDisplayName() + ".teams." + team.getId() + ".z");

            team.sendPlayers(world, x, y, z);
        }
    }


    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public boolean isState(GameState state) {
        return getGameState() == state;
    }
}
