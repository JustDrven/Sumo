package cz.drven.sumo.items.spectator;

import cz.drven.sumo.Main;
import cz.drven.sumo.items.util.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.WHITE;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_AQUA;

public class LeftItem extends Item {

    public LeftItem(Main pl) {
        super(pl);
    }

    @Override
    public void build(Player player, int slot) {
        super.build(player, slot);
    }

    @Override
    public void build(Player player) {
        super.build(player);
    }

    @Override
    public ItemStack getItem() {
        ItemStack items = new ItemStack(Material.BED);
        ItemMeta itemsmeta = items.getItemMeta();
        itemsmeta.setDisplayName("" + BLUE + "Return to Lobby");

        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "Click to return to lobby.");

        itemsmeta.setLore(lore);

        items.setItemMeta(itemsmeta);
        return items;
    }

    @Override
    public void runClick(ItemStack item, Player p) {
        p.kickPlayer(DARK_GRAY + "[" + AQUA + "Server" + DARK_GRAY + "] " + WHITE + "Connecting to " + DARK_GRAY + "Lobby..");
    }
}
