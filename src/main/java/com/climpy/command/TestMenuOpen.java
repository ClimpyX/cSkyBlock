package com.climpy.command;

import com.climpy.menus.SkyblockMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestMenuOpen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;

        SkyblockMenu.INVENTORY.open(player);
        return false;
    }
}
