package com.climpy.listeners;

import com.climpy.SkyBlockC;
import com.climpy.user.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncJoinListener implements Listener {

    @EventHandler
    public void onJoinEvent(AsyncPlayerPreLoginEvent event) {
        User user = SkyBlockC.getInstance().getUserManager().getUser(event.getUniqueId());

        if (user == null) {
            user = new User(event.getUniqueId());
            SkyBlockC.getInstance().getUserManager().getUsers().put(event.getUniqueId(), user);
            user.setMoney(7500);
        }

        user.setName(event.getName());
        Bukkit.getScheduler().runTaskLater(SkyBlockC.getInstance(), user::save, 20L);
    }
}
