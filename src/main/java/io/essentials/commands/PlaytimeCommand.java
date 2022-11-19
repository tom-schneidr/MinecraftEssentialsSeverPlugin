package io.essentials.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.essentials.Essentials;
import io.essentials.utils.Maths;
import io.essentials.utils.Text;

public class PlaytimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/playtime")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        if(args.length == 0) {
            if(!sender.hasPermission("essentials.playtime")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            Player p = (Player)sender;
  
            long logintime = Essentials.getLoginTimeList().get(p.getUniqueId());
            long ontime = System.currentTimeMillis() - logintime;
    
            ResultSet rs = Essentials.mysql.getPlayer(p.getUniqueId());
            try {
                long playtime = rs.getLong("Playtime");
                playtime += ontime;
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.playtimeSelf").replaceAll("%time%", Maths.timeConverter(playtime))));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(!sender.hasPermission("essentials.playtime.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p2 = Bukkit.getServer().getPlayer(args[0]);

        long logintime = Essentials.getLoginTimeList().get(p2.getUniqueId());
        long ontime = System.currentTimeMillis() - logintime;

        ResultSet rs = Essentials.mysql.getPlayer(p2.getUniqueId());
        try {
            long playtime = rs.getLong("Playtime");
            playtime += ontime;
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.playtimeSelf").replaceAll("%time%", Maths.timeConverter(playtime)).replaceAll("%player%", p2.getDisplayName())));
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    
}
