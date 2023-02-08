package com.prind.ctf;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.prind.ctf.commands.CommandManager;
import com.prind.ctf.game.Game;
import com.prind.ctf.game.manager.GameManager;
import com.prind.ctf.util.ConfigUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CTF extends JavaPlugin {

    private ConfigUtil gameConfig;

    private GameManager gameManager;
    private MultiverseCore multiverseCore;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.gameConfig = new ConfigUtil(this, "game", this.getDataFolder().getAbsolutePath());
        this.multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        this.gameManager = new GameManager(this);

        for (String key : gameConfig.getConfiguration().getConfigurationSection("games").getKeys(false)) {
            Game game = new Game(gameConfig.getConfiguration().getString("games." + key + ".displayName"));
            gameManager.registerGames(game);
        }

        new CommandManager(this);

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CTF getInstance() {
        return getPlugin(CTF.class);
    }
}
