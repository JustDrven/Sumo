package cz.drven.sumo.utils;

import org.bukkit.ChatColor;

public class Colors {
    private Colors() {
        throw new IllegalStateException("Colors not definited");
    }

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
