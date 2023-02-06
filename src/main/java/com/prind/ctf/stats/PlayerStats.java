package com.prind.ctf.stats;

import java.util.UUID;

public class PlayerStats {

  private final UUID uuid;

  private int deaths;
  private int kills;
  private int wins;

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

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }
}
