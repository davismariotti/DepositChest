package me.gomeow.Listener;

import me.gomeow.Depositchest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {
	
	public Depositchest plugin;




	public InventoryClickListener(Depositchest plugin) {
		this.plugin = plugin;
	}

	
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		
		
		if(!(event.getWhoClicked() instanceof Player)) return; //Make sure it was a player.
		if(event.getSlotType() != SlotType.CONTAINER) return; //Make sure it is the chest's inventory.
		if(event.getInventory().getType() != InventoryType.CHEST) return; //Make sure it was a chest.
		Player p = (Player) event.getWhoClicked();
		if(event.getSlot() < 9) return;
		if(event.getInventory().getItem(event.getSlot()) != null) { //Make sure it was a withdrawal.
			InventoryHolder ih = event.getInventory().getHolder();
			if(ih instanceof Chest) {
				Chest eventChest = (Chest) ih;
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
							}
							else {
								if(!p.hasPermission("depositchest.bypass")) {
									p.sendMessage("You do not have permission to take from that chest!");
									event.setCancelled(true);
								}
								else {
									p.sendMessage("You took from "+nameKey+"\'s chest!");
								}
							}
						}
					}
				}
				
			}
			else if(ih instanceof DoubleChest) {
				DoubleChest eventChest = (DoubleChest) ih;
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
						String loc3 = world+", "+x.toString()+", "+y.toString()+", "+z.toString();
						String loc4 = world1+", "+x1.toString()+", "+y1.toString()+", "+z1.toString();
						if(loc3.equalsIgnoreCase(loc4)) {
							if(p.getName().equals(nameKey)) {
							}
							else {
								if(!p.hasPermission("depositchest.bypass")) {
									p.sendMessage(ChatColor.RED+"You do not have permission to take from that chest!");
									event.setCancelled(true);
								}
								else {
									p.sendMessage(ChatColor.GOLD+"You took from "+nameKey+"\'s chest!");
								}
							}
							return;
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
										p.sendMessage("You just took from your chest!");
									}
									else {
										if(!p.hasPermission("depositchest.bypass")) {
											p.sendMessage("You do not have permission to take from that chest!");
											event.setCancelled(true);
										}
										else {
											p.sendMessage("You took from "+nameKey+"\'s chest!");
										}
									}
									return;
								}
							}
						}
					}
				}
			}
		}
	}
	
	
}
