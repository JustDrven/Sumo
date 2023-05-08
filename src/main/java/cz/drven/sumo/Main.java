package cz.drven.sumo;

import cz.drven.sumo.commands.GameCommand;
import cz.drven.sumo.game.Game;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Game game;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.game = new Game(this);

        this.getCommand("game").setExecutor(new GameCommand(this));

    }

    public Game getGame() {
        return game;
    }
}
