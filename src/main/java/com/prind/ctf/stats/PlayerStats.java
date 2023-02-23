package com.prind.ctf.stats;

import com.prind.ctf.kits.impl.StrikerKit;
import com.prind.ctf.kits.Kit;
import com.prind.ctf.kits.KitManager;
import com.prind.ctf.kits.enums.KitEnum;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerStats {

  private final UUID uuid;

  private int deaths;
  private int kills;
  private int wins;
  private int coins;

  private Kit selectedKit = null;
  private Set<Kit> unlockedKits = new HashSet<>();

  public PlayerStats(UUID uuid) {
    this.uuid = uuid;
    this.unlockedKits.add(new StrikerKit());
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

  public int getCoins() {
    return coins;
  }

  public Kit getSelectedKit() {
    return selectedKit;
  }

  public Set<Kit> getUnlockedKitsSet() {
    return unlockedKits;
  }

  public String getUnlockedKitsString() {
    StringBuilder kits = new StringBuilder();

    for (Kit kit : unlockedKits) {
      kits.append(kit.getKitEnum()).append(",");
    }

    return kits.toString();
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

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public void setSelectedKit(Kit selectedKit) {
    this.selectedKit = selectedKit;
  }

  public void setUnlockedKitsSet(Set<Kit> unlockedKits) {
    this.unlockedKits = unlockedKits;
  }

  public void SetUnlockedKitsString(String str) {
    if (str.equals("")) return;

    String[] kitEnumsString = str.split(",");

    Set<Kit> newUnlockedKits = new HashSet<>();

    for (String kitEnum : kitEnumsString) {
      newUnlockedKits.add(KitManager.getKitByEnum(KitEnum.valueOf(kitEnum)));
    }

    this.unlockedKits = newUnlockedKits;
  }

  public void addUnlockedKit(Kit kit) {
    this.unlockedKits.add(kit);
  }

}
