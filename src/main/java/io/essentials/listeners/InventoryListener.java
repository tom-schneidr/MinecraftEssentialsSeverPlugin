package io.essentials.listeners;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import io.essentials.Essentials;

public class InventoryListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory().getHolder() instanceof Player) {
            UUID p1 = e.getWhoClicked().getUniqueId();
            UUID p2 = ((Player)e.getClickedInventory().getHolder()).getUniqueId();
            if(Essentials.getInvseeList().containsKey(p1)) {
                if(Essentials.getInvseeList().get(p1) == p2) {
                    e.setCancelled(true);
                }
            }
        }
        
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(e.getInventory().getHolder() instanceof Player) {
            UUID p1 = e.getPlayer().getUniqueId();
            UUID p2 = ((Player)e.getInventory().getHolder()).getUniqueId();
            if(Essentials.getInvseeList().containsKey(p1)) {
                if(Essentials.getInvseeList().get(p1) == p2) {
                    Essentials.getInvseeList().remove(p1);
                }
            }
        }
    }
}
