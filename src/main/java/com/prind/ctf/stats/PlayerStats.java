package com.prind.ctf.stats;

import com.prind.ctf.kits.Kit;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class PlayerStats {

  private final UUID uuid;

  private int deaths;
  private int kills;
  private int wins;

  private Set<Kit> kits;

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

  public Set<Kit> getKits() {
    return kits;
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

  public void setKits(Set<Kit> kits) {
    this.kits = kits;
  }

  public void addKit(Kit kit) {
    this.kits.add(kit);
  }

}
