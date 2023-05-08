package cz.drven.sumo.game.team;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum TeamColor {

    RED(ChatColor.RED, DyeColor.RED),
    BLUE(ChatColor.BLUE, DyeColor.BLUE);

    private final ChatColor chatColor;
    private final DyeColor dyeColor;

    TeamColor(ChatColor chatColor, DyeColor dyeColor) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    public String getName() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
