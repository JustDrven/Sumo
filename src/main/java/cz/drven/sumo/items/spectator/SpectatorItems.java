package cz.drven.sumo.items.spectator;

import cz.drven.sumo.Main;
import org.bukkit.entity.Player;

public class SpectatorItems {

    private final TeleporterItem teleporterItem;
    private final Main pl;

    public SpectatorItems(Main pl) {
        this.pl = pl;
        this.teleporterItem = new TeleporterItem(pl);
    }

    public void giveItems(Player player) {
        this.getTeleporterItem().build(player, 0);
    }

    public TeleporterItem getTeleporterItem() {
        return teleporterItem;
    }
}
