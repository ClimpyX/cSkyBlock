package com.climpy.command;

import com.climpy.SkyBlockC;
import com.climpy.util.C;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {
    private final SkyBlockC skyBlockC = SkyBlockC.getInstance();


    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

        Player player = (Player) sender;

        if (arguments.length > 0) {
            player.sendMessage(C.color("&bDoğru Kullanım: &6/" + label));
        }

        else if (this.skyBlockC.getSkyblockBoard().isScoreboardMini(player)) {
                this.skyBlockC.getSkyblockBoard().setScoreboardMode(player, false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aKenar çubuğu büyütüldü."));
            } else {
                this.skyBlockC.getSkyblockBoard().setScoreboardMode(player, true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aKenar çubuğu küçültüldü."));
            }
        return false;
    }
}
