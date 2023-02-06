package com.prind.ctf.stats;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class StatsManager {

  private final HashMap<UUID, PlayerStats> stats = new HashMap<>();

  public void remove(UUID uuid) {
    stats.remove(uuid);
  }

  public void put(UUID uuid, PlayerStats playerStats) {
    stats.put(uuid, playerStats);
  }

  @NotNull
  public PlayerStats get(UUID uuid) {
    PlayerStats playerStats = stats.get(uuid);
    if (playerStats == null) {
      throw new IllegalStateException("Trying to get stats data of an unloaded player!");
    }
    return playerStats;
  }
}
