package cz.drven.sumo.player;

import cz.drven.sumo.Main;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {

    private final Main plugin;
    private Set<SumoPlayer> sumoPlayers = new HashSet<>();

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
    }

    public Main getPlugin() {
        return plugin;
    }

    public void addPlayer(UUID uuid) {
        this.getSumoPlayers().add(new SumoPlayer(uuid));
    }

    public void removePlayer(UUID uuid) {
        this.getSumoPlayers().removeIf(sumoPlayer -> sumoPlayer.getUUID().equals(uuid));
    }

    public Optional<SumoPlayer> getPlayer(UUID uuid) {
        return this.getSumoPlayers().stream()
                .filter(name -> name.getUUID().equals(uuid))
                .findAny();
    }

    public Set<SumoPlayer> getSumoPlayers() {
        return sumoPlayers;
    }

    public Set<SumoPlayer> getAlivePlayers() {
        return this.getSumoPlayers().stream()
                .filter(name -> !name.isSpectating())
                .collect(Collectors.toSet());
    }

}
