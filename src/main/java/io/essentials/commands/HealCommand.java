package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/heal <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        // /heal
        if(args.length == 0) {
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.heal")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            p.setHealth(20);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.healSelf")));
            return true;
        }
        // /heal <player>
        if(!sender.hasPermission("essentials.heal.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        target.setHealth(20);
        target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.healSelf")));
        sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.healOther").replaceAll("%player%", target.getDisplayName())));
        return true;
    }
}
