package cz.drven.sumo.game.state;

import cz.drven.sumo.Main;
import cz.drven.sumo.player.SumoPlayer;
import cz.drven.sumo.utils.Colors;
import cz.drven.sumo.utils.TitleAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayingState extends State {

    @Override
    public void onEnable() {
        super.onEnable();

        this.update();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public PlayingState(Main pl) {
        super(pl);
    }

    @Override
    public State getNextState() {
        return new RestartingState(pl);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        pl.getGame().getPlayerManager().addPlayer(e.getPlayer().getUniqueId());
        SumoPlayer player = pl.getGame().getPlayerManager().getPlayer(e.getPlayer().getUniqueId()).get();

        if (!player.isSpectating()) {
            player.setSpectating();

            player.getBukkitPlayer().getInventory().clear();
            player.getBukkitPlayer().getInventory().setArmorContents(null);

            pl.getGame().getItemManager().getSpectatorItems().giveItems(e.getPlayer());

            pl.getGame().getMapManager().teleport(player.getBukkitPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void left(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        pl.getGame().getPlayerManager().removePlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hunger(FoodLevelChangeEvent e) {
        final Player p = (Player) e.getEntity();
        if (p != null) {
            p.setFoodLevel(20);
            p.setHealth(20.0);
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        Player victim = e.getEntity();
        Player killer = e.getEntity().getKiller();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(victim)) continue;
            if (player.equals(killer)) continue;

            TitleAPI.send(player, Colors.format("&fWinner: &a" + killer.getName()), Colors.format("&e&l  &e&l  "));

        }

        if (killer instanceof Player) {
            TitleAPI.send(killer, Colors.format("&e&lVictory"), Colors.format("&e&l  &c "));
        }
        if (victim instanceof Player) {
            TitleAPI.send(killer, Colors.format("&cGame Over"), Colors.format("&e&l &c&l &a &c "));
        }
        pl.getGame().getNextState();

    }

    public void update() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("waiting-state", "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Colors.format("&r &3&lSumo &r"));

        obj.getScore(Colors.format("&fServer: &b" + pl.getConfig().getString("game.server-name"))).setScore(5);
        obj.getScore(Colors.format("&fMap: &b" + pl.getConfig().getString("game.map-name"))).setScore(4);
        obj.getScore(Colors.format("&e&l  &c&l  &a")).setScore(3);
        obj.getScore(Colors.format("&fPlayers: &b" + pl.getGame().getPlayerManager().getAlivePlayers().size())).setScore(2);
        obj.getScore(Colors.format("&a&l  &c&l &9&l ")).setScore(1);
        obj.getScore(Colors.format("&7  mc.valonity.cz")).setScore(0);

        Bukkit.getOnlinePlayers().forEach(players -> players.setScoreboard(board));
    }

}
