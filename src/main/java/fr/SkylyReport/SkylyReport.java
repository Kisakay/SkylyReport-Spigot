package fr.SkylyReport;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class SkylyReport extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        File configFile = new File(getDataFolder(), "config.yml");
        File dbFile = new File(getDataFolder(), "db.txt");

        if(!configFile.exists()){
            getDataFolder().mkdirs();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/config.yml");
                InputStream inputStream1 = getClass().getResourceAsStream("/db.txt");

                assert inputStream != null;
                Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(inputStream1, dbFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                getLogger().info("Les fichiers ont été extrait avec succès !");
            } catch (IOException e) {
                getLogger().warning("Impossible d'extraire les fichier !");
                e.printStackTrace();
            }
        } else {
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        }

        getLogger().info("Dev by Kisakay aka PeacefulTrees x Ezermoz");

        getCommand("customreport").setExecutor(new ReportCommand());
        getCommand("tempban").setExecutor(new BanCommand());

    }
    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        File configFile = new File(getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        Player player = event.getPlayer();
        InetSocketAddress address = player.getAddress();
        String ip = address.getAddress().getHostAddress();
        try {
            MyDatabase.init();
            MyDatabase.add(player.getUniqueId().toString(), ip);

            String ban = MyDatabase.get("blacklisted-"+player.getUniqueId().toString());
            if(ban == null) { return; }

            if(ban.equals(ip)){} {
                player.kickPlayer(ChatColor.RED + config.getString("lang-player-blacklisted-msg")
                        .replace("%reason%", MyDatabase.get("blacklistedReason-"+player.getUniqueId())
                ));

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), ChatColor.RED + config.getString("lang-player-blacklisted-msg")
                        .replace("%reason%", MyDatabase.get("blacklistedReason-"+player.getUniqueId()
                        )), null, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
