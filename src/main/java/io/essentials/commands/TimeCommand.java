package io.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.essentials.Essentials;
import io.essentials.utils.Maths;
import io.essentials.utils.Text;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/time <time>")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        if(!sender.hasPermission("essentials.time")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p = (Player)sender;
        if(Maths.isNumeric(args[0])) {
            p.getWorld().setTime(Integer.parseInt(args[0]));
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.timeSet").replaceAll("%time%", args[0])));
            return true;
        }
        if(args[0].equalsIgnoreCase("day")) {
            p.getWorld().setTime(6000);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.timeSet").replaceAll("%time%", args[0])));
            return true;
        }
        if(args[0].equalsIgnoreCase("night")) {
            p.getWorld().setTime(15000);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.timeSet").replaceAll("%time%", args[0])));
            return true;
        }
        return true;
    } 
}
