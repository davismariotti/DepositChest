package info.gomeow;

import info.gomeow.commands.DCCommand;
import info.gomeow.listener.BlockBreakListener;
import info.gomeow.listener.ExplosionListener;
import info.gomeow.listener.InteractListener;
import info.gomeow.listener.InventoryClickListener;
import info.gomeow.listener.InventoryMoveItemListener;
import info.gomeow.util.ChestsConfig;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Depositchest extends JavaPlugin implements Listener {

    public BlockFace[] faces = {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};

    public HashMap<String, String> setChest = new HashMap<String, String>();
    public ArrayList<String> info = new ArrayList<String>();
    public ArrayList<String> removeCmd = new ArrayList<String>();
    public Integer maxChests;
    public boolean infiniteChests = false;

    public FileConfiguration chests;
    public ChestsConfig chestConfig;

    @Override
    public void onEnable() {
        chestConfig = new ChestsConfig(this);
        chestConfig.reloadChests();
        chestConfig.saveChests();
        chests = chestConfig.chests;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryMoveItemListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplosionListener(this), this);
        saveConfig();
        if(!(infiniteChests = this.getConfig().getBoolean("Infinite-Deposit-Chests", false))) {
            maxChests = getConfig().getInt("Maximum-Chests", 3);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmdObj, String label, String[] args) {
        return new DCCommand(this).execute(sender, args);
    }
}
