package fr.SkylyReport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.awt.Color;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;

public class ReportCommand implements CommandExecutor {
    public static String globalPrefix = ChatColor.GOLD + "[SkyLy]";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE + " This command can only be executed by a player");
            return true;
        }
        switch (args.length){
            case 0:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " Entre le noms du joueur que tu veux signaler");
                return true;
            case 1:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " Entre la raison");
                return true;
            case 2:
                break;
            default:
                sender.sendMessage(globalPrefix + ChatColor.WHITE + " La syntaxe de ta commande est incompréhensible");
                return true;
        }

        String arg = args[0];
        String arg2 = args[1];

        Player reportedPlayer = Bukkit.getServer().getPlayer(arg);

        if (reportedPlayer == null) {
            sender.sendMessage(globalPrefix + ChatColor.WHITE +" Le joueur spécifié est introuvable");
            return true;
        }

        Player player = (Player) sender;
        if(!arg.isEmpty() && !arg2.isEmpty()){
                   try {
                       SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                       Date date = new Date();
                       String formattedDate = formatter.format(date);

            DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1097635034334249061/LX7HbPpv1sQhLpPyzotRMArDDK40_MiTbIVLVmhcqACHwP0Npmnvgl2G48d-smlUMpnM");
            webhook.setContent(":mega: || @  here ||");
            webhook.setAvatarUrl("https://media.discordapp.net/attachments/1073309804493287585/1097644114943684728/Logo_S.png");
            webhook.setUsername("SkyLy");
            webhook.setTts(false);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle("Report de "+player.getName().toString())
                            .setDescription("Le report de **"+URLEncoder.encode(player.getName().toString(), "UTF-8")+"** qui signale le joueur **"+arg+"** a `"+formattedDate.toString()+"`"+ "\\nPour la raison :\\n**"+arg2+"**")
                            .setColor(Color.RED)
                    .setThumbnail("https://mc-heads.net/avatar/"+player.getName())
                    .setFooter("Latest skyly.fr report detected - The server is currently on", "https://media.discordapp.net/attachments/1073309804493287585/1097644114943684728/Logo_S.png"));
                webhook.execute(); //Handle exception
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //String command = "**[** 📣 **]** __Report__:      L'utilisateur **"+player.getName()+"** à envoyer un signalement à **"+arg+"** pour `"+arg2+"`";

            player.sendMessage(globalPrefix + ChatColor.BLUE + " You have succefully reported this user lol.");
        }
        return true;
    }
}