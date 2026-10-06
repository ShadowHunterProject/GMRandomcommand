package com.gm.randomcommand;

import com.gm.randomcommand.command.GMRcCommand;
import com.gm.randomcommand.manager.ListManager;
import com.gm.randomcommand.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GMRandomCommand extends JavaPlugin {
    private static GMRandomCommand instance;
    private ListManager listManager;
    private boolean papiHooked = false;
    private FileConfiguration messagesConfig;
    private File messagesFile;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveDefaultMessages();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiHooked = true;
        }

        listManager = new ListManager();
        listManager.loadLists();

        getCommand("gmrc").setExecutor(new GMRcCommand());

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colorize("&e===================================================="));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colorize("&e  Thank you for using &aGMRandomCommand&e!"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colorize("&e  If you find this plugin useful, please consider"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colorize("&e  supporting me on PayPal: &bhttps://paypal.me/Gemixstudio"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.colorize("&e===================================================="));
        }, 20L);
    }

    @Override
    public void onDisable() {
    }

    private void saveDefaultMessages() {
        messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadPlugin() {
        reloadConfig();
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        listManager.loadLists();
    }

    public String getMsg(String path) {
        String prefix = messagesConfig.getString("prefix", "&8[&aGMRc&8] &r");
        if (path.equals("prefix")) {
            return ColorUtil.colorize(prefix);
        }
        String msg = messagesConfig.getString(path);
        if (msg == null) return "Message not found: " + path;
        return ColorUtil.colorize(prefix + msg);
    }

    public static GMRandomCommand getInstance() {
        return instance;
    }

    public ListManager getListManager() {
        return listManager;
    }

    public boolean isPapiHooked() {
        return papiHooked;
    }
}
