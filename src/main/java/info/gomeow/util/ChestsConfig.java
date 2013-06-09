package info.gomeow.util;

import info.gomeow.Depositchest;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ChestsConfig {

    public Depositchest plugin;
    public File chestsFile;
    public FileConfiguration chests;

    public ChestsConfig(Depositchest plugin) {
        this.plugin = plugin;
    }

    public void reloadChests() {
        if(chestsFile == null) {
            chestsFile = new File(plugin.getDataFolder(), "chests.yml");
        }
        chests = YamlConfiguration.loadConfiguration(chestsFile);
    }

    public FileConfiguration getChests() {
        if(chests == null) {
            this.reloadChests();
        }
        return chests;
    }

    public void saveChests() {
        if(chests == null || chests == null) {
            return;
        }
        try {
            getChests().save(chestsFile);
        } catch(IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + chests, ex);
        }
    }

}
