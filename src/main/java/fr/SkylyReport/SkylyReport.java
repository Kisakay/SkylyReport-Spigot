package fr.SkylyReport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.List;

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

        System.out.println("[SkyLyReport] "+"Dev by Kisakay aka PeacefulTrees x Ezermoz");
        getCommand("customreport").setExecutor(new ReportCommand());
        //getCommand("tempban").setExecutor(new BanCommand());

    }
    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
       /* Player player = event.getPlayer();
        InetSocketAddress address = player.getAddress();
        String ip = address.getAddress().getHostAddress();
        getLogger().info("Le joueur " + player.getName() + " a l'adresse IP " + ip);*/
    }
}
