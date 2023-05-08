package cz.drven.sumo.game.state;

import cz.drven.sumo.Main;
import cz.drven.sumo.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class RestartingState extends State {

    private BukkitTask task;
    private int time = 10;

    public RestartingState(Main pl) {
        super(pl);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.task = Bukkit.getScheduler().runTaskTimer(pl, () -> {
            this.update();
            if (time == 10 || time == 5 || time == 3 || time == 2 || time == 1) {
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Colors.format("&8[&bServer&8] &fGame over in &3" + time + " &fsecond(s)")));
            }

            if (time == 0) {

                Bukkit.broadcastMessage(Colors.format("&8[&bSumo&8] &a&oServer restarted"));

                Bukkit.shutdown();

                this.task.cancel();
            }
            time--;
        }, 0L, 20L);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public State getNextState() {
        return null;
    }

    public void update() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("waiting-state", "dummy");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Colors.format("&r &3&lSumo &r"));

        obj.getScore(Colors.format("&e&l  &c&l  &a")).setScore(4);
        obj.getScore(Colors.format("&fStatus: &bEnding..")).setScore(3);
        obj.getScore(Colors.format("&fTime: &b" + pl.getGame().toTime(time))).setScore(2);
        obj.getScore(Colors.format("&a&l  &c&l &9&l ")).setScore(1);
        obj.getScore(Colors.format("&7  mc.valonity.cz")).setScore(0);

        Bukkit.getOnlinePlayers().forEach(players -> players.setScoreboard(board));
    }

}
