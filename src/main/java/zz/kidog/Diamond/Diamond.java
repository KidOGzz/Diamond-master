package zz.kidog.Diamond;

import zz.kidog.oglib.command.FrozenCommandHandler;
import zz.kidog.oglib.ogLib;
import lombok.Getter;
import zz.kidog.Diamond.property.ServerProperty;
import zz.kidog.Diamond.property.ServerStatus;
import zz.kidog.Diamond.tasks.ServerTask;
import zz.kidog.Diamond.utils.Messages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class Diamond extends JavaPlugin
{

    @Getter private static Diamond instance;
    @Getter private JedisPool jedisPool;
    private boolean redisConnected = false;
    @Getter private ServerTask serverTask;
    @Getter private BukkitTask serverBukkitTask;
    @Getter private Messages messages;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Diamond]" + ChatColor.AQUA + "Enabled");
        instance = this;
        this.saveDefaultConfig();
        initRedis();
        FrozenCommandHandler.registerAll(this);
        messages = new Messages();
        if(redisConnected) {
            serverTask = new ServerTask();
            serverBukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, serverTask, 0, 10);
            serverTask.setShouldSync(true);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Diamond] Server Data is " + (redisConnected ? "currently being logged as Redis is connected!" : "not being logged as Redis is not connected."));
    }

    @Override
    public void onDisable() {
        serverTask.setShouldSync(false);
        serverTask.save(ServerProperty.STATUS, ServerStatus.OFFLINE.name());
        serverTask.save(ServerProperty.ONLINE, "0");
        Bukkit.getScheduler().cancelTasks(this);
        jedisPool.destroy();
        instance = null;
    }

    private void initRedis() {
        // Initialize Redis
        System.out.println(Messages.isRedisAuth() + " " + getConfig().getString("redis.host") + ":" + getConfig().getInt("redis.port") + " " + getConfig().getString("redis.auth.password"));
        if(!Messages.isRedisAuth()) {
            jedisPool = new JedisPool(new JedisPoolConfig(), getConfig().getString("redis.host"), getConfig().getInt("redis.port"), 0);
        }else {
            jedisPool = new JedisPool(new JedisPoolConfig(), getConfig().getString("redis.host"), getConfig().getInt("redis.port"), 0, getConfig().getString("redis.auth.password"));
        }
        try {
            Jedis jedis = getJedisPool().getResource();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Diamond] Connected to Redis!");
            redisConnected = true;
            getJedisPool().returnResource(jedis);
        }
        catch (JedisConnectionException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Diamond] Failed to connect to Redis!");
            redisConnected = false;
        }
    }
}
