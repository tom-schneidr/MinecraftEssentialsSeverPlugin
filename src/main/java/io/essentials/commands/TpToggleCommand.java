package io.essentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class TpToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/tptoggle")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        if(!sender.hasPermission("essentials.tptoggle")) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player p = (Player)sender;
        if(Essentials.getTpToggledOffList().contains(p.getUniqueId())) {
            Essentials.getTpToggledOffList().remove(p.getUniqueId());
            p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpToggleOff")));
            return true;
        }
        Essentials.getTpToggledOffList().add(p.getUniqueId());
        p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpToggleOn")));
        return true;
    }
    
}
