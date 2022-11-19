package io.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.essentials.Essentials;
import io.essentials.utils.Maths;
import io.essentials.utils.Text;

public class PlayerTimeCommand implements CommandExecutor {

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
        if(!sender.hasPermission("essentials.ptime")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p = (Player)sender;
        if(Maths.isNumeric(args[0])) {
            p.setPlayerTime(Integer.parseInt(args[0]), true);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.ptimeSet").replaceAll("%time%", args[0])));
            return true;
        }
        if(args[0].equalsIgnoreCase("day")) {
            p.setPlayerTime(6000, true);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.timeSet").replaceAll("%time%", args[0])));
            return true;
        }
        if(args[0].equalsIgnoreCase("night")) {
            p.setPlayerTime(15000, true);
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.timeSet").replaceAll("%time%", args[0])));
            return true;
        }
        return true;
    }
    
}
