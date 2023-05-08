package cz.drven.sumo.player;

import cz.drven.sumo.game.team.Team;
import cz.drven.sumo.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SumoPlayer {

    private final UUID uuid;
    private Team team;
    private boolean spectating = false;

    public SumoPlayer(UUID uuid) {
        this.uuid = uuid;

    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    public boolean isOnline() {
        return this.getOfflinePlayer().isOnline();
    }

    public boolean isSpectating() {
        return spectating;
    }

    public void setSpectating() {
        this.spectating = true;

        for (Player ps : Bukkit.getOnlinePlayers()) {
            if (this.getUUID().equals(ps.getUniqueId())) continue;

            ps.hidePlayer(this.getBukkitPlayer());
        }

        this.getBukkitPlayer().getActivePotionEffects().forEach(potionEffect -> {
            this.getBukkitPlayer().removePotionEffect(potionEffect.getType());
        });

        this.getBukkitPlayer().spigot().setCollidesWithEntities(false);
        this.getBukkitPlayer().getInventory().clear();
        this.getBukkitPlayer().getInventory().setArmorContents(null);

        this.getBukkitPlayer().setGameMode(GameMode.ADVENTURE);
        this.getBukkitPlayer().setFallDistance(0.0F);
        this.getBukkitPlayer().setAllowFlight(true);
        this.getBukkitPlayer().setFlying(true);

        this.getBukkitPlayer().sendMessage(Colors.format("&8[&bServer&8] &aYou are spectator"));

    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
