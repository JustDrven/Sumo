package cz.drven.sumo.game.state;

import cz.drven.sumo.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class State implements Listener {

    protected final Main pl;

    public State(Main pl) {
        this.pl = pl;
    }

    public void onEnable(){
        Bukkit.getServer().getPluginManager().registerEvents(this, this.pl);
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public abstract State getNextState();

}
