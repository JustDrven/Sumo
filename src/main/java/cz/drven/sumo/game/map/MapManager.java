package cz.drven.sumo.game.map;

import cz.drven.sumo.Main;
import cz.drven.sumo.player.SumoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class MapManager {

    private final Main pl;
    private final FileConfiguration config;
    private Location red;
    private Location blue;
    private Location spectate;

    public MapManager(Main pl) {
        this.pl = pl;
        this.config = pl.getConfig();

        this.redLocation();
        this.blueLocation();
        this.spectateLocation();
    }

    private void redLocation() {
        double redx = config.getDouble("game.spawns.red.x");
        double redy = config.getDouble("game.spawns.red.y");
        double redz = config.getDouble("game.spawns.red.z");
        float redpitch = (float) config.getDouble("game.spawns.red.pitch");
        float redyaw = (float) config.getDouble("game.spawns.red.pitch");

        World redworld = Bukkit.getWorld(config.getString("game.spawns.red.world"));

        red = new Location(redworld, redx, redy, redz, redyaw, redpitch);

    }

    private void blueLocation() {
        double bluex = config.getDouble("game.spawns.blue.x");
        double bluey = config.getDouble("game.spawns.blue.y");
        double bluez = config.getDouble("game.spawns.blue.z");
        float bluepitch = (float) config.getDouble("game.spawns.blue.pitch");
        float blueyaw = (float) config.getDouble("game.spawns.blue.pitch");

        World blueworld = Bukkit.getWorld(config.getString("game.spawns.blue.world"));

        blue = new Location(blueworld, bluex, bluey, bluez, blueyaw, bluepitch);

    }

    private void spectateLocation() {
        double bluex = config.getDouble("game.spawns.spectate.x");
        double bluey = config.getDouble("game.spawns.spectate.y");
        double bluez = config.getDouble("game.spawns.spectate.z");
        float bluepitch = (float) config.getDouble("game.spawns.spectate.pitch");
        float blueyaw = (float) config.getDouble("game.spawns.spectate.pitch");

        World blueworld = Bukkit.getWorld(config.getString("game.spawns.spectate.world"));

        spectate = new Location(blueworld, bluex, bluey, bluez, blueyaw, bluepitch);

    }

    public void teleport(Player p) {
        SumoPlayer[] players = pl.getGame().getPlayerManager().getSumoPlayers()
                .stream().filter(ps -> !ps.isSpectating()).toArray(SumoPlayer[]::new);

        SumoPlayer redPlayer = players[0];
        SumoPlayer bluePlayer = players[1];

        if (pl.getGame().getPlayerManager().getSumoPlayers().size() == 1) {
            redPlayer.getBukkitPlayer().teleport(this.getRed());
            return;
        }
        if (pl.getGame().getPlayerManager().getSumoPlayers().size() == 2) {
            bluePlayer.getBukkitPlayer().teleport(this.getBlue());

            return;
        }
        if (pl.getGame().getPlayerManager().getSumoPlayers().size() >= 3) {
            p.teleport(this.getSpectate());
        }
    }

    public Location getBlue() {
        return blue;
    }

    public Location getRed() {
        return red;
    }

    public Location getSpectate() {
        return spectate;
    }

}
