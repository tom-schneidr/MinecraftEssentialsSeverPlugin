package io.essentials.data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.essentials.Essentials;

public class ConfigManager {

    private FileConfiguration config;
    private File configfile;
    
    public void setupFiles() {
        configfile = new File(Essentials.getInstance().getDataFolder(), "config.yml");
        System.out.println(configfile);

        if (!this.configfile.exists()) {
            this.configfile.getParentFile().mkdirs();
            Essentials.getInstance().saveResource("config.yml", false);
            Bukkit.getLogger().log(Level.FINE, "config.yml file created");
        }

        this.config = new YamlConfiguration();
        System.out.println(config);

        try {
            this.config.load(this.configfile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
    }

    public void saveConfig() {
        try {
            this.config.save(this.configfile);
            Bukkit.getLogger().log(Level.FINE, "Config.yml has been saved");
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Config.yml could not be saved");
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

}