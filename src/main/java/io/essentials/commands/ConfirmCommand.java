package io.essentials.commands;

import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.essentials.Essentials;
import io.essentials.utils.Text;

public class ConfirmCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/craft")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        Player p = (Player)sender;
        if(!p.hasPermission("essentials.confirm")) {
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        for(Map<UUID,UUID> map: Essentials.getConfirmList().keySet()) {
            for(Entry<UUID,UUID> entry: map.entrySet()) {

                if(entry.getKey() == p.getUniqueId()) {
                    String type = Essentials.getConfirmList().get(map);

                    if(type.equals("tpall")) {
                        Location loc = Bukkit.getServer().getPlayer(map.get(p.getUniqueId())).getLocation();
                        p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.confirmedCommand")));
                        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
                            player.teleport(loc);
                            player.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.teleportingStatusMessage")));
                        }
                        Essentials.getConfirmList().remove(map);
                        return true;
                    }

                    if(type.equals("clearInventory")) {
                        Player target = Bukkit.getServer().getPlayer(map.get(p.getUniqueId()));
                        target.getInventory().clear();
                        target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.inventoryCleared")));
                        Essentials.getConfirmList().remove(map);
                        return true;
                    }
                    return true;
                }
            }
        }
        sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.noPendingConfirmation")));
        return true;
    }
}
