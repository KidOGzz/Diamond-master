package zz.kidog.Diamond.property;

import org.bukkit.Bukkit;

public enum ServerStatus {

    ONLINE, OFFLINE, WHITELISTED;

    public static ServerStatus getCurrentStatus() {
        return Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED : ServerStatus.ONLINE;
    }
}
