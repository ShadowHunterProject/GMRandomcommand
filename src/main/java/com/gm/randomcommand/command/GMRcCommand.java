package com.gm.randomcommand.command;

import com.gm.randomcommand.GMRandomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMRcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(GMRandomCommand.getInstance().getMsg("prefix") + "§7Usage: /gmrc <list> [player] [count] or /gmrc reload");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("gmrandomcommand.admin")) {
                GMRandomCommand.getInstance().reloadPlugin();
                sender.sendMessage(GMRandomCommand.getInstance().getMsg("reloaded"));
            } else {
                sender.sendMessage(GMRandomCommand.getInstance().getMsg("no-permission"));
            }
            return true;
        }

        String listName = args[0];
        Player target = null;
        int count = 1;

        if (args.length > 1) {
            if (!sender.hasPermission("gmrandomcommand.admin")) {
                sender.sendMessage(GMRandomCommand.getInstance().getMsg("no-permission"));
                return true;
            }
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(GMRandomCommand.getInstance().getMsg("player-not-found"));
                return true;
            }
        }
        
        if (args.length > 2) {
            try {
                count = Integer.parseInt(args[2]);
                if (count < 1) count = 1;
            } catch (NumberFormatException e) {
                sender.sendMessage(GMRandomCommand.getInstance().getMsg("prefix") + "§cSố lần không hợp lệ. Sử dụng mặc định là 1.");
            }
        }

        for (int i = 0; i < count; i++) {
            boolean success = GMRandomCommand.getInstance().getListManager().executeList(listName, sender, target);
            if (!success) {
                break;
            }
        }
        
        return true;
    }
}
