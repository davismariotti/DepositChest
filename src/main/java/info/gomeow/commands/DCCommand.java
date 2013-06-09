package info.gomeow.commands;

import info.gomeow.Depositchest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DCCommand {

    Depositchest plugin;

    public DCCommand(Depositchest plugin) {
        this.plugin = plugin;
    }

    public void showHelp(CommandSender cs) {
        if(cs.hasPermission("depositchest.user")) {
            cs.sendMessage(ChatColor.GOLD + "----DepositChest Help----");
            cs.sendMessage(ChatColor.GOLD + "/dc - " + ChatColor.BLUE + "Shows the help page.");
            cs.sendMessage(ChatColor.GOLD + "/dc setchest <name> - " + ChatColor.BLUE + "Allows you to set a deposit chest.");
            cs.sendMessage(ChatColor.GOLD + "/dc remove - " + ChatColor.BLUE + "Removes protection on a chest you hit.");
            cs.sendMessage(ChatColor.GOLD + "/dc remove <name> - " + ChatColor.BLUE + "Removes protection on a specified chest.");
            cs.sendMessage(ChatColor.GOLD + "/dc info - " + ChatColor.BLUE + "Will give you info on a chest you hit.");
            cs.sendMessage(ChatColor.GOLD + "/dc info <name> - " + ChatColor.BLUE + "Will give you info a specified chest.");
        } else {
            cs.sendMessage(ChatColor.RED + "You do not have permission to do that!");
        }
    }

    public boolean execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            if(sender.hasPermission("depositchest.user")) {
                showHelp(sender);
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
            }
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("info")) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    if(player.hasPermission("depositchest.user")) {
                        plugin.info.add(player.getName());
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You need to be a player to do that!");
                    return true;
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                if(sender instanceof Player) {
                    Player p = (Player) sender;
                    if(p.hasPermission("depositchest.user")) {
                        if(!(plugin.chestConfig.getChests().getKeys(false).contains(p.getName()))) {
                            p.sendMessage(ChatColor.RED + "You have no deposit chests!");
                            return true;
                        }
                        if(plugin.chestConfig.getChests().getConfigurationSection(p.getName()).getKeys(false).isEmpty()) {
                            p.sendMessage(ChatColor.RED + "You have no deposit chests!");
                            return true;
                        }
                        p.sendMessage(ChatColor.BLUE + "Your chests:");
                        for(String chestName:plugin.chestConfig.getChests().getConfigurationSection(p.getName()).getKeys(false)) {
                            p.sendMessage(ChatColor.BLUE + " - " + ChatColor.GOLD + chestName);
                        }
                        return true;
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "You need to be a player to do that!");
                        return true;
                    }
                }
            } else {
                showHelp(sender);
                return true;
            }
        } else if(args.length == 2) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args[0].equalsIgnoreCase("setchest")) {
                    if(player.hasPermission("depositchest.user")) {
                        for(String nameKey:plugin.chestConfig.getChests().getKeys(false)) {
                            for(String key:plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false)) {
                                if(key.toLowerCase().equalsIgnoreCase(args[1].toLowerCase())) {
                                    player.sendMessage(ChatColor.RED + "There is already a deposit chest named that owned by " + nameKey + "!");
                                    player.sendMessage(ChatColor.RED + "Please choose a different name.");
                                    return true;
                                }
                            }
                        }
                        int size;
                        if(plugin.chestConfig.getChests().isConfigurationSection(player.getName())) {
                            size = plugin.chestConfig.getChests().getConfigurationSection(player.getName()).getKeys(false).size();
                        } else {
                            size = 0;
                        }
                        boolean underMax = true;
                        if(size >= plugin.maxChests) {
                            underMax = false;
                        }
                        if(plugin.infiniteChests) {
                            underMax = true;
                        }
                        if(underMax || player.hasPermission("depositchest.infinite")) {
                            plugin.setChest.put(player.getName(), args[1]);
                            player.sendMessage(ChatColor.GREEN + "Now click a chest to set a DepositChest!");
                            return true;
                        } else {
                            player.sendMessage(ChatColor.RED + "You already have too many deposit chests.");
                            player.sendMessage(ChatColor.RED + "Please destroy one before making a new one.");
                            player.sendMessage(ChatColor.RED + "You can also use /dc remove <name>.");
                            return true;
                        }
                    }
                } else if(args[0].equalsIgnoreCase("info")) {
                    ArrayList<String> namekeys = new ArrayList<String>(plugin.chestConfig.getChests().getKeys(false));
                    if(namekeys.isEmpty()) {
                        player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                        return true;
                    }
                    String name = args[1];
                    for(String nameKey:namekeys) {
                        ArrayList<String> keys = new ArrayList<String>(plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false));
                        if(keys.isEmpty()) {
                            player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                            return true;
                        }
                        for(String key:keys) {
                            if(key.equalsIgnoreCase(name)) {
                                player.sendMessage(ChatColor.GOLD + "That chest is owned by " + ChatColor.BLUE + nameKey + ChatColor.GOLD + "!");
                                return true;
                            } else if(nameKey.equals(namekeys.get(namekeys.size() - 1)) && key.equals(keys.get(keys.size() - 1))) {
                                player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                            }
                        }
                    }
                } else if(args[0].equalsIgnoreCase("list")) {
                    if(player.hasPermission("depositchest.user")) {
                        if(!(plugin.chestConfig.getChests().getKeys(false).contains(args[1]))) {
                            player.sendMessage(ChatColor.RED + args[1] + " has no deposit chests!");
                            return true;
                        }
                        if(plugin.chestConfig.getChests().getConfigurationSection(args[1]).getKeys(false).isEmpty()) {
                            player.sendMessage(ChatColor.RED + args[1] + " has no deposit chests!");
                            return true;
                        }
                        player.sendMessage(ChatColor.BLUE + args[1] + "\'s chests:");
                        for(String chestName:plugin.chestConfig.getChests().getConfigurationSection(args[1]).getKeys(false)) {
                            player.sendMessage(ChatColor.BLUE + " - " + ChatColor.GOLD + chestName);
                        }
                        return true;
                    }
                }
                else if(args[0].equalsIgnoreCase("remove")) {
                    if(!player.hasPermission("depositchest.user")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                        return true;
                    }
                    ArrayList<String> namekeys = new ArrayList<String>(plugin.chestConfig.getChests().getKeys(false));
                    if(namekeys.isEmpty()) {
                        player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                        return true;
                    }
                    String name = args[1];
                    for(String nameKey:namekeys) {
                        ArrayList<String> keys = new ArrayList<String>(plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false));
                        if(keys.isEmpty()) {
                            player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                            return true;
                        }
                        for(String key:keys) {
                            if(key.equalsIgnoreCase(name)) {
                                if(!(player.getName().equalsIgnoreCase(nameKey))) {
                                    if(player.hasPermission("depositchest.break")) {
                                        plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                                        player.sendMessage(ChatColor.GOLD + "You just removed a deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + " owned by " + ChatColor.BLUE + nameKey + ChatColor.GOLD + ".");
                                        plugin.chestConfig.saveChests();
                                        return true;
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You do not have permission to remove that chest.");
                                        player.sendMessage(ChatColor.RED + "It is called " + ChatColor.GOLD + key + ChatColor.RED + " and is owned by " + ChatColor.GOLD + nameKey + ChatColor.RED + ".");
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ChatColor.GOLD + "You just removed your deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + ".");
                                    plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                                    plugin.chestConfig.saveChests();
                                    return true;
                                }
                            } else if(nameKey.equals(namekeys.get(namekeys.size() - 1)) && key.equals(keys.get(keys.size() - 1))) {
                                player.sendMessage(ChatColor.RED + "There is no Deposit Chest named that.");
                            }
                        }
                    }
                } else {
                    showHelp(sender);
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to do that!");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Too many arguments!");
            return true;
        }
        return false;
    }

}
