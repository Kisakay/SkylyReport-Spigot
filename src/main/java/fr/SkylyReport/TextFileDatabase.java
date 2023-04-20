package fr.SkylyReport;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextFileDatabase extends JavaPlugin {

    private final String DATABASE_FILENAME = getDataFolder()+"db.txt";
    private Map<String, String> data;

    public TextFileDatabase() {
        data = new HashMap<>();
        loadDataFromFile();
    }

    public void addData(String key, String value) {
        data.put(key, value);
        saveDataToFile();
    }

    public String getData(String key) {
        return data.get(key);
    }

    public void setData(String key, String value) {
        if (data.containsKey(key)) {
            data.put(key, value);
            saveDataToFile();
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from database file: " + e.getMessage());
        }
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILENAME))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to database file: " + e.getMessage());
        }
    }
}
