package com.prind.ctf;

import com.prind.ctf.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CTF extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new CommandManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
