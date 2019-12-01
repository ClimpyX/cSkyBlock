package com.climpy.command;

import com.climpy.ClimpyLibrary;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import com.climpy.utils.ColorUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
  private final ClimpyLibrary utilities = ClimpyLibrary.getInstance();
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
    User user = ProfilePlugin.getInstance().getUserManager().getUser(sender.getName());
    Player player = (Player) sender;

    if (sender instanceof Player && !player.isOp() && !user.getRankType().isAboveOrEqual(RankType.MOD)) {
      sender.sendMessage(ChatColor.RED + "Bu komutu gerçekleştirmek için " + RankType.MOD.getDisplayName() + ChatColor.RED + " veya üzeri rütbe gerekiyor.");
      return false;
    }

      if (arguments.length == 0) {

        if (sender instanceof Player) {
          if (this.utilities.getStaffModeListener().isStaffChatActive(player))
          {
            this.utilities.getStaffModeListener().setStaffChat(player, false);
            sender.sendMessage((new ColorUtils()).translateFromString("&aSohbet kanalınız &fherkese açık &aolarak değiştirildi."));
          }
          else
          {
            this.utilities.getStaffModeListener().setStaffChat(player, true);
            sender.sendMessage((new ColorUtils()).translateFromString("&aSohbet kanalınız &fpersonel modu &aolarak değiştirildi."));
          }
        
        } else {
          
          sender.sendMessage((new ColorUtils()).translateFromString("&cYetki yok."));
        }
      
      } else {
        
        for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
          
          if (!user.getRankType().isAboveOrEqual(RankType.MOD))
          {
            staff.sendMessage((new ColorUtils()).translateFromString("&b(Personel) " + sender.getName() + "&7: &f" + StringUtils.join(arguments, ' ')));
          }
        } 
        Bukkit.getServer().getConsoleSender().sendMessage((new ColorUtils()).translateFromString("&b(Personel) " + sender.getName() + "&7: &f" + StringUtils.join(arguments, ' ')));
      }
    return false;
  }
}
