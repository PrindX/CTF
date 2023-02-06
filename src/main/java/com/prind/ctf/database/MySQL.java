package com.prind.ctf.database;

import com.prind.ctf.CTF;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.prind.ctf.stats.PlayerStats;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MySQL implements Database{

  private HikariDataSource dataSource;

  private final String host;
  private final String database;
  private final String user;
  private final String pass;
  private final int port;
  private final boolean ssl;
  private final boolean certificateVerification;
  private final int poolSize;
  private final int maxLifetime;

  public MySQL() {
    FileConfiguration config = CTF.getInstance().getConfig();

    this.host = config.getString("database.host");
    this.database = config.getString("database.database");
    this.user = config.getString("database.user");
    this.pass = config.getString("database.pass");
    this.port = config.getInt("database.port");
    this.ssl = config.getBoolean("database.ssl");
    this.certificateVerification = config.getBoolean("database.verify-certificate", true);
    this.poolSize = config.getInt("database.pool-size", 10);
    this.maxLifetime = config.getInt("database.max-lifetime", 1800);
  }

  @Override
  public boolean connect() {
    HikariConfig config = new HikariConfig();

    config.setPoolName("CTFMySql");

    config.setMaximumPoolSize(poolSize);
    config.setMaxLifetime(maxLifetime * 1000L);

    config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
    config.setUsername(pass);
    config.setPassword(user);

    config.addDataSourceProperty("useSSL", String.valueOf(ssl));
    if (!certificateVerification) {
      config.addDataSourceProperty("verifyServerCertificate", String.valueOf(false));
    }

    config.addDataSourceProperty("characterEncoding", "utf8");
    config.addDataSourceProperty("encoding", "UTF-8");
    config.addDataSourceProperty("useUnicode", "true");

    config.addDataSourceProperty("rewriteBatchedStatements", "true");
    config.addDataSourceProperty("jdbcCompliantTruncation", "false");

    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "275");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    // Recover if connection gets interrupted
    config.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));

    dataSource = new HikariDataSource(config);

    try {
      dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public void init() {
    try (Connection connection = dataSource.getConnection()) {
      String sql = "CREATE TABLE IF NOT EXISTS stats (uuid VARCHAR(36) PRIMARY KEY, deaths INT, kills INT, wins INT)";
      try (Statement statement = connection.createStatement()) {
        statement.executeUpdate(sql);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean hasStats(UUID uuid) {
    String sql = "SELECT uuid FROM stats WHERE uuid = ?;";
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, uuid.toString());
        try (ResultSet result = statement.executeQuery()) {
          return  result.next();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean saveStats(PlayerStats stats) {
    String sql;

    try (Connection connection = dataSource.getConnection()) {
      if(hasStats(stats.getUuid())) {
        sql = "UPDATE stats SET deaths=?, kills=?, wins=? WHERE uuid=?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setInt(1, stats.getDeaths());
          statement.setInt(2, stats.getKills());
          statement.setInt(3, stats.getWins());
          statement.setString(4, stats.getUuid().toString());
          statement.executeUpdate();
        }
      } else {
        sql = "INSERT INTO stats (uuid, deaths, kills, wins) VALUES (?, ?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
          statement.setString(1, stats.getUuid().toString());
          statement.setInt(2, stats.getDeaths());
          statement.setInt(3, stats.getKills());
          statement.setInt(4, stats.getWins());
          statement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  @Override
  public PlayerStats fetchStats(UUID uuid) {
    PlayerStats stats = new PlayerStats(uuid);
    String sql = "SELECT `deaths`, `kills`, `wins` FROM stats WHERE `uuid`=?;";
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, uuid.toString());
        try (ResultSet result = statement.executeQuery()) {
          if (result.next()) {
            stats.setDeaths(result.getInt("deaths"));
            stats.setKills(result.getInt("kills"));
            stats.setWins(result.getInt("wins"));
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return stats;
  }
}
