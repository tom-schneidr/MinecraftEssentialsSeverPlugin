package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class VanishCommand implements CommandExecutor {

    

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/vanish <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        // /vanish
        if(args.length == 0) {
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.vanish")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            }
            if(Essentials.getVanished().contains(p.getUniqueId())) {
                for(Player online: Bukkit.getOnlinePlayers()) {
                    online.showPlayer(Essentials.getInstance(), p); 
                }
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishDisabledSelf")));
                Essentials.getVanished().remove(p.getUniqueId());
                return true;
            }
            for(Player online: Bukkit.getOnlinePlayers()) {
                online.hidePlayer(Essentials.getInstance(), p);
            }
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishEnabledSelf")));
            Essentials.getVanished().add(p.getUniqueId());
            return true;
        }
        // /vanish <player>
        if(args.length == 1) {
            if(!sender.hasPermission("essentials.vanish.others")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
                return true;
            }
            if(Essentials.getVanished().contains(target.getUniqueId())) {
                for(Player online: Bukkit.getOnlinePlayers()) {
                    online.showPlayer(Essentials.getInstance(), target); 
                }
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishDisabledOther").replaceAll("%player%", target.getDisplayName())));
                target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishDisabledSelf")));
                Essentials.getVanished().remove(target.getUniqueId());
                return true;
            }
            for(Player online: Bukkit.getOnlinePlayers()) {
                online.hidePlayer(Essentials.getInstance(), target);
            }
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishEnabledOther").replaceAll("%player%", target.getDisplayName())));
            target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.vanishEnabledSelf")));
            Essentials.getVanished().add(target.getUniqueId());
            return true;
        }
        return true;
    }
}
