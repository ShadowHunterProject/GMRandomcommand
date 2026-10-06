package com.gm.randomcommand.model;

import com.gm.randomcommand.GMRandomCommand;
import com.gm.randomcommand.util.ColorUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandList {
    private final String name;
    private final boolean useWeight;
    private final List<String> commands = new ArrayList<>();
    private final NavigableMap<Double, String> weightedCommands = new TreeMap<>();
    private double totalWeight = 0;
    private final Random random = new Random();

    public CommandList(String name, ConfigurationSection section, String splitChar) {
        this.name = name;
        this.useWeight = section.getBoolean("use-weight", false);

        List<String> rawCommands = section.getStringList("commands");
        if (this.useWeight) {
            for (String raw : rawCommands) {
                if (raw.contains(splitChar)) {
                    String[] split = raw.split(java.util.regex.Pattern.quote(splitChar), 2);
                    try {
                        double weight = Double.parseDouble(split[0]);
                        if (weight > 0) {
                            totalWeight += weight;
                            weightedCommands.put(totalWeight, split[1]);
                        }
                    } catch (NumberFormatException e) {
                        GMRandomCommand.getInstance().getLogger().warning("Invalid weight in list '" + name + "': " + raw);
                    }
                } else {
                    GMRandomCommand.getInstance().getLogger().warning("Missing split character '" + splitChar + "' in weighted list '" + name + "': " + raw);
                }
            }
        } else {
            this.commands.addAll(rawCommands);
        }
    }

    public boolean execute(CommandSender sender, Player target) {
        String selectedCommand = null;
        if (useWeight) {
            if (totalWeight <= 0) {
                GMRandomCommand.getInstance().getLogger().warning("List '" + name + "' has no valid weighted commands.");
                return false;
            }
            double value = random.nextDouble() * totalWeight;
            selectedCommand = weightedCommands.higherEntry(value).getValue();
        } else {
            if (commands.isEmpty()) {
                GMRandomCommand.getInstance().getLogger().warning("List '" + name + "' has no commands.");
                return false;
            }
            selectedCommand = commands.get(random.nextInt(commands.size()));
        }

        if (selectedCommand != null) {
            String finalCmd = selectedCommand;
            
            if (target != null) {
                finalCmd = finalCmd.replace("%player%", target.getName());
            } else if (sender instanceof Player) {
                finalCmd = finalCmd.replace("%player%", sender.getName());
            }

            if (GMRandomCommand.getInstance().isPapiHooked()) {
                finalCmd = PlaceholderAPI.setPlaceholders(target != null ? target : (sender instanceof Player ? (Player)sender : null), finalCmd);
            }

            finalCmd = ColorUtil.colorize(finalCmd);

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), finalCmd);
            return true;
        }

        return false;
    }
    
    public String getName() {
        return name;
    }
}
