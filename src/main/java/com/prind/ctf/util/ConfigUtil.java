package com.prind.ctf.util;

import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.*;

public class ConfigUtil {

    private File file;
    private String name;
    private String directory;
    private YamlConfiguration configuration;

    public ConfigUtil(JavaPlugin plugin, String name, String directory) {
        this.setName(name);
        this.setDirectory(directory);
        this.file = new File(directory, name + ".yml");
        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setConfiguration(YamlConfiguration configuration) {
        this.configuration = configuration;
    }

}