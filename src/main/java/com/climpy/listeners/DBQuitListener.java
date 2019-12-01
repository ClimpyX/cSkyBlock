package com.climpy.listeners;

import com.climpy.SkyBlockC;
import com.climpy.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DBQuitListener implements Listener {

    @EventHandler
    public void onQuitListener(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = SkyBlockC.getInstance().getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return;
        }

        user.save();
        SkyBlockC.getInstance().getUserManager().getUsers().remove(player.getUniqueId());
    }
}
