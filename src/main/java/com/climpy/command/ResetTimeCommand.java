package com.climpy.command;

import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import com.climpy.util.C;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command label, String s, String[] args) {
        Player player = (Player) sender;
        User user = ProfilePlugin.getInstance().getUserManager().getUser(sender.getName());

        if (sender instanceof Player && !player.isOp() && user.getRankType().isAboveOrEqual(RankType.MODPLUS)) {
            sender.sendMessage(ChatColor.RED + "Bu komutu gerçekleştirmek için " + RankType.MODPLUS.getDisplayName() + ChatColor.RED + " veya üzeri rütbe gerekiyor.");
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(C.color("&bDoğru Kullanım: &6/" + label.getName() + " [oyuncuAdı]"));
            return true;
        }

        com.climpy.user.User targetUser = SkyBlockC.getInstance().getUserManager().getUser(args[0]);

        if (targetUser == null) {
            sender.sendMessage(C.color("&f" + args[0] + " &cismine sahip bir oyuncu bulunamadı."));
            return true;
        }

        Player t = Bukkit.getPlayer(args[0]);

        targetUser.setPlayTime(0);
        sender.sendMessage(C.color(user.getRankType().getColor() + t.getName() + " &aoyuncusu için oyun oynama verileri sıfırlandı.\nBu işlem geri döndürülmez."));
        t.sendMessage(C.color("&aOyun oynama verileri başarıyla, sıfırlandı.\nBu işlem geri döndürülmez."));
        return true;
    }
}
