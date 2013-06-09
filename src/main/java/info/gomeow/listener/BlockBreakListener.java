package info.gomeow.listener;

import info.gomeow.Depositchest;

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
        Player player = event.getPlayer();
        for(String nameKey:plugin.chestConfig.getChests().getKeys(false)) {
            for(String key:plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false)) {
                String world = plugin.chestConfig.getChests().getString(nameKey + "." + key + ".World");
                Integer x = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".X");
                Integer y = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Y");
                Integer z = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Z");
                if(loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z && loc.getWorld().getName().equalsIgnoreCase(world)) {
                    if(!(player.getName().equalsIgnoreCase(nameKey))) {
                        if(player.hasPermission("depositchest.break")) {
                            plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                            player.sendMessage(ChatColor.GOLD + "You just broke a deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + " owned by " + ChatColor.BLUE + nameKey + ChatColor.GOLD + ".");
                            plugin.chestConfig.saveChests();
                            return;
                        } else {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "You do not have permission to break that chest.");
                            player.sendMessage(ChatColor.RED + "It is called " + ChatColor.GOLD + key + ChatColor.RED + " and is owned by " + ChatColor.GOLD + nameKey + ChatColor.RED + ".");
                            return;
                        }
                    } else {
                        player.sendMessage(ChatColor.GOLD + "You just broke your deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + ".");
                        plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                        plugin.chestConfig.saveChests();
                        return;
                    }
                } else {
                    for(BlockFace bf:plugin.faces) {
                        Block faceBlock = b.getRelative(bf);
                        Location location = faceBlock.getLocation();
                        if(location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z && location.getWorld().getName().equalsIgnoreCase(world)) {
                            if(!(player.getName().equalsIgnoreCase(nameKey))) {
                                if(player.hasPermission("depositchest.break")) {
                                    plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                                    player.sendMessage(ChatColor.GOLD + "You just broke a deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + " owned by " + ChatColor.BLUE + nameKey + ChatColor.GOLD + ".");
                                    plugin.chestConfig.saveChests();
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "You do not have permission to break that chest.");
                                    player.sendMessage(ChatColor.RED + "It is called " + ChatColor.GOLD + key + ChatColor.RED + " and is owned by " + ChatColor.GOLD + nameKey + ChatColor.RED + ".");
                                }
                            } else {
                                player.sendMessage(ChatColor.GOLD + "You just broke your deposit chest named " + ChatColor.BLUE + key + ChatColor.GOLD + ".");
                                plugin.chestConfig.getChests().set(nameKey + "." + key, null);
                                plugin.chestConfig.saveChests();
                            }
                        }
                    }

                }
            }
        }
    }

}
