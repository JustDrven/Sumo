package cz.drven.sumo.items;

import cz.drven.sumo.Main;
import cz.drven.sumo.items.spectator.SpectatorItems;

public class ItemManager {

    private final Main pl;
    private SpectatorItems spectatorItems;

    public ItemManager(Main pl) {
        this.pl = pl;

        this.spectatorItems = new SpectatorItems(pl);
    }

    public SpectatorItems getSpectatorItems() {
        return spectatorItems;
    }
}
