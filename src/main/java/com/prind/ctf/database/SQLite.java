package com.prind.ctf.database;

import com.prind.ctf.CTF;
import com.prind.ctf.stats.PlayerStats;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SQLite implements Database {

    private String url;

    private Connection connection;

    public SQLite() {
        File folder = new File(CTF.getInstance().getDataFolder() + "/Cache");
        if (!folder.exists())
            if (!folder.mkdir())
                CTF.getInstance().getLogger().severe("Could not create /Cache folder");
        File dataFolder = new File(folder.getPath() + "/ctf.db");
        if (!dataFolder.exists()) {
            try {
                if (!dataFolder.createNewFile())
                    CTF.getInstance().getLogger().severe("Could not create /Cache/ctf.db file");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        this.url = "jdbc:sqlite:" + dataFolder;

        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException)
                CTF.getInstance().getLogger().severe("Could not find the SQLite Driver on your system");
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        try {
            checkConnection();

            String sql = "CREATE TABLE IF NOT EXISTS stats (uuid VARCHAR(36) PRIMARY KEY, deaths INT, kills INT, wins INT, unlocked_kits VARCHAR(200))";
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

        try {
            checkConnection();

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

        try {
            checkConnection();

            if(hasStats(stats.getUuid())) {
                sql = "UPDATE stats SET deaths=?, kills=?, wins=?, unlocked_kits=? WHERE uuid=?;";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, stats.getDeaths());
                    statement.setInt(2, stats.getKills());
                    statement.setInt(3, stats.getWins());
                    statement.setString(4, stats.getUuid().toString());
                    statement.setString(5, stats.getUnlockedKitsString());
                    statement.executeUpdate();
                }
            } else {
                sql = "INSERT INTO stats (uuid, deaths, kills, wins, unlocked_kits) VALUES (?, ?, ?, ?, ?);";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, stats.getUuid().toString());
                    statement.setInt(2, stats.getDeaths());
                    statement.setInt(3, stats.getKills());
                    statement.setInt(4, stats.getWins());
                    statement.setString(5, stats.getUnlockedKitsString());
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
        String sql = "SELECT `deaths`, `kills`, `wins`, `unlocked_kits` FROM stats WHERE `uuid`=?;";
        try {
            checkConnection();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, uuid.toString());
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        stats.setDeaths(result.getInt("deaths"));
                        stats.setKills(result.getInt("kills"));
                        stats.setWins(result.getInt("wins"));
                        stats.SetUnlockedKitsString(result.getString("unlocked_kits"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }


    public void checkConnection() throws SQLException {
        boolean renew = false;

        if (this.connection == null)
            renew = true;
        else
            if (this.connection.isClosed())
                renew = true;

        if (renew)
            this.connection = DriverManager.getConnection(url);
    }
}
