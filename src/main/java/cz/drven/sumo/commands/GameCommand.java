package cz.drven.sumo.commands;

import cz.drven.sumo.Main;
import cz.drven.sumo.utils.Colors;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    private final Main pl;

    public GameCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player) sender;
            Location loc = p.getLocation();
            if (p.hasPermission("game.admin")) {
                if (args.length == 0) {
                    p.sendMessage(Colors.format("&8[&bServer&8] &7Usage: &3/game setred"));
                    p.sendMessage(Colors.format("&8[&bServer&8] &7Usage: &3/game setblue"));
                    p.sendMessage(Colors.format("&8[&bServer&8] &7Usage: &3/game setspec"));
                    return false;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("setspec")) {
                    pl.getConfig().set("game.spawns.spectate.world", loc.getWorld().getName());
                    pl.getConfig().set("game.spawns.spectate.x", loc.getX());
                    pl.getConfig().set("game.spawns.spectate.y", loc.getY());
                    pl.getConfig().set("game.spawns.spectate.z", loc.getZ());
                    pl.getConfig().set("game.spawns.spectate.pitch", loc.getPitch());
                    pl.getConfig().set("game.spawns.spectate.yaw", loc.getYaw());

                    pl.saveConfig();
                    pl.reloadConfig();

                    p.sendMessage(Colors.format("&8[&bServer&8] &fYou are now just set spectate location"));
                    return false;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("setred")) {
                    pl.getConfig().set("game.spawns.red.world", loc.getWorld().getName());
                    pl.getConfig().set("game.spawns.red.x", loc.getX());
                    pl.getConfig().set("game.spawns.red.y", loc.getY());
                    pl.getConfig().set("game.spawns.red.z", loc.getZ());
                    pl.getConfig().set("game.spawns.red.pitch", loc.getPitch());
                    pl.getConfig().set("game.spawns.red.yaw", loc.getYaw());

                    pl.saveConfig();
                    pl.reloadConfig();

                    p.sendMessage(Colors.format("&8[&bServer&8] &fYou are now just set red location"));
                    return false;
                }
                if (args[0].equalsIgnoreCase("setblue")) {
                    if (args.length == 1) {
                        pl.getConfig().set("game.spawns.blue.world", loc.getWorld().getName());
                        pl.getConfig().set("game.spawns.blue.x", loc.getX());
                        pl.getConfig().set("game.spawns.blue.y", loc.getY());
                        pl.getConfig().set("game.spawns.blue.z", loc.getZ());
                        pl.getConfig().set("game.spawns.blue.pitch", loc.getPitch());
                        pl.getConfig().set("game.spawns.blue.yaw", loc.getYaw());

                        pl.saveConfig();
                        pl.reloadConfig();

                        p.sendMessage(Colors.format("&8[&bServer&8] &fYou are now just set blue location"));
                    }
                    return false;
                } else {
                    p.sendMessage(Colors.format("&8[&bServer&8] &7Usage: &3/game setred"));
                    p.sendMessage(Colors.format("&8[&bServer&8] &7Usage: &3/game setblue"));
                    return false;
                }
            } else {
                p.sendMessage(Colors.format("&cYou are not allowed to do this."));
                return false;
            }
        }
        return true;
    }
}
