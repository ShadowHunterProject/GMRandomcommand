package com.gm.randomcommand.manager;

import com.gm.randomcommand.GMRandomCommand;
import com.gm.randomcommand.model.CommandList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ListManager {
    private final Map<String, CommandList> lists = new HashMap<>();

    public void loadLists() {
        lists.clear();
        
        File listsFolder = new File(GMRandomCommand.getInstance().getDataFolder(), "lists");
        if (!listsFolder.exists()) {
            listsFolder.mkdirs();
            GMRandomCommand.getInstance().saveResource("lists/example.yml", false);
        }

        String splitChar = GMRandomCommand.getInstance().getConfig().getString("settings.split-character", ":");
        File[] files = listsFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        
        if (files != null) {
            for (File file : files) {
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
                for (String listName : yaml.getKeys(false)) {
                    CommandList list = new CommandList(listName, yaml.getConfigurationSection(listName), splitChar);
                    lists.put(listName.toLowerCase(), list);
                }
            }
        }
    }

    public boolean executeList(String listName, CommandSender sender, Player target) {
        CommandList list = lists.get(listName.toLowerCase());
        if (list == null) {
            String msg = GMRandomCommand.getInstance().getMsg("list-not-found").replace("%list%", listName);
            sender.sendMessage(msg);
            return false;
        }
        return list.execute(sender, target);
    }
}
