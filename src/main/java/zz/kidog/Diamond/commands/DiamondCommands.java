package zz.kidog.Diamond.commands;

import zz.kidog.oglib.command.Param;
import zz.kidog.oglib.command.Command;
import zz.kidog.Diamond.property.ServerProperty;
import zz.kidog.Diamond.utils.Messages;
import zz.kidog.Diamond.utils.ServerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class DiamondCommands {

    @Command(names = "rsenv", permission = "op", description = "Prints Diamond enviroment information", async = true)
    public static void rsenv(CommandSender s, @Param(name = "server...") String server) {
        if (ServerInfo.serverExists(server)) {
            s.sendMessage("§c§nDiamond Environment Dump");
            s.sendMessage("");
            s.sendMessage("§eName: §f" + server);
            s.sendMessage("§eGroup: §f" + server);
            s.sendMessage("§eTPS: §f" + Messages.formatTPS(Double.parseDouble(Objects.requireNonNull(ServerInfo.getProperty(server, ServerProperty.TPS))), true));
            s.sendMessage("§eOnline Players: §f" + ServerInfo.getProperty(server, ServerProperty.ONLINE) + "/" + ServerInfo.getProperty(server, ServerProperty.MAXIMUM));
            s.sendMessage("§eStatus: §f" + ServerInfo.getStatusFormatted(server, true));
        } else {
            s.sendMessage(ChatColor.RED + "This server don't exist...");
            return;
        }
    }
}
