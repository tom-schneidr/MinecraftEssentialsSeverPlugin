package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/god <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        // /god
        if(args.length == 0) {
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.god")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            if(p.isInvulnerable()) {
                p.setInvulnerable(false);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godDisabledSelf")));
            } else {
                p.setInvulnerable(true);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godEnabledSelf")));
            }
            return true;
        }
        // /god <player>
        if(!sender.hasPermission("essentials.god.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        if(target.isInvulnerable()) {
            target.setInvulnerable(false);
            target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godDisabledSelf")));
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godDisabledOther").replaceAll("%player%", target.getDisplayName())));
        } else {
            target.setInvulnerable(true);
            target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godEnabledSelf")));
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.godEnabledOther").replaceAll("%player%", target.getDisplayName())));
        }
        return true;
    }
    
}
