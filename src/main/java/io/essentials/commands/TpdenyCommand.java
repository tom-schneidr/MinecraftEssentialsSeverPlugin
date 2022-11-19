package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class TpdenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/tpdeny")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        Player p2 = (Player)sender;
        if(!p2.hasPermission("essentials.tpdeny")) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
        }
        if(!Essentials.getTpaRequests().containsKey(p2.getUniqueId()) && !Essentials.getTpahereRequests().containsKey(p2.getUniqueId())) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.noPendingTpRequest")));
            return true;
        }
        Player p1 = Bukkit.getPlayer(Essentials.getTpaRequests().get(p2.getUniqueId()));
        if(Essentials.getTpaRequests().containsKey(p2.getUniqueId())) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.otherRequestDenied")));
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.ownRequestDenied")).replaceAll("%player%", p2.getDisplayName()));
            Essentials.getTpaRequests().remove(p2.getUniqueId());
        } else if(Essentials.getTpahereRequests().containsKey(p2.getUniqueId())) {
            p2.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.otherRequestDenied")));
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.ownRequestDenied")).replaceAll("%player%", p2.getDisplayName()));
            Essentials.getTpahereRequests().remove(p2.getUniqueId());
        }
        else {
            
        }
        return true;
    }
    
}
