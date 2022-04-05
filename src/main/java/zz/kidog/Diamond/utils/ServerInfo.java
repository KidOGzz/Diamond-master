package zz.kidog.Diamond.utils;

import lombok.Getter;
import zz.kidog.Diamond.property.ServerProperty;
import zz.kidog.Diamond.property.ServerStatus;
import zz.kidog.Diamond.tasks.ServerTask;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ServerInfo {

    @Getter
    private static final HashSet<String> Servers = new HashSet<>();

    public static String getStatusFormatted(String s, boolean shouldColor) {
        if(!serverExists(s)) return null;
        ServerStatus st;
        try {
            st = ServerStatus.valueOf(getProperty(s, ServerProperty.STATUS).toUpperCase());
        }catch (IllegalArgumentException e) {
            return null;
        }
        switch (st) {
            case ONLINE:
                return (shouldColor ? ChatColor.GREEN : "") + "Online";
            case WHITELISTED:
                return (shouldColor ? ChatColor.YELLOW : "") + "Whitelisted";
            case OFFLINE:
                return (shouldColor ? ChatColor.RED : "") + "Offline";
        }
        return null;
    }

    public static boolean serverExists(String server) {
        return ServerTask.getServerDataTable().column(ServerProperty.ONLINE).containsKey(server);
    }

    public static List<String> getGroups() {
        List<String> groups = new ArrayList<>();
        ServerTask.getServerDataTable().column(ServerProperty.ONLINE).values().stream().filter(s -> getProperty(s.toString(), ServerProperty.GROUP) != null);
        if(groups.isEmpty()) return null;
        return groups;
    }
    public static String getProperty(String server, ServerProperty data) {
        if(!serverExists(server)) {
            return null;
        }else {
            return "" + ServerTask.getServerDataTable().column(data).get(server);
        }
    }
}
