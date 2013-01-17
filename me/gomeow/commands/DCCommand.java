package me.gomeow.commands;

import java.util.ArrayList;

import me.gomeow.Depositchest;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DCCommand {

	Depositchest plugin;
	
	public DCCommand(Depositchest plugin) {
		this.plugin = plugin;
	}
	
	public void showHelp(CommandSender cs) {
		if(cs.hasPermission("depositchest.use")) {
			cs.sendMessage(ChatColor.GOLD+"----DepositChest Help----");
			cs.sendMessage(ChatColor.GOLD+"/dc - "+ChatColor.BLUE+"Shows the help page.");
			cs.sendMessage(ChatColor.GOLD+"/dc setchest <name> - "+ChatColor.BLUE+"Allows you to set a deposit chest.");
			cs.sendMessage(ChatColor.GOLD+"/dc remove - "+ChatColor.BLUE+"Removes protection on a chest you hit.");
			cs.sendMessage(ChatColor.GOLD+"/dc remove <name> - "+ChatColor.BLUE+"Removes protection on a specified chest.");
			cs.sendMessage(ChatColor.GOLD+"/dc info - "+ChatColor.BLUE+"Will give you info on a chest you hit.");
			cs.sendMessage(ChatColor.GOLD+"/dc info <name> - "+ChatColor.BLUE+"Will give you info a specified chest.");
		}
		else {
			cs.sendMessage(ChatColor.RED+"You do not have permission to do that!");
		}
	}
	
	/*
	 * Executes a command with the given sender and args
	 * 
	 * @param cs The command sender.
	 * @param args the list of arguments.
	 * 
	 * @returns true or false
	 * 
	 */
	public boolean execute(CommandSender cs, String[] args) {
		if(args.length == 0) {
			if(cs.hasPermission("depositchest.use")) {
				this.showHelp(cs);
			}
			else {
				cs.sendMessage(ChatColor.RED+"You do not have permission to do that!");
			}
		}
		else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("info")) {
				if(cs instanceof Player) {
					Player p = (Player) cs;
					if(p.hasPermission("depositchest.info")) {
						plugin.info.add(p.getName());
					}
					else {
						p.sendMessage(ChatColor.RED+"You do not have permission to do that!");
						return true;
					}
				}
				else {
					cs.sendMessage(ChatColor.RED+"You need to be a player to do that!");
					return true;
				}
			}
			else {
				this.showHelp(cs);
				return true;
			}
		}
		else if(args.length == 2) {
			if(cs instanceof Player) {
				Player p = (Player) cs;
				if(args[0].equalsIgnoreCase("setchest")) {
					if(p.hasPermission("depositchest.use")) {
						for(String nameKey:plugin.cc.getChests().getKeys(false)) {
							for(String key:plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false)) {
								if(key.toLowerCase().equalsIgnoreCase(args[1].toLowerCase())) {
									p.sendMessage(ChatColor.RED+"There is already a deposit chest named that owned by "+nameKey+"!");
									p.sendMessage(ChatColor.RED+"Please choose a different name.");
									return true;
								}
							}
						}
						int size;
						if(plugin.cc.getChests().isConfigurationSection(p.getName())) {
							size = plugin.cc.getChests().getConfigurationSection(p.getName()).getKeys(false).size();
						}
						else {
							size = 0;
						}
						boolean underMax = true;
						if(size >= plugin.maxChests) {
							underMax = false;
						}
						if(plugin.infChests) underMax = true;
						if(underMax || p.hasPermission("depositchest.infinite")) {
							plugin.setChest.put(p.getName(), args[1]);
							p.sendMessage(ChatColor.GREEN+"Now click a chest to set a DepositChest!");
							return true;
						}
						else {
							p.sendMessage(ChatColor.RED+"You already have too many deposit chests.");
							p.sendMessage(ChatColor.RED+"Please destroy one before making a new one.");
							p.sendMessage(ChatColor.RED+"You can also use /dc remove <name>.");
							return true;
						}
					}
				}
				else if(args[0].equalsIgnoreCase("info")) {
					ArrayList<String> namekeys = new ArrayList<String>(plugin.cc.getChests().getKeys(false));
					if(namekeys.isEmpty()) {
						p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
						return true;
					}
					String name = args[1];
					for(String nameKey:namekeys) {
						ArrayList<String> keys = new ArrayList<String>(plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false));
						if(keys.isEmpty()) {
							p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
							return true;
						}
						for(String key:keys) {
							if(key.equalsIgnoreCase(name)) {
								p.sendMessage(ChatColor.GOLD+"That chest is owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+"!");
								return true;
							}
							else if(nameKey.equals(namekeys.get(namekeys.size()-1)) && key.equals(keys.get(keys.size()-1))) {
								p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
							}
						}
					}
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					ArrayList<String> namekeys = new ArrayList<String>(plugin.cc.getChests().getKeys(false));
					if(namekeys.isEmpty()) {
						p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
						return true;
					}
					String name = args[1];
					for(String nameKey:namekeys) {
						ArrayList<String> keys = new ArrayList<String>(plugin.cc.getChests().getConfigurationSection(nameKey).getKeys(false));
						if(keys.isEmpty()) {
							p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
							return true;
						}
						for(String key:keys) {
							if(key.equalsIgnoreCase(name)) {
								if(!(p.getName().equalsIgnoreCase(nameKey))) {
									if(p.hasPermission("depositchest.break")) {
										plugin.cc.getChests().set(nameKey+"."+key, null);
										p.sendMessage(ChatColor.GOLD+"You just removed a deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+" owned by "+ChatColor.BLUE+nameKey+ChatColor.GOLD+".");
										plugin.cc.saveChests();
										return true;
									}
									else {
										p.sendMessage(ChatColor.RED+"You do not have permission to remove that chest.");
										p.sendMessage(ChatColor.RED+"It is called "+ChatColor.GOLD+key+ChatColor.RED+" and is owned by "+ChatColor.GOLD+nameKey+ChatColor.RED+".");
										return true;
									}
								}
								else {
									p.sendMessage(ChatColor.GOLD+"You just removed your deposit chest named "+ChatColor.BLUE+key+ChatColor.GOLD+".");
									plugin.cc.getChests().set(nameKey+"."+key, null);
									plugin.cc.saveChests();
									return true;
								}
							}
							else if(nameKey.equals(namekeys.get(namekeys.size()-1)) && key.equals(keys.get(keys.size()-1))) {
								p.sendMessage(ChatColor.RED+"There is no Deposit Chest named that.");
							}
						}
					}
				}
				else {
					this.showHelp(cs);
					return true;
				}
			}
			else {
				cs.sendMessage(ChatColor.RED+"You must be a player to do that!");
				return true;
			}
		}
		else {
			cs.sendMessage(ChatColor.RED+"Too many arguments!");
			return true;
		}
		
		return false;
	}
	
}
