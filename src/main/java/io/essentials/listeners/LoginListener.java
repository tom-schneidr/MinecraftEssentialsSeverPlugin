package io.essentials.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import io.essentials.Essentials;
import io.essentials.utils.Text;

public class LoginListener implements Listener {
    
    @EventHandler
    public void onLoginEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // Hide vanished players from new players
        for(UUID vanished: Essentials.getVanished()) {
            p.hidePlayer(Essentials.getInstance(), Bukkit.getPlayer(vanished));
        }
        // Add to recently logged in list
        //Essentials.getLogin().add(p.getUniqueId());

        // Time logged in added to playtime map
        Essentials.getLoginTimeList().put(p.getUniqueId(), System.currentTimeMillis());

        // Add player to essentials database if not added yet
        ResultSet playerData = Essentials.mysql.getPlayer(p.getUniqueId());
        
        try {
            if(!playerData.last()) {
                String sql = "INSERT INTO essentials_Players (UUID, Playtime, TpToggle, IsFlying)" +
                            "VALUES ('" + p.getUniqueId() + "', 0, 1, 0);";
                Essentials.mysql.execute(sql);
            } else {
                    int tpToggle = playerData.getInt("TpToggle");
                    int IsFlying = playerData.getInt("IsFlying");

                    // Set fitting plugin status
                    if(tpToggle == 0) {
                        p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpToggleOn")));
                        Essentials.getTpToggledOffList().add(p.getUniqueId());
                    }
                    if(IsFlying == 1) {
                        p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyEnabledSelf")));
                        p.setAllowFlight(true);
                    }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
