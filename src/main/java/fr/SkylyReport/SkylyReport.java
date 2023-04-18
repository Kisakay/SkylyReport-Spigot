package fr.SkylyReport;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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

public final class SkylyReport extends JavaPlugin {

    @Override
    public void onEnable() {
        File configFile = new File(getDataFolder(), "config.yml");

        if(!configFile.exists()){
            getDataFolder().mkdirs();
            try {
                InputStream inputStream = getClass().getResourceAsStream("/config.yml");
                Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                getLogger().info("Le fichier config.yml a été extrait avec succès !");
            } catch (IOException e) {
                getLogger().warning("Impossible d'extraire le fichier config.yml !");
                e.printStackTrace();
            }
        } else {
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            String maValeur = config.getString("ma-clef");
        }

        System.out.println("[SkyLyReport] "+"Dev by Kisakay aka PeacefulTrees x Ezermoz");
        getCommand("report").setExecutor(new ReportCommand());
    }
    @Override
    public void onDisable() {

    }
}
