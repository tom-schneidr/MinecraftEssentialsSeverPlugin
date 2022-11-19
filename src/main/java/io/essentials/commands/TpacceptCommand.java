package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class TpacceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/tpaccept")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        final Player p2 = (Player)sender;
        if(!p2.hasPermission("essentials.tpaccept")) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        // /tpaccept
        if(!Essentials.getTpaRequests().containsKey(p2.getUniqueId()) && !Essentials.getTpahereRequests().containsKey(p2.getUniqueId())) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.noPendingTpRequest")));
            return true;
        }
        if(Essentials.getTpaRequests().containsKey(p2.getUniqueId())) {
            final Player p1 = Bukkit.getPlayer(Essentials.getTpaRequests().get(p2.getUniqueId()));
            final Location startloc = p1.getLocation();
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.afterTpaccept").replaceAll("%teleportTime%", Essentials.configManager.getConfig().getString("teleportSettings.teleportTime"))));
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.hasAccepted").replaceAll("%player%", p1.getDisplayName())));
            Essentials.getTpaRequests().remove(p2.getUniqueId());

            p2.getServer().getScheduler().scheduleSyncDelayedTask(Essentials.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(p1.getLocation().equals(startloc)) {
                        p1.teleport(p2.getLocation());
                        p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.teleportingStatusMessage")));
                    } else {
                        p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpCanceledByMovement")));
                    }
                }
            }, Integer.parseInt(Essentials.configManager.getConfig().getString("teleportSettings.teleportTime")));
        } 

        if(Essentials.getTpahereRequests().containsKey(p2.getUniqueId())) {
            final Player p1 = Bukkit.getPlayer(Essentials.getTpahereRequests().get(p2.getUniqueId()));
            final Location startloc = p2.getLocation();
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.afterTpaccept").replaceAll("%teleportTime%", Essentials.configManager.getConfig().getString("teleportSettings.teleportTime"))));
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.hasAccepted").replaceAll("%player%", p2.getDisplayName())));
            Essentials.getTpahereRequests().remove(p2.getUniqueId());

            p2.getServer().getScheduler().scheduleSyncDelayedTask(Essentials.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(p2.getLocation().equals(startloc)) {
                        p2.teleport(p1.getLocation());
                        p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.teleportingStatusMessage")));
                    } else {
                        p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpCanceledByMovement")));
                    }
                }
            }, Integer.parseInt(Essentials.configManager.getConfig().getString("teleportSettings.teleportTime")));
        }
        return true;
    }
    
}
