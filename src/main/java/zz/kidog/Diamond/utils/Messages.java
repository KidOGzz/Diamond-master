package zz.kidog.Diamond.utils;

import lombok.Getter;
import zz.kidog.Diamond.Diamond;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;

public class Messages {

    @Getter private static String redisIP, redisPassword;
    @Getter private static int redisPort;
    @Getter private static boolean redisAuth;
    @Getter private static String serverName;

    public Messages() {
        redisIP = Diamond.getInstance().getConfig().getString("redis.host");
        redisPort = Diamond.getInstance().getConfig().getInt("redis.port");
        redisAuth = Diamond.getInstance().getConfig().getBoolean("redis.auth.enabled");
        redisPassword = Diamond.getInstance().getConfig().getString("redis.auth.password");
        serverName = Diamond.getInstance().getConfig().getString("servername");
    }

    public static String formatTPS(double tps, boolean shouldColor) {
        DecimalFormat format = new DecimalFormat("##.##");
        ChatColor colour;
        if (tps >= 18.0D) {
            colour = ChatColor.GREEN;
        } else {
            if (tps >= 15.0D) {
                colour = ChatColor.YELLOW;
            } else {
                colour = ChatColor.RED;
            }
        }
        return (shouldColor ? colour : "") + format.format(tps);
    }

    public static void sendLog(Plugin plugin, String type, String message) {
        sendLog(plugin, ChatColor.AQUA, type, message);
    }

    public static void sendLog(Plugin plugin, ChatColor color, String type, String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[" + color + plugin.getName() + "&8] &7(&f" + type + "&7) &f" + message));
    }
}
