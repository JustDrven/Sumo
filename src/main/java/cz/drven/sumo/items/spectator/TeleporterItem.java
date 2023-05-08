package cz.drven.sumo.items.spectator;

import cz.drven.sumo.Main;
import cz.drven.sumo.items.util.Item;
import cz.drven.sumo.utils.Colors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;

public class TeleporterItem extends Item {

    public TeleporterItem(Main pl) {
        super(pl);
    }

    @Override
    public void build(Player player) {
        super.build(player);
    }

    @Override
    public void build(Player player, int slot) {
        super.build(player, slot);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.ANVIL, 1, (byte)0);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("" + AQUA + BOLD + "Teleporter");

        List<String> lore = new ArrayList<>();
        lore.add(Colors.format("&7Click to spectate other players."));
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public void runClick(ItemStack item, Player p) {
        if (pl.getGame().getPlayerManager().getAlivePlayers().size() <= 0) {
            p.closeInventory();
            p.sendMessage(Colors.format("&8[&bServer&8] &cAll players is't alive!"));
            return;
        }
        p.closeInventory();
        p.sendMessage(Colors.format("&8[&bServer&8] &aLoading data.."));
    }

}
