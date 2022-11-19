package io.essentials.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class TpallCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 1) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/tpall <player>")));
            return true;
        }
        if(!(sender instanceof Player)) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
            return true;
        }
        final Player p1 = (Player)sender;
        if(!p1.hasPermission("essentials.tpall")) {
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
            return true;
        }
        Player target = p1;
        if(args.length == 0) {
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpallWarningSelf")));
        } else if(args.length == 1) {
            Player p2 = Bukkit.getServer().getPlayer(args[0]);
            if(p2 == null) {
                p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
                return true;
            }
            p1.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.tpallWarningOther").replaceAll("%player%", p2.getDisplayName())));
            target = p2;
        }
        Map<UUID,UUID> tmp = new HashMap<>();
        tmp.put(p1.getUniqueId(), target.getUniqueId());
        Essentials.getConfirmList().put(tmp, "tpall");

        p1.getServer().getScheduler().scheduleSyncDelayedTask(Essentials.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Map<UUID,UUID> map: Essentials.getConfirmList().keySet()) {
                    for(Entry<UUID,UUID> entry: map.entrySet()) {
                        if(entry.getKey() == p1.getUniqueId()) {
                            Essentials.getConfirmList().remove(map);
                            return;
                        }
                    }
                }
            }
        }, Integer.parseInt(Essentials.configManager.getConfig().getString("generalSettings.confirmLifetime")));
        return true;
    }
}
