package fr.SkylyReport;

import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.Bukkit;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

public class BanCommand implements CommandExecutor {
    Plugin SkylyReport = Bukkit.getPluginManager().getPlugin("SkylyReport");
    File configFile = new File(SkylyReport.getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public String globalPrefix = ChatColor.GOLD + config.getString("globalprefix");

    private long parseDuration(String durationString) {
        long duration = 0;
        try {
            if (durationString.endsWith("d")) {
                duration = Long.parseLong(durationString.substring(0, durationString.length() - 1)) * 86400000;
            } else if (durationString.endsWith("h")) {
                duration = Long.parseLong(durationString.substring(0, durationString.length() - 1)) * 3600000;
            } else if (durationString.endsWith("m")) {
                duration = Long.parseLong(durationString.substring(0, durationString.length() - 1)) * 60000;
            } else {
                duration = Long.parseLong(durationString) * 1000;
            }
        } catch (NumberFormatException ex) {
            // Handle invalid duration format
        }
        return duration;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE + " " +config.getString("lang-console-cant-execute-command"));
            return true;
        }
        switch (args.length){
            case 0:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-dont-type-player-2-command"));
                return true;
            case 1:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-dont-type-time-2-command"));
                return true;
            case 2:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-dont-type-reason-2-command"));
                return true;
            case 3:
                break;
        }

        String arg = args[0];
        String arg2 = args[1];
        String arg3 = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        Player reportedPlayer = Bukkit.getServer().getPlayer(arg);

        if (reportedPlayer == null) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE +" "+config.getString("lang-dont-found-player-2-command"));
            return true;
        }

        Player player = (Player) sender;

        /*if(arg.equals(player.getName())) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-player-self-2-command"));
            return true;
        }*/

        if(!arg.isEmpty() && !arg2.isEmpty() && !arg3.isEmpty()){
            SimpleDateFormat formatter = new SimpleDateFormat(config.getString("dateformat"));
            Date date = new Date();
            String formattedDate = formatter.format(date);

            String durationString = args[1];
            long duration = parseDuration(durationString);

            Date expires = new Date(System.currentTimeMillis() + duration);

            InetSocketAddress address = reportedPlayer.getAddress();
            String ip = address.getAddress().getHostAddress();

                reportedPlayer.kickPlayer(ChatColor.RED + config.getString("lang-player-blacklisted-msg")
                        .replace("%reason%", arg3));

            Bukkit.getBanList(BanList.Type.IP).addBan(ip,
                    ChatColor.RED + config.getString("lang-player-blacklisted-msg").replace("%reason%", arg3),
                    expires, null);

            player.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-succes-work-2-command"));
        }
        return true;
    }

}
