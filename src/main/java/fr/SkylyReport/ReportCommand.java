package fr.SkylyReport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class ReportCommand implements CommandExecutor {
    public static String globalPrefix = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "SkylyReport" + ChatColor.DARK_GREEN + "]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(globalPrefix + ChatColor.RED + " This command can only be executed by a player.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(globalPrefix + ChatColor.RED + " Usage: /report <user> <reason>");
            return true;
        }

        String arg = args[0];
        String arg2 = args[1];

        Player player = (Player) sender;
        if(!arg.isEmpty() && !arg2.isEmpty()){
            try {

                String command = "**[** 📣 **]** __Report__:      L'utilisateur **"+player.getName()+"** à envoyer un signalement à **"+arg+"** pour `"+arg2+"`";

                final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://discord.com/api/webhooks/1097635034334249061/LX7HbPpv1sQhLpPyzotRMArDDK40_MiTbIVLVmhcqACHwP0Npmnvgl2G48d-smlUMpnM").openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11");
                connection.setDoOutput(true);
                try (final OutputStream outputStream = connection.getOutputStream()) {
                    // Handle backslashes.
                    String preparedCommand = command.replaceAll("\\\\", "");
                    if (preparedCommand.endsWith(" *")) preparedCommand = preparedCommand.substring(0, preparedCommand.length() - 2) + "*";

                    outputStream.write(("{\"content\":\"" + preparedCommand + "\"}").getBytes(StandardCharsets.UTF_8));
                    //outputStream.write(("\"embeds\": [{\"title\": \"Report de {user}\", \"description\": \"Le report de {user} qui vise {targetedUsername} à : DD:MM:YYYY HH:MM pour:\n{reason}\", \"color\": 16711680, \"footer\": {\"text\": \"SkylyReport de play.skyly.fr\", \"icon_url\": \"https://media.discordapp.net/attachments/1073309804493287585/1097644114943684728/Logo_S.png\"}}]}").getBytes(StandardCharsets.UTF_8));
                }
                connection.getInputStream();
            } catch (final IOException e) {
                e.printStackTrace();

            }

            player.sendMessage(globalPrefix + ChatColor.BLUE + " You have succefully reported this user lol.");
        }
        return true;
    }
}