package com.prind.ctf;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.prind.ctf.commands.CommandManager;
import com.prind.ctf.database.Database;
import com.prind.ctf.database.MySQL;
import com.prind.ctf.database.SQLite;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.event.GameEvent;
import com.prind.ctf.game.GameManager;
import com.prind.ctf.kits.listeners.SwordOfDeathListener;
import com.prind.ctf.kits.listeners.WarperSwordListener;
import com.prind.ctf.kits.listeners.WizardWandListener;
import com.prind.ctf.menus.listeners.KitSelectionMenuItemListener;
import com.prind.ctf.menus.listeners.KitSelectionMenuListener;
import com.prind.ctf.menus.listeners.KitShopMenuItemListener;
import com.prind.ctf.menus.listeners.KitShopMenuListener;
import com.prind.ctf.stats.StatsManager;
import com.prind.ctf.util.ConfigUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CTF extends JavaPlugin {

    private ConfigUtil gameConfig;

    private GameManager gameManager;
    private MultiverseCore multiverseCore;

    private StatsManager statsManager;
    private Database remoteDatabase;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.statsManager = new StatsManager();
        this.gameConfig = new ConfigUtil(this, "game", this.getDataFolder().getAbsolutePath());
        this.multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        this.gameManager = new GameManager(this);

        for (String key : gameConfig.getConfiguration().getConfigurationSection("games").getKeys(false)) {
            Game game = new Game(gameConfig.getConfiguration().getString("games." + key + ".displayName"));
            gameManager.registerGames(game);
        }

        new CommandManager(this);

        saveDefaultConfig();

        initDatabase();

        registerListeners();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new KitSelectionMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new KitShopMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SwordOfDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new WizardWandListener(), this);
        Bukkit.getPluginManager().registerEvents(new WarperSwordListener(), this);
        Bukkit.getPluginManager().registerEvents(new KitShopMenuItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new KitSelectionMenuItemListener(), this);
    }

    private void initDatabase() {
        if (getConfig().getBoolean("database.enable")) {
            MySQL mySQL = new MySQL();
            long time = System.currentTimeMillis();
            if (!mySQL.connect()) {
                this.getLogger().severe("Could not connect to the database! Please verify your credentials and make sure that the server IP is whitelisted in MySQL.");
                remoteDatabase = new SQLite();
            } else {
                remoteDatabase = mySQL;
            }
            if (System.currentTimeMillis() - time >= 5000) {
                this.getLogger().severe("It took " + (System.currentTimeMillis() - time) + " ms to establish a database connection\n" +
                        "Using this remote connection is not recommended");
            }
            remoteDatabase.init();

            this.getLogger().info("Connected to the database in " + (System.currentTimeMillis() - time) + " ms (MySQL)");
        } else {
            remoteDatabase = new SQLite();
            remoteDatabase.init();
        }
    }

    public Database getRemoteDatabase() {
        return remoteDatabase;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public static CTF getInstance() {
        return getPlugin(CTF.class);
    }
}
