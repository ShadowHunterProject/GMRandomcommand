package com.gm.randomcommand.util;

import org.bukkit.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String colorize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer(text.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            try {
                Class<?> chatColorClass = Class.forName("net.md_5.bungee.api.ChatColor");
                java.lang.reflect.Method ofMethod = chatColorClass.getMethod("of", String.class);
                Object color = ofMethod.invoke(null, "#" + group);
                matcher.appendReplacement(buffer, color.toString());
            } catch (Exception e) {
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);
        
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
