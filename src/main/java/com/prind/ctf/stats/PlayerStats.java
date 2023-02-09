package com.prind.ctf.stats;

import com.prind.ctf.kits.Kit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerStats {

  private final UUID uuid;

  private int deaths;
  private int kills;
  private int wins;

  private Set<Kit> unlockedKits = new HashSet<>();

  public PlayerStats(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUuid() {
    return uuid;
  }

  public int getDeaths() {
    return deaths;
  }

  public int getKills() {
    return kills;
  }

  public int getWins() {
    return wins;
  }

  public Set<Kit> getUnlockedKits() {
    return unlockedKits;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  public void setUnlockedKits(Set<Kit> unlockedKits) {
    this.unlockedKits = unlockedKits;
  }

  public void addUnlockedKit(Kit kit) {
    this.unlockedKits.add(kit);
  }

}
