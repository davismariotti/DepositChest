package me.gomeow.Listener;

import me.gomeow.Depositchest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

	public Depositchest plugin;


	public BlockBreakListener(Depositchest plugin) {
		this.plugin = plugin;
	}
	

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		if(b.getType() != Material.CHEST) return;
		Location loc = b.getLocation();
		Player p = event.getPlayer();
		for(String nameKey:plugin.cc.getChests().getKeys(false)) {
			for(String key:plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false)) {
				String world = plugin.cc.getChests().getString(nameKey+"."+key+".World");
				Integer x = plugin.cc.getChests().getInt(nameKey+"."+key+".X");
				Integer y = plugin.cc.getChests().getInt(nameKey+"."+key+".Y");
				Integer z = plugin.cc.getChests().getInt(nameKey+"."+key+".Z");
				if(loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z 
						&& loc.getWorld().getName().equalsIgnoreCase(world)) {
					if(!(p.getName().equalsIgnoreCase(nameKey))) {
						if(p.hasPermission("depositchest.break")) {
							plugin.cc.getChests().set(nameKey+"."+key, null);
							p.sendMessage(ChatColor.GOLD+"You just broke a deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+" owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+".");
							plugin.cc.saveChests();
							return;
						}
						else {
							event.setCancelled(true);
							p.sendMessage(ChatColor.RED+"You do not have permission to break that chest.");
							p.sendMessage(ChatColor.RED+"It is called "+ChatColor.GOLD+key+ChatColor.RED+" and is owned by "+ChatColor.GOLD+nameKey+ChatColor.RED+".");
							return;
						}
					}
					else {
						p.sendMessage(ChatColor.GOLD+"You just broke your deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+".");
						plugin.cc.getChests().set(nameKey+"."+key, null);
						plugin.cc.saveChests();
						return;
					}
				}
				else {
					for(BlockFace bf:plugin.bfs) {
						Block faceBlock = b.getRelative(bf);
						Location l = faceBlock.getLocation();
						if(l.getBlockX() == x && l.getBlockY() == y && l.getBlockZ() == z 
								&& l.getWorld().getName().equalsIgnoreCase(world)) {
							if(!(p.getName().equalsIgnoreCase(nameKey))) {
								if(p.hasPermission("depositchest.break")) {
									plugin.cc.getChests().set(nameKey+"."+key, null);
									p.sendMessage(ChatColor.GOLD+"You just broke a deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+" owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+".");
									plugin.cc.saveChests();
									return;
								}
								else {
									event.setCancelled(true);
									p.sendMessage(ChatColor.RED+"You do not have permission to break that chest.");
									p.sendMessage(ChatColor.RED+"It is called "+ChatColor.GOLD+key+ChatColor.RED+" and is owned by "+ChatColor.GOLD+nameKey+ChatColor.RED+".");
									return;
								}
							}
							else {
								p.sendMessage(ChatColor.GOLD+"You just broke your deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+".");
								plugin.cc.getChests().set(nameKey+"."+key, null);
								plugin.cc.saveChests();
								return;
							}
						}
					}
					
				}
			}
		}
	}


}
