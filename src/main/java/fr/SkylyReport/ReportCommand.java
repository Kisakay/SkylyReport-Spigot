package fr.SkylyReport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import java.util.Arrays;
import org.bukkit.plugin.Plugin;
public class ReportCommand implements CommandExecutor {

    Plugin SkylyReport = Bukkit.getPluginManager().getPlugin("SkylyReport");
    File configFile = new File(SkylyReport.getDataFolder(), "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    public String globalPrefix = ChatColor.GOLD + config.getString("globalprefix");


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE + " " +config.getString("lang-console-cant-execute-command"));
            return true;
        }
        switch (args.length){
            case 0:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-dont-type-player-command"));
                return true;
            case 1:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-dont-type-reason-command"));
                return true;
            case 2:
                break;
        }

        String arg = args[0];
        String arg2 = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        Player reportedPlayer = Bukkit.getServer().getPlayer(arg);

        if (reportedPlayer == null) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE +" "+config.getString("lang-dont-found-player-command"));
            return true;
        }

        Player player = (Player) sender;
        if(!arg.isEmpty() && !arg2.isEmpty()){
            SimpleDateFormat formatter = new SimpleDateFormat(config.getString("dateformat"));
            Date date = new Date();
            String formattedDate = formatter.format(date);

            try {
            DiscordWebhook webhook = new DiscordWebhook(config.getString("webhook"));
            webhook.setContent(config.getString("msg-content-webhook"));
            webhook.setAvatarUrl(config.getString("msg-logo-avatar-webhook"));
            webhook.setUsername(config.getString("msg-username-webhook"));
            webhook.setTts(false);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(config.getString("msg-title-embed-webhook"))
                    .setDescription(config.getString("msg-description-embed-webhook"))
                    .setColor(Color.RED)
                    .setThumbnail(config.getString("msg-thumbnail-player-head-api-webhook"))
                    .setFooter(config.getString("msg-footer-text-embed-webhook"), config.getString("msg-footer-icon-embed-webhook")));
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.sendMessage(globalPrefix + ChatColor.WHITE + " "+config.getString("lang-succes-work-command"));
        }
        return true;
    }
}