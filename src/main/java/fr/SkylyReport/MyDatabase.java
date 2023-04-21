package fr.SkylyReport;

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MyDatabase {
    static Plugin SkylyReport = Bukkit.getPluginManager().getPlugin("SkylyReport");
    private static final String DB_FILE = SkylyReport.getDataFolder()+"./db.txt";


    private static final Map<String, String> data = new HashMap<>();

    public static void init() throws IOException {
        File file = new File(DB_FILE);

        if (!file.exists()) {
            file.createNewFile();
        }
        load();
    }

    public static void add(String key, String value) throws IOException {
        data.put(key, value);
        save();
    }

    public static String get(String key) {
        return data.get(key);
    }

    public static String unget(String value) {
        return data.get(value);
    }
    public static void update(String key, String value) throws IOException {
        if (data.containsKey(key)) {
            data.put(key, value);
            save();
        }
    }

    public static void remove(String key) throws IOException {
        if (data.containsKey(key)) {
            data.remove(key);
            save();
        }
    }

    private static void load() throws IOException {
        try (Scanner scanner = new Scanner(new File(DB_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("=", 2);
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        }
    }

    private static void save() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DB_FILE))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        }
    }
}
