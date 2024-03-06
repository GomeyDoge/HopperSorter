package me.dotty.hoppersorter.events;

import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;

public class HopperEvents implements Listener {

    String getItemName(String translationKey) {
        if (translationKey == null) return null;
        String[] names = translationKey.split("\\.");
        return names[names.length-1];
    }

    boolean filterMatch(String filterString, String fullItemName) {
        String itemName = getItemName(fullItemName);
        String[] filter = filterString.split(",");
        return Arrays.asList(filter).stream().anyMatch((filter_i) -> {
           if (filter_i.endsWith("*")) {
               return itemName.startsWith(filter_i.substring(0, filter_i.length() - 1));
           } else if (filter_i.startsWith("*")) {
               return  itemName.endsWith(filter_i.substring(1));
           } else {
               return filter_i.equalsIgnoreCase(itemName);
           }
        });
    }

    @EventHandler
    void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        if(e.getDestination().getType().equals(InventoryType.HOPPER) && e.getDestination().getHolder() instanceof Container) {
            String customName = ((Container) e.getDestination().getHolder()).getCustomName();
            if (customName != null) {
                String itemName = e.getItem().getType().getItemTranslationKey();
                if (!filterMatch(customName, itemName)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    void onInventoryPickupItemEvent(InventoryPickupItemEvent e) {
        if (e.getInventory().getHolder() instanceof Container) {
            String customName = ((Container) e.getInventory().getHolder()).getCustomName();
            if (customName != null) {
                String itemName = e.getItem().getItemStack().getType().getItemTranslationKey();
                if (!filterMatch(customName, itemName)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
