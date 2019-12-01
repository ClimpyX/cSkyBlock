package com.climpy.command;

import com.climpy.ClimpyLibrary;
import com.climpy.SkyBlockC;
import com.climpy.profile.ProfilePlugin;
import com.climpy.profile.rank.RankType;
import com.climpy.profile.user.User;
import com.climpy.util.C;
import com.climpy.utils.ColorUtils;
import com.climpy.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChatCommand implements CommandExecutor {
  private final SkyBlockC skyBlockC = SkyBlockC.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
      Player player = (Player) sender;
      User user = ProfilePlugin.getInstance().getUserManager().getUser(sender.getName());

      if (sender instanceof Player && !player.isOp() && user.getRankType().isAboveOrEqual(RankType.MODPLUS)) {
        sender.sendMessage(ChatColor.RED + "Bu komutu gerçekleştirmek için " + RankType.MODPLUS.getDisplayName() + ChatColor.RED + " veya üzeri rütbe gerekiyor.");
        return true;
      }

      Long newTicks;
      long currentTicks = skyBlockC.getChatListener().getMillisecondLeft();

      if (currentTicks > 0L) {

        newTicks = Long.valueOf(0L);


      } else if (arguments.length < 1) {

        newTicks = Long.valueOf(TimeUtils.parse("10m"));
      } else {

        newTicks = Long.valueOf(TimeUtils.parse(arguments[0]));
        if (newTicks.longValue() == -1L) {

          sender.sendMessage((new ColorUtils()).translateFromString("&cGeçersiz süre, doğru formatı kullanın: 5m15s [5 dakika 15 saniye]"));
          return true;
        }
      }


    skyBlockC.getChatListener().setMuteChatMillis(newTicks.longValue());

    if (skyBlockC.getChatListener().isChatMuted()) {
      Bukkit.getServer().broadcastMessage(C.color(ClimpyLibrary.getInstance().getSuffix(player) + sender.getName() + "&a, " + com.climpy.util.TimeUtils.getDurationWords(newTicks.longValue(), true, true) + " &aiçin devre dışı bıraktı."));
    } else {
      Bukkit.getServer().broadcastMessage(C.color("&f" + ClimpyLibrary.getInstance().getSuffix(player) + sender.getName() + "&a, genel sohbeti açık duruma getirdi. "));
    }
    return false;
  }
}
