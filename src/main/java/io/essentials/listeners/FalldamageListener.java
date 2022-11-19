package io.essentials.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import io.essentials.Essentials;

public class FalldamageListener implements Listener {
    
    @EventHandler
    public void onFalldamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        if(e.getCause() != DamageCause.FALL) {
            return;
        }
        Player p = (Player)e.getEntity();
        if(Essentials.getLogin().contains(p.getUniqueId())) {
            e.setCancelled(true);
            Essentials.getLogin().remove(p.getUniqueId());
        }
    }
}
