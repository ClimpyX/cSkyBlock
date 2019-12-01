package com.climpy.command;

import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import com.climpy.util.NumberUtil;
import com.climpy.util.TimeUtils;
import com.climpy.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class SetTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        User user = ProfilePlugin.getInstance().getUserManager().getUser(sender.getName());
        com.climpy.user.User userSky = SkyBlockC.getInstance().getUserManager().getUser(sender.getName());

        Player player = (Player) sender;

        if (sender instanceof Player && !player.isOp() && !user.getRankType().isAboveOrEqual(RankType.ADMIN)) {
            sender.sendMessage(ChatColor.RED + "Bu komutu gerçekleştirmek için " + RankType.ADMIN.getDisplayName() + ChatColor.RED + " veya üzeri rütbe gerekiyor.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bDoğru Kullanım: &6/" + label + " [Oyuncu-Adı] <oynama-Süresi>"));
            return true;
        }

        com.climpy.user.User targetUser = SkyBlockC.getInstance().getUserManager().getUser(args[0]);

        if (targetUser == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&f" + args[0] + " &cismine sahip bir oyuncu bulunamadı."));
            return true;
        }


        Player t = Bukkit.getPlayer(args[0]);

        targetUser.setPlayTime(Long.parseLong(args[1]));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',user.getRankType().getColor() + t.getName() + " &aoyuncusunun oynama süresi &f" + TimeUtils.getDurationWords(Long.parseLong(args[1]), true, false) + " &aolarak güncellendi."));
        t.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aOynama süreniz &f" + TimeUtils.getDurationWords(Long.parseLong(args[1]), true, false) + " &aolarak güncellendi."));
        user.save();
        return true;
    }
}
