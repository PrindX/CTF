package com.prind.ctf.game;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {

    private int id;

    private List<Player> players = new ArrayList<>();
    private List<Player> playerIsKing = new ArrayList<>();

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
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        MultiverseWorld gameWorld = worldManager.getMVWorld(world);

        for (Player player : players) {
            player.teleport(new Location(gameWorld.getCBWorld(), x, y, z));
        }
    }
}
