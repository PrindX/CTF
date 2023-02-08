package com.prind.ctf.game;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.prind.ctf.CTF;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Team {

    private int id;

    private int flagsCaptured = 0;

    private final List<Player> players = new ArrayList<>();
    private final List<Player> playerIsKing = new ArrayList<>();

    public Team(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addKing(Player player) {
        playerIsKing.add(player);
    }

    public void sendPlayers(String world, double x, double y, double z) {
        MVWorldManager worldManager = CTF.getInstance().getMultiverseCore().getMVWorldManager();
        MultiverseWorld gameWorld = worldManager.getMVWorld(world);

        for (Player player : players) {
            player.teleport(new Location(gameWorld.getCBWorld(), x, y, z));
        }
    }

    public void addFlags(int amount) {
        setFlagsCaptured(getFlags() + amount);
    }

    public int getFlags() {
        return getFlagsCaptured();
    }

    public boolean hasWon() {
        return getFlags() >= 3;
    }
}
