package com.climpy.command.economy;

import com.climpy.SkyBlockC;
import com.climpy.user.User;
import com.climpy.util.C;
import com.climpy.util.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command label, String s, String[] args) {
        Player player = (Player) sender;
        User userSky = SkyBlockC.getInstance().getUserManager().getUser(sender.getName());

        sender.sendMessage(C.color("&b[⛁] &7Mevcut para değeriniz; &f" + NumberUtil.formatNumberByDecimal(userSky.getMoney())));
        return true;
    }
}
