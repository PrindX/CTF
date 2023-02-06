package com.prind.ctf.database;

import com.prind.ctf.stats.PlayerStats;

import java.util.UUID;

public interface Database {

  void init();

  boolean hasStats(UUID uuid);

  boolean saveStats(PlayerStats stats);

  PlayerStats fetchStats(UUID uuid);

  boolean connect();

}
