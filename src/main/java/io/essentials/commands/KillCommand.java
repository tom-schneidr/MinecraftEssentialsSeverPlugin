package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class KillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/kill <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        
        if(args.length == 0) {
            if(!sender.hasPermission("essentials.kill")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            Player p = (Player)sender;
            p.setHealth(0);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.killSelf")));
            return true;
        }
        if(!sender.hasPermission("essentials.kill.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        target.setHealth(0);
        target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.killSelf")));
        sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.killOther").replaceAll("%player%", target.getDisplayName())));
        return true;
    }
    
}
