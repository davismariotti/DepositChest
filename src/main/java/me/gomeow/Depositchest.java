package me.gomeow;

import java.util.ArrayList;
import java.util.HashMap;

import me.gomeow.commands.DCCommand;
import me.gomeow.listener.BlockBreakListener;
import me.gomeow.listener.ExplosionListener;
import me.gomeow.listener.InteractListener;
import me.gomeow.listener.InventoryClickListener;
import me.gomeow.listener.InventoryMoveItemListener;
import me.gomeow.util.ChestsConfig;

import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * To whoever approves this:
 * Thank you guys so much for helping with BukkitDev!
 * 
 * Thanks,
 * -gomeow
 */



public class Depositchest extends JavaPlugin implements Listener {

	public BlockFace[] bfs = {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
	
	public HashMap<String, String> setChest = new HashMap<String, String>();
	public ArrayList<String> info = new ArrayList<String>();
	public ArrayList<String> removeCmd = new ArrayList<String>();
	public Integer maxChests;
	public boolean infChests = false;
	
	public FileConfiguration chests;
	public ChestsConfig cc;
	
	@Override
	public void onEnable() {
		cc = new ChestsConfig(this);
		cc.reloadChests();
		cc.saveChests();
		chests = cc.chests;
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new InteractListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryMoveItemListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplosionListener(this), this);
		saveConfig();
		this.infChests = this.getConfig().getBoolean("Infinite-Deposit-Chests", false);
		if(!this.infChests) {
			this.maxChests = getConfig().getInt("Maximum-Chests", 3);
		}
	}
	
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmdObj, String label, String[] args) {
		String cmd = cmdObj.getName();
		if(cmd.equalsIgnoreCase("dc")) {
			DCCommand command = new DCCommand(this);
			return command.execute(cs, args);
		}
		return false;
	}
}
