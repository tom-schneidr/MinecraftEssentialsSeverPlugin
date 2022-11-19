package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/fly <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        // /fly
        if(args.length == 0) {
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.fly")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            if(p.getAllowFlight()) {
                p.setAllowFlight(false);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyDisabledSelf")));
            } else {
                p.setAllowFlight(true);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyEnabledSelf")));
            }
            return true;
        }
        // /fly <player>
        if(!sender.hasPermission("essentials.fly.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        if(target.getAllowFlight()) {
            target.setAllowFlight(false);
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyDisabledOther").replaceAll("%player%", target.getDisplayName())));
            target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyDisabledSelf")));
        } else {
            target.setAllowFlight(true);
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyEnabledOther").replaceAll("%player%", target.getDisplayName())));
            target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.flyEnabledSelf")));
        }
        return true;
    } 
}
