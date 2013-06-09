package info.gomeow.listener;

import info.gomeow.Depositchest;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMoveItemListener implements Listener {

    public Depositchest plugin;

    public InventoryMoveItemListener(Depositchest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryItemMove(InventoryMoveItemEvent event) {
        InventoryHolder holder = event.getSource().getHolder();
        if(holder instanceof Chest) {
            Chest eventChest = (Chest) holder;
            Location chestLoc = eventChest.getLocation();
            String world = chestLoc.getWorld().getName();
            Integer x = chestLoc.getBlockX();
            Integer y = chestLoc.getBlockY();
            Integer z = chestLoc.getBlockZ();
            for(String nameKey:plugin.chestConfig.getChests().getKeys(false)) {
                for(String key:plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false)) {
                    String world1 = plugin.chestConfig.getChests().getString(nameKey + "." + key + ".World");
                    Integer x1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".X");
                    Integer y1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Y");
                    Integer z1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Z");
                    String loc1 = world + ", " + x.toString() + ", " + y.toString() + ", " + z.toString();
                    String loc2 = world1 + ", " + x1.toString() + ", " + y1.toString() + ", " + z1.toString();
                    if(loc1.equalsIgnoreCase(loc2)) {
                        event.setCancelled(true);
                    }
                }
            }

        } else if(holder instanceof DoubleChest) {
            DoubleChest eventChest = (DoubleChest) holder;
            Location chestLoc = eventChest.getLocation();
            String world = chestLoc.getWorld().getName();
            Integer x = chestLoc.getBlockX();
            Integer y = chestLoc.getBlockY();
            Integer z = chestLoc.getBlockZ();
            for(String nameKey:plugin.chestConfig.getChests().getKeys(false)) {
                for(String key:plugin.chestConfig.getChests().getConfigurationSection(nameKey).getKeys(false)) {
                    String world1 = plugin.chestConfig.getChests().getString(nameKey + "." + key + ".World");
                    Integer x1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".X");
                    Integer y1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Y");
                    Integer z1 = plugin.chestConfig.getChests().getInt(nameKey + "." + key + ".Z");
                    String loc1 = world + ", " + x.toString() + ", " + y.toString() + ", " + z.toString();
                    String loc2 = world1 + ", " + x1.toString() + ", " + y1.toString() + ", " + z1.toString();
                    if(loc1.equalsIgnoreCase(loc2)) {
                        event.setCancelled(true);
                    } else {
                        for(BlockFace bf:plugin.faces) {
                            Location chestLoc2 = eventChest.getLocation().getBlock().getRelative(bf).getLocation();
                            String world2 = chestLoc2.getWorld().getName();
                            Integer x2 = chestLoc2.getBlockX();
                            Integer y2 = chestLoc2.getBlockY();
                            Integer z2 = chestLoc2.getBlockZ();
                            String loc3 = world1 + ", " + x1.toString() + ", " + y1.toString() + ", " + z1.toString();
                            String loc4 = world2 + ", " + x2.toString() + ", " + y2.toString() + ", " + z2.toString();
                            if(loc3.equalsIgnoreCase(loc4)) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
