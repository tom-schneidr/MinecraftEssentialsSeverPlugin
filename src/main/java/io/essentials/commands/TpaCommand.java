package io.essentials.commands;

import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class TpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length != 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/tpa <player>")));
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        final Player p1 = (Player)sender;
        // /tpa <player>
        if(!p1.hasPermission("essentials.tpa")) {
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p2 = Bukkit.getServer().getPlayer(args[0]);
        if(p2 == null) {
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        if(Essentials.getTpToggledOffList().contains(p2.getUniqueId())) {
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.targetTpToggledOff").replaceAll("%player%", p2.getDisplayName())));
            return true;
        }

        p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpaSent").replaceAll("%player%", p2.getDisplayName())));
        p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpaReceived").replaceAll("%player%", p1.getDisplayName())));
        // If player already has a pending request, replace it with new request
        if(Essentials.getTpaRequests().containsKey(p2.getUniqueId())) {
            Essentials.getTpaRequests().remove(p2.getUniqueId());
        }
        if(Essentials.getTpahereRequests().containsKey(p2.getUniqueId())) {
            Essentials.getTpahereRequests().remove(p2.getUniqueId());
        }
        Essentials.getTpaRequests().put(p2.getUniqueId(), p1.getUniqueId());

        p1.getServer().getScheduler().scheduleSyncDelayedTask(Essentials.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Entry<UUID,UUID> entry: Essentials.getTpaRequests().entrySet()) {
                    if(entry.getValue().equals(p1.getUniqueId())) {
                        Essentials.getTpaRequests().values().remove(p1.getUniqueId());
                        return;
                    }
                }
            }
        }, Integer.parseInt(Essentials.configManager.getConfig().getString("teleportSettings.requestLifetime")));
        return true;
    }
}
