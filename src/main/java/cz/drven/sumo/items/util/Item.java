package cz.drven.sumo.items.util;

import cz.drven.sumo.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Item implements Listener {

    protected final Main pl;

    public Item(Main pl) {
        this.pl = pl;

        Bukkit.getServer().getPluginManager().registerEvents(this, pl);
    }

    public void build(Player player, int slot) {
        player.getInventory().setItem(slot, this.getItem());
    }

    public void build(Player player) {
        player.getInventory().addItem(this.getItem());
    }

    public abstract ItemStack getItem();

    public abstract void runClick(ItemStack item, Player p);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void clickeditem(PlayerDropItemEvent e) {
        if (this.getItem().equals(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void runClickItem(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getItemInHand().equals(this.getItem())) {
                e.setCancelled(true);
                this.runClick(e.getPlayer().getItemInHand(), e.getPlayer());
            }
        }
    }

}
