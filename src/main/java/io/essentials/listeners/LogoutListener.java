package io.essentials.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import io.essentials.Essentials;

public class LogoutListener implements Listener {

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        // Get ontime and remove player from list
        long logintime = Essentials.getLoginTimeList().get(p.getUniqueId());
        Essentials.getLoginTimeList().remove(p.getUniqueId());
        long ontime = System.currentTimeMillis() - logintime;
        // Get player data from database
        ResultSet playerData = Essentials.mysql.getPlayer(p.getUniqueId());
        
        try {
            if(!playerData.last()) System.out.println("idfk whats happening");
            // Get current player data
            // Playtime data
            long playtime = playerData.getLong("Playtime");
            long updatedTime = playtime + ontime;
            // Get tpToggle status
            int tpToggle = 1;
            if(Essentials.getTpToggledOffList().contains(p.getUniqueId())) {
                tpToggle = 0;
            }
            // Get isFlying status
            int isFlying = 0;
            if(p.getAllowFlight()) {
                isFlying = 1;
            }

            // Update
            String sql = "UPDATE essentials_Players SET Playtime = " + updatedTime +
                                                    ", TpToggle = " + tpToggle + 
                                                    ", IsFlying = " + isFlying +
                                                    " WHERE UUID = '" + p.getUniqueId() + "';";
            Essentials.mysql.execute(sql);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
