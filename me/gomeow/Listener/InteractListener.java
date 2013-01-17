package me.gomeow.Listener;

import me.gomeow.Depositchest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

	Depositchest plugin;
	
	public InteractListener(Depositchest plg) {
		this.plugin = plg;
	}
	
	
	@EventHandler
	public void onHit(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action a = event.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if(b.getType() == Material.CHEST) {
				if(plugin.setChest.containsKey(p.getName())) {
					String name = plugin.setChest.get(p.getName());
					Location loc = b.getLocation();
					for(String nameKey:plugin.cc.getChests().getKeys(false)) {
						for(String key:plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false)) {
							String world = plugin.cc.getChests().getString(nameKey+"."+key+".World");
							Integer x = plugin.cc.getChests().getInt(nameKey+"."+key+".X");
							Integer y = plugin.cc.getChests().getInt(nameKey+"."+key+".Y");
							Integer z = plugin.cc.getChests().getInt(nameKey+"."+key+".Z");
							if(loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z 
									&& loc.getWorld().getName().equalsIgnoreCase(world)) {
								p.sendMessage(ChatColor.RED+"There is already a deposit chest located there.");
								p.sendMessage(ChatColor.RED+"It is called "+key+" and is owned by "+nameKey);
								event.setCancelled(true);
								return;
								
							}
							
						}
					}
					
					plugin.setChest.remove(p.getName());
					p.sendMessage(ChatColor.GREEN+"You just set a deposit chest!");
					plugin.chests.set(p.getName()+"."+name+".World", loc.getWorld().getName());
					plugin.chests.set(p.getName()+"."+name+".X", loc.getBlockX());
					plugin.chests.set(p.getName()+"."+name+".Y", loc.getBlockY());
					plugin.chests.set(p.getName()+"."+name+".Z", loc.getBlockZ());
					plugin.cc.saveChests();
					event.setCancelled(true);
				}
				else if(plugin.info.contains(p.getName())) {
					event.setCancelled(true);
					plugin.info.remove(p.getName());
					BlockState bs = b.getState();
					if(bs instanceof Chest) {
						Chest eventChest = (Chest) bs;
						Location chestLoc = eventChest.getLocation();
						String world = chestLoc.getWorld().getName();
						Integer x = chestLoc.getBlockX();
						Integer y = chestLoc.getBlockY();
						Integer z = chestLoc.getBlockZ();
						for(String nameKey:plugin.cc.getChests().getKeys(false)) {
							for(String key:plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false)) {
								String world1 = plugin.cc.getChests().getString(nameKey+"."+key+".World"); 
								Integer x1 = plugin.cc.getChests().getInt(nameKey+"."+key+".X");
								Integer y1 = plugin.cc.getChests().getInt(nameKey+"."+key+".Y");
								Integer z1 = plugin.cc.getChests().getInt(nameKey+"."+key+".Z");
								String loc1 = world+", "+x.toString()+", "+y.toString()+", "+z.toString();
								String loc2 = world1+", "+x1.toString()+", "+y1.toString()+", "+z1.toString();
								if(loc1.equalsIgnoreCase(loc2)) {
									if(p.getName().equals(nameKey)) {
										p.sendMessage(ChatColor.GOLD+"You own this chest. It is called "+ChatColor.BLUE+key+ChatColor.GOLD+".");
									}
									else {
										p.sendMessage(ChatColor.GOLD+"That chest is owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+" and is called "+ChatColor.BLUE+key+ChatColor.GOLD+" !");
									}
								}
								else {
									for(BlockFace bf:plugin.bfs) {
										Location chestLoc2 = eventChest.getLocation().getBlock().getRelative(bf).getLocation();
										String world2 = chestLoc2.getWorld().getName();
										Integer x2 = chestLoc2.getBlockX();
										Integer y2 = chestLoc2.getBlockY();
										Integer z2 = chestLoc2.getBlockZ();
										String loc5 = world1+", "+x1.toString()+", "+y1.toString()+", "+z1.toString();
										String loc6 = world2+", "+x2.toString()+", "+y2.toString()+", "+z2.toString();
										if(loc5.equalsIgnoreCase(loc6)) {
											if(p.getName().equals(nameKey)) {
												p.sendMessage(ChatColor.GOLD+"You own this chest. It is called "+ChatColor.BLUE+key+ChatColor.GOLD+".");
											}
											else {
												p.sendMessage(ChatColor.GOLD+"That chest is owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+" and is called "+ChatColor.BLUE+key+ChatColor.GOLD+" !");
											}
											return;
										}
										else {
											if(bf == BlockFace.SOUTH) {
												p.sendMessage(ChatColor.RED+"That is a normal chest");
											}
										}
									}
								}
							}
						}
						
					}
				}
				else if(plugin.removeCmd.contains(p.getName())) {
					
				}
			}
		}
	}
}
