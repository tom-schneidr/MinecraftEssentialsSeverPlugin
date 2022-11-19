package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/ping <player>")));
            return true;
        }
        if(!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        if(args.length == 0) {
            if(!sender.hasPermission("essentials.ping")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            Player p1 = (Player)sender;
            String ping = p1.getPing() + "";
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.getPingSelf").replaceAll("%ping%", ping)));
            return true;
        }
        if(!sender.hasPermission("essentials.ping.others")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p2 = Bukkit.getServer().getPlayer(args[0]);
        if(p2 == null) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
            return true;
        }
        String ping = p2.getPing() + "";
        sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.getPingSelf").replaceAll("%ping%", ping).replaceAll("%player%", p2.getDisplayName())));
        return true;
    }
}
