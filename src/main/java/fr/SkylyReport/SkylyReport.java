package fr.SkylyReport;

import org.bukkit.plugin.java.JavaPlugin;

public final class SkylyReport extends JavaPlugin {
    @Override
    public void onEnable() {

        //Register your commands one by one and provide an instance of the class
        getCommand("report").setExecutor(new ReportCommand());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
