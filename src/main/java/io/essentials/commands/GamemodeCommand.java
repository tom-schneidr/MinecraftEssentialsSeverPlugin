package io.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.essentials.Essentials;
import io.essentials.utils.Text;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Checks
        if(args.length > 2 || args.length == 0) {
            sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.wrongUsage").replaceAll("%command%", "/gamemode <gamemode> <player>")));
            return true;
        }
        // /gamemode <mode>
        if(args.length == 1) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.onlyPlayers")));
                return true;
            }
            Player p = (Player)sender;
            if(!p.hasPermission("essentials.gamemodes")) {
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            if(args[0].equals("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "survival")));
                return true;
            }
            if(args[0].equals("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "creative")));
                return true;
            }
            if(args[0].equals("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "adventure")));
                return true;
            }
            if(args[0].equals("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "spectator")));
                return true;
            }   
        }
        // /gamemode <mode> <player>
        if(args.length == 2) {
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.invalidTarget")));
                return true;
            }
            if(!sender.hasPermission("essentials.gamemodes.others")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.missingPermission")));
                return true;
            }
            if(Integer.parseInt(args[0]) == 0 || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeOther").replaceAll("%gamemode%", "survival").replaceAll("%player%", target.getDisplayName())));
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "survival")));
                return true;
            }
            if(Integer.parseInt(args[0]) == 1 || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeOther").replaceAll("%gamemode%", "creative").replaceAll("%player%", target.getDisplayName())));
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "creative")));
                return true;
            }
            if(Integer.parseInt(args[0]) == 2 || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeOther").replaceAll("%gamemode%", "adventure").replaceAll("%player%", target.getDisplayName())));
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "adventure")));
                return true;
            }
            if(Integer.parseInt(args[0]) == 3 || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                sender.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeOther").replaceAll("%gamemode%", "spectator").replaceAll("%player%", target.getDisplayName())));
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(Text.convertString(Essentials.configManager.getConfig().getString("messages.setGamemodeSelf").replaceAll("%gamemode%", "spectator")));
                return true;
            }   
        }
        return true;
    }
}
