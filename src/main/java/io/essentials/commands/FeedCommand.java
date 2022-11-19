package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/feed <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        // /feed
        if(args.length == 0) {
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.feed")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            p.setFoodLevel(20);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.fedSelf")));
            return true;
        }
        // /feed <player>
        if(!sender.hasPermission("essentials.feed.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        target.setFoodLevel(20);
        target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.fedSelf")));
        sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.fedOther").replaceAll("%player%", target.getDisplayName())));
        return true;
    }
    
}
