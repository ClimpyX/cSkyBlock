package com.climpy.util;

import org.bukkit.ChatColor;

public class C {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
