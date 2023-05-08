package cz.drven.sumo.game.state;

import cz.drven.sumo.Main;
import cz.drven.sumo.utils.Colors;
import cz.drven.sumo.utils.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class WaitingState extends State {

    private BukkitTask task;
    private int time;

    public WaitingState(Main pl) {
        super(pl);
        this.time = 60;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.task = Bukkit.getScheduler().runTaskTimer(pl, () -> {
            if (pl.getGame().getPlayerManager().getAlivePlayers().size() >= 1) {
                this.update();
                if (time == 60 || time == 50 || time == 40 || time == 30 || time == 20 || time == 10 ||
                time == 3 || time == 2 || time == 1) {
                    pl.getGame().getPlayerManager().getSumoPlayers().forEach(players -> {
                        players.getBukkitPlayer().sendMessage(Colors.format("&8[&bServer&8] &fGame start in &3" + time + " &fsecond(s)"));

                        players.getBukkitPlayer().playSound(players.getBukkitPlayer().getLocation(),
                                Sound.NOTE_PLING,
                                20.0F,
                                20.0F);
                    });
                }
                if (time == 0) {
                    pl.getGame().getPlayerManager().getSumoPlayers().forEach(players -> {
                        players.getBukkitPlayer().sendMessage(Colors.format("&8[&bServer&8] &a&oGame started!"));
                        TitleAPI.send(players.getBukkitPlayer(), Colors.format("&e&l  &c"), Colors.format("&a&oGame started!"));

                        players.getBukkitPlayer().playSound(players.getBukkitPlayer().getLocation(),
                                Sound.ORB_PICKUP,
                                1.0F,
                                1.0F);
                    });

                    pl.getGame().getNextState();
                    this.task.cancel();

                }
                time--;
            }
        }, 0L, 20L);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public State getNextState() {
        return new PlayingState(pl);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void join(PlayerJoinEvent e) {
        pl.getGame().getPlayerManager().addPlayer(e.getPlayer().getUniqueId());
        e.setJoinMessage(Colors.format("&8[&bServer&8] &3" + e.getPlayer().getName() + "&f has joined the game. &3(" + pl.getGame().getPlayerManager().getAlivePlayers().size() + ")"));

        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().setHealth(20.0);
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setWalkSpeed(0.2F);
        e.getPlayer().setAllowFlight(false);
        e.getPlayer().setFlying(false);

        pl.getGame().getMapManager().teleport(e.getPlayer());

        this.update();

    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void left(PlayerQuitEvent e) {
        e.setQuitMessage(Colors.format("&8[&bServer&8] &3" + e.getPlayer().getName() + "&f left the game. &3(" + pl.getGame().getPlayerManager().getAlivePlayers().size() + ")"));
        pl.getGame().getPlayerManager().removePlayer(e.getPlayer().getUniqueId());

        this.update();
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
    public void breaks(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void places(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    public void update() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("waiting-state", "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Colors.format("&r &3&lSumo &r"));

        obj.getScore(Colors.format("&fServer: &b" + pl.getConfig().getString("game.server-name"))).setScore(6);
        obj.getScore(Colors.format("&fMap: &b" + pl.getConfig().getString("game.map-name"))).setScore(5);
        obj.getScore(Colors.format("&e&l  &c&l  &a")).setScore(4);
        obj.getScore(Colors.format("&fPlayers: &b" + pl.getGame().getPlayerManager().getAlivePlayers().size())).setScore(3);
        obj.getScore(Colors.format("&fTime: &b" + pl.getGame().toTime(time))).setScore(2);
        obj.getScore(Colors.format("&a&l  &c&l &9&l ")).setScore(1);
        obj.getScore(Colors.format("&7  mc.valonity.cz")).setScore(0);

        Bukkit.getOnlinePlayers().forEach(players -> players.setScoreboard(board));
    }

}
